package com.vision.services.surveillance.pipeline;

import android.util.Log;

import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.surveillance.pipeline.commons.data.pipeline_cycle.PipelineCycle;
import com.vision.services.surveillance.pipeline.commons.data.pipeline_cycle.PipelineCycle_V2;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_job.PipelineJob;
import com.vision.services.surveillance.pipeline.jobs.OperationOneJob;
import com.vision.services.surveillance.pipeline.jobs.OperationTwoJob;
import com.vision.services.surveillance.pipeline.operations.DetectDeviceMovementOperation;

import java.util.List;


public class Pipeline {
    private static Pipeline sInstance;

    private boolean mIsRunning;
    private Thread mWorkerThread;
    private PipelineCycle_V2 mCycle;
    private boolean mNeedStopCycle = false;

    private long mCycleCounter;

    private Pipeline() {
        mCycleCounter = 0;

        mIsRunning = false;

        // ===
        mCycle = new PipelineCycle_V2();

        mCycle.addOperation((jobs, onSuccess, onError) -> {
//            Log.d("TAG", "Operation_1: " + Thread.currentThread().getId());

            List<PipelineJob> operationJobs = jobs.getAndRemoveJobs(OperationOneJob.TYPE);
//            Log.d("TAG", "Operation_1: " + operationJobs.size());

            onSuccess.onSuccess(true);
        });
//        mCycle.addOperation((jobs, onSuccess, onError) -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            onSuccess.onSuccess(true);
//        });
        mCycle.addOperation((jobs, onSuccess, onError) -> {
//            Log.d("TAG", "Operation_2: " + Thread.currentThread().getId());

            List<PipelineJob> operationJobs = jobs.getAndRemoveJobs(OperationTwoJob.TYPE);
//            Log.d("TAG", "Operation_2: " + operationJobs.size());

            onSuccess.onSuccess(true);
        });
//        mCycle.addOperation((jobs, onSuccess, onError) -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            onSuccess.onSuccess(true);
//        });
        mCycle.addOperation((jobs, onSuccess, onError) -> {
//            Log.d("TAG", "Operation_3: " + Thread.currentThread().getId());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            onSuccess.onSuccess(true);
        });
//        mCycle.addOperation(new DetectDeviceMovementOperation());
        // ===
    }

    public static synchronized Pipeline get() {
        if (sInstance == null) {
            sInstance = new Pipeline();
        }

        return sInstance;
    }

    public void start(OnTaskSuccess<Void> onSuccess, OnTaskError<ServiceError> error) {
        Log.d("TAG", "Pipeline-start(): " + Thread.currentThread().getId());

        setNeedStopCycle(false);

        mWorkerThread = new Thread(() -> {
            while (!needStopCycle()) {
                Thread cycleThread = mCycle.run();
                try {
                    cycleThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    ++mCycleCounter;
//                    Log.d("TAG", "===> AFTER_CYCLE: " + mCycleCounter++);
                }
            }

            Log.d("TAG", "===> PIPELINE_CYCLE_FINISHED: " + mCycleCounter);

//            Thread cycleThread = mCycle.run();
//            try {
//                cycleThread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                Log.d("TAG", "===> AFTER_CYCLE");
//            }
        });
        mWorkerThread.start();

        mIsRunning = true;

        onSuccess.onSuccess(null);
    }

    public void stop(OnTaskSuccess<Void> onSuccess, OnTaskError<ServiceError> error) {
        Log.d("TAG", "Pipeline->stop(): " + Thread.currentThread().getId());

        setNeedStopCycle(true);

        mIsRunning = false;

        if (mWorkerThread != null) {
            mWorkerThread.interrupt();
            try {
                mWorkerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        onSuccess.onSuccess(null);
    }

    public boolean isRunning() {
        Log.d("TAG", "Pipeline->isRunning()");

        return mIsRunning;
    }

    public void scheduleJob(PipelineJob job) {

    }

    private synchronized void setNeedStopCycle(boolean needStop) {
        mNeedStopCycle = needStop;
    }

    private synchronized boolean needStopCycle() {
        return mNeedStopCycle;
    }
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
//import com.vision.services.surveillance.pipeline.operations.DetectDeviceMovementOperation;
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
