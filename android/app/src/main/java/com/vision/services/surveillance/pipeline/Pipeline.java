package com.vision.services.surveillance.pipeline;

import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.surveillance.pipeline.commons.data.pipeline_cycle.PipelineCycle;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_cycle_jobs_finalizer.PipelineCycleJobsFinalizer;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_cycle_operation_states_processor.PipelineCycleOperationStatesProcessor;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_cycle_result_builder.PipelineCycleResultBuilder;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_cycle_result_processor.PipelineCycleResultProcessor;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_cycle_result_validator.PipelineCycleResultValidator;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_job.PipelineJob;
import com.vision.services.surveillance.pipeline.cycle_result_processor.SimpleCycleResultProcessor;
import com.vision.services.surveillance.pipeline.cycle_jobs_finalizer.SimpleCycleJobsFinalizer;
import com.vision.services.surveillance.pipeline.cycle_operation_states_processor.SimpleCycleOperationStatesProcessor;
import com.vision.services.surveillance.pipeline.operations.detect_device_movement.operation.DetectDeviceMovementOperation;
import com.vision.services.surveillance.pipeline.operations.empty.operation.EmptyOperation;
import com.vision.services.surveillance.pipeline.operations.recognize_person.operation.RecognizePersonOperation;
import com.vision.services.surveillance.pipeline.operations.wait.operation.WaitOperation;
import com.vision.services.surveillance.pipeline.cycle_result_builder.SimpleCycleResultBuilder;
import com.vision.services.surveillance.pipeline.cycle_result_validator.SimpleCycleResultValidator;

import org.json.JSONObject;

import java.util.concurrent.LinkedBlockingQueue;


public class Pipeline {
    private static Pipeline sInstance;

    private boolean mIsRunning;
    private Thread mWorkerThread;
    private PipelineCycle mCycle;
    private boolean mNeedStopCycle = false;

    private long mCycleCounter;

    private PipelineCycleJobsFinalizer mCycleJobsFinalizer;
    private PipelineCycleOperationStatesProcessor mCycleOperationStatesProcessor;
    private PipelineCycleResultBuilder mCycleResultBuilder;
    private PipelineCycleResultValidator mCycleResultValidator;
    private PipelineCycleResultProcessor mCycleResultProcessor;

    private LinkedBlockingQueue<Boolean> mCanRunNextCycleQueue;

    private Pipeline() {
        mCycleCounter = 0;

        mIsRunning = false;

        mCycleJobsFinalizer = new SimpleCycleJobsFinalizer();
        mCycleOperationStatesProcessor = new SimpleCycleOperationStatesProcessor();
        mCycleResultBuilder = new SimpleCycleResultBuilder();
        mCycleResultValidator = new SimpleCycleResultValidator();
        mCycleResultProcessor = new SimpleCycleResultProcessor();

        int operationIdsCounter = 1;

        mCycle = new PipelineCycle();
        mCycle.addOperation(new EmptyOperation(String.valueOf(++operationIdsCounter)));
        mCycle.addOperation(new WaitOperation(String.valueOf(++operationIdsCounter)));
        mCycle.addOperation(new DetectDeviceMovementOperation(String.valueOf(++operationIdsCounter)));
        mCycle.addOperation(new WaitOperation(String.valueOf(++operationIdsCounter)));
        mCycle.addOperation(new RecognizePersonOperation(String.valueOf(++operationIdsCounter)));

        mCanRunNextCycleQueue = new LinkedBlockingQueue<>();
    }

    public static synchronized Pipeline get() {
        if (sInstance == null) {
            sInstance = new Pipeline();
        }

        return sInstance;
    }

    public void start(Context context, OnTaskSuccess<Void> onSuccess, OnTaskError<ServiceError> error) {
        Log.d("TAG", "Pipeline-start(): " + Thread.currentThread().getId());

        mCycleCounter = 0;

        setNeedStopCycle(false);

        mWorkerThread = new Thread(() -> {
            while (!needStopCycle()) {
                runCycleOnce(context);

                try {
                    mCanRunNextCycleQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        mWorkerThread.start();

        mIsRunning = true;

        onSuccess.onSuccess(null);
    }

    public void stop(Context context, OnTaskSuccess<Void> onSuccess, OnTaskError<ServiceError> error) {
        Log.d("TAG", "Pipeline->stop(): " + Thread.currentThread().getId());

        setNeedStopCycle(true);

        if (mWorkerThread != null) {
            try {
                mWorkerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        mIsRunning = false;

//        if (mWorkerThread != null) {
//            mWorkerThread.interrupt();
//            try {
//                mWorkerThread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        Log.d("TAG", "Pipeline->stop()->CYCLES_PASSED: " + mCycleCounter);

        onSuccess.onSuccess(null);
    }

    public boolean isRunning() {
        Log.d("TAG", "Pipeline->isRunning()");

        return mIsRunning;
    }

    public void scheduleJob(PipelineJob job) {
        mCycle.scheduleJob(job);
    }

    private void runCycleOnce(Context context) {
        Thread cycleThread = mCycle.run();
        try {
            cycleThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            ++mCycleCounter;

            mCycleJobsFinalizer.finalize(context, mCycle.getCurrentCycleJobs());
            mCycleOperationStatesProcessor.process(context, mCycle.getCurrentCycleOperationStates());
            JSONObject cycleResult = mCycleResultBuilder.build(context, mCycle.getCurrentCycleOperationStates());
            boolean resultValid = mCycleResultValidator.isValid(cycleResult, mCycle);
            if (resultValid) {
                mCycleResultProcessor.process(
                        context,
                        cycleResult,
                        mCycle.getCurrentCycleOperationStates(),
                        data -> {
                            try {
                                mCanRunNextCycleQueue.put(true);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> {
                            Log.d("TAG", "Pipeline->runCycleOnce()->CYCLE_RESULT_PROCESSOR_ERROR");

                            try {
                                mCanRunNextCycleQueue.put(true);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                );
            } else {
                Log.d("TAG", "Pipeline->RESULT_IS_NOT_VALID");

                try {
                    mCanRunNextCycleQueue.put(true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Log.d("TAG", "===> PIPELINE_CYCLE_FINISHED: " + mCycleCounter);
        }

//        mCycleJobsFinalizer.finalize(context, mCycle.getCurrentCycleJobs());
//        mCycleOperationStatesProcessor.process(context, mCycle.getCurrentCycleOperationStates());
//        JSONObject cycleResult = mCycleResultBuilder.build(context, mCycle.getCurrentCycleOperationStates());
//        boolean resultValid = mCycleResultValidator.isValid(cycleResult, mCycle);
//        if (resultValid) {
//            mCycleResultProcessor.process(context, cycleResult);
//        } else {
//            Log.d("TAG", "Pipeline->RESULT_IS_NOT_VALID");
//        }
//
//        Log.d("TAG", "===> PIPELINE_CYCLE_FINISHED: " + mCycleCounter);
    }

    private synchronized void setNeedStopCycle(boolean needStop) {
        mNeedStopCycle = needStop;
    }

    private synchronized boolean needStopCycle() {
        return mNeedStopCycle;
    }

    //    public void start(Context context, OnTaskSuccess<Void> onSuccess, OnTaskError<ServiceError> error) {
//        Log.d("TAG", "Pipeline-start(): " + Thread.currentThread().getId());
//
//        setNeedStopCycle(false);
//
//        mWorkerThread = new Thread(() -> {
//            Thread cycleThread = mCycle.run();
//            try {
//                cycleThread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                ++mCycleCounter;
//            }
//
//            mCycleJobsFinalizer.finalize(context, mCycle.getCurrentCycleJobs());
//            mCycleOperationStatesProcessor.process(context, mCycle.getCurrentCycleOperationStates());
//            JSONObject cycleResult = mCycleResultBuilder.build(context, mCycle.getCurrentCycleOperationStates());
//            boolean resultValid = mCycleResultValidator.isValid(cycleResult, mCycle);
//            if (resultValid) {
//                mCycleResultProcessor.process(context, cycleResult);
//            } else {
//                Log.d("TAG", "Pipeline->RESULT_IS_NOT_VALID");
//            }
//
//            // ===
////            while (!needStopCycle()) {
////                Thread cycleThread = mCycle.run();
////                try {
////                    cycleThread.join();
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                    break;
////                } finally {
////                    ++mCycleCounter;
////                }
////
////                finalizeCurrentCycleJobs();
////            }
//            // ===
//
//            Log.d("TAG", "===> PIPELINE_CYCLE_FINISHED: " + mCycleCounter);
//        });
//        mWorkerThread.start();
//
//        mIsRunning = true;
//
//        onSuccess.onSuccess(null);
//    }

    //    private synchronized void processCurrentCycleOperationStates() {
//        Log.d("TAG", "Pipeline->processCurrentCycleOperationStates(): " + Thread.currentThread().getId());
//
//        List<PipelineOperationState> states = mCycle.getCurrentCycleOperationStates();
//        Log.d("TAG", "Pipeline->processCurrentCycleOperationStates()->STATES_COUNT: " + states.size());
//
//        for (int i = 0; i < states.size(); ++i) {
//            PipelineOperationState state = states.get(i);
//
//            if (state.operation().type().equalsIgnoreCase(DetectDeviceMovementOperation.TYPE)) {
//                DetectDeviceMovementOperationStatus status = (DetectDeviceMovementOperationStatus) state.status();
//
//                Log.d("TAG", "Pipeline->processCurrentCycleOperationStates()->DETECT_MOVEMENT_RUNNING: " + status.deviceMovementRunning());
//                Log.d("TAG", "Pipeline->processCurrentCycleOperationStates()->MOVEMENT_DETECTED: " + status.movementDetected());
//            }
//        }
//    }

    //    private synchronized void finalizeCurrentCycleJobs() {
//        Log.d("TAG", "Pipeline->finalizeCurrentCycleJobs()");
//
//        PipelineJobs jobs = mCycle.getCurrentCycleJobs();
//        List<PipelineJob> jobsList = jobs.getAllJobs();
//        Log.d("TAG", "Pipeline->finalizeCurrentCycleJobs()->JOBS_COUNT: " + jobsList.size());
//        for (int i = 0; i < jobsList.size(); ++i) {
//            PipelineJob job = jobsList.get(i);
//            if (job.error() != null) {
//                job.errorCallback().onError(job.error());
//            } else if (job.finished()) {
//                job.successCallback().onSuccess(job.result());
//            }
//        }
//    }
}


//package com.vision.services.surveillance.pipeline;
//
//import android.util.Log;
//
//import com.vision.common.data.service_error.ServiceError;
//import com.vision.common.data.service_generic_callbacks.OnTaskError;
//import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
//import com.vision.services.surveillance.pipeline.commons.data.pipeline_cycle.PipelineCycle;
//import com.vision.services.surveillance.pipeline.commons.data.pipeline_cycle.PipelineCycle_V2;
//import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_job.PipelineJob;
//import com.vision.services.surveillance.pipeline.jobs.OperationOneJob;
//import com.vision.services.surveillance.pipeline.jobs.OperationTwoJob;
//import com.vision.services.surveillance.pipeline.operations.detect_device_movement.operation.DetectDeviceMovementOperation;
//
//import java.util.List;
//
//
//public class Pipeline {
//    private static Pipeline sInstance;
//
//    private boolean mIsRunning;
//    private Thread mWorkerThread;
//    private PipelineCycle_V2 mCycle;
////    private PipelineCycle.OnCycleFinished mFinishedCallback = () -> {
////        Log.d("TAG",
////                "FINISHED_CALLBACK: " +
////                        Thread.currentThread().getId() + " - " +
////                        Thread.currentThread().isInterrupted() + " - " +
////                        needStopCycle());
////
////        if (!needStopCycle()) {
////            mCycle.run(get().mFinishedCallback);
////        } else {
////            Log.d("TAG", "====> INTERRUPTED");
////        }
////    };
//    private boolean mNeedStopCycle = false;
//
//    private Pipeline() {
//        mIsRunning = false;
//
//        // ===
//        mCycle = new PipelineCycle_V2();
//
//        mCycle.addOperation((jobs, onSuccess, onError) -> {
//            Log.d("TAG", "Operation_1: " + Thread.currentThread().getId());
//
//            List<PipelineJob> operationJobs = jobs.getAndRemoveJobs(OperationOneJob.TYPE);
//            Log.d("TAG", "Operation_1: " + operationJobs.size());
//
//            onSuccess.onSuccess(true);
//        });
////        mCycle.addOperation((jobs, onSuccess, onError) -> {
////            try {
////                Thread.sleep(1000);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////            onSuccess.onSuccess(true);
////        });
//        mCycle.addOperation((jobs, onSuccess, onError) -> {
//            Log.d("TAG", "Operation_2: " + Thread.currentThread().getId());
//
//            List<PipelineJob> operationJobs = jobs.getAndRemoveJobs(OperationTwoJob.TYPE);
//            Log.d("TAG", "Operation_2: " + operationJobs.size());
//
//            onSuccess.onSuccess(true);
//        });
////        mCycle.addOperation((jobs, onSuccess, onError) -> {
////            try {
////                Thread.sleep(1000);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////            onSuccess.onSuccess(true);
////        });
//        mCycle.addOperation((jobs, onSuccess, onError) -> {
//            Log.d("TAG", "Operation_3: " + Thread.currentThread().getId());
////            try {
////                Thread.sleep(1000);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
//            onSuccess.onSuccess(true);
//        });
//        mCycle.addOperation(new DetectDeviceMovementOperation());
//        // ===
//    }
//
//    public static synchronized Pipeline get() {
//        if (sInstance == null) {
//            sInstance = new Pipeline();
//        }
//
//        return sInstance;
//    }
//
//    public void start(OnTaskSuccess<Void> onSuccess, OnTaskError<ServiceError> error) {
//        Log.d("TAG", "Pipeline-start(): " + Thread.currentThread().getId());
//
//        setNeedStopCycle(false);
//
//        mWorkerThread = new Thread(() -> {
//            Thread cycleThread = mCycle.run();
//            try {
//                cycleThread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                Log.d("TAG", "===> AFTER_CYCLE");
//            }
//
////            mCycle.run(mFinishedCallback);
//        });
//        mWorkerThread.start();
//
//        mIsRunning = true;
//
//        onSuccess.onSuccess(null);
//    }
//
//    public void stop(OnTaskSuccess<Void> onSuccess, OnTaskError<ServiceError> error) {
//        Log.d("TAG", "Pipeline->stop(): " + Thread.currentThread().getId());
//
//        setNeedStopCycle(true);
//
//        mIsRunning = false;
//
//        if (mWorkerThread != null) {
//            mWorkerThread.interrupt();
//            try {
//                mWorkerThread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        onSuccess.onSuccess(null);
//    }
//
//    public boolean isRunning() {
//        Log.d("TAG", "Pipeline->isRunning()");
//
//        return mIsRunning;
//    }
//
//    public void scheduleJob(PipelineJob job) {
//
//    }
//
//    private synchronized void setNeedStopCycle(boolean needStop) {
//        mNeedStopCycle = needStop;
//    }
//
//    private synchronized boolean needStopCycle() {
//        return mNeedStopCycle;
//    }
//
////    private void runCycle(PipelineCycle.OnCycleFinished finishedCallback) {
////        mCycle.run(finishedCallback);
////    }
//}
