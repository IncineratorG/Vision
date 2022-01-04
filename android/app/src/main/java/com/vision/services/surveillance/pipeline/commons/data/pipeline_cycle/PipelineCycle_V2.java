package com.vision.services.surveillance.pipeline.commons.data.pipeline_cycle;

import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.CallbackImpl;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.PromiseImpl;
import com.vision.services.surveillance.pipeline.commons.data.pipeline_jobs.PipelineJobs;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_job.PipelineJob;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation.PipelineOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class PipelineCycle_V2 {
    public interface OnCycleFinished {
        void finished();
    }

    private int mCurrentOperationIndex;
    private List<PipelineOperation> mOperationsList;
    private OnCycleFinished mCycleFinishedCallback;
    private PipelineJobs mCurrentJobs;
    private PipelineJobs mScheduledJobs;

    private LinkedBlockingQueue<PipelineOperation> mOperationsQueue;
    private boolean mCycleFinished;

    public PipelineCycle_V2() {
        mCycleFinished = false;

        mOperationsQueue = new LinkedBlockingQueue<>();

        mCurrentOperationIndex = -1;
        mOperationsList = new ArrayList<>();

        mCurrentJobs = new PipelineJobs();
        mScheduledJobs = new PipelineJobs();
    }

    public void addOperation(PipelineOperation operation) {
        mOperationsList.add(operation);
    }

    public void scheduleJob(PipelineJob job) {
        mScheduledJobs.addJob(job);
    }

    public Thread run() {
        mCurrentOperationIndex = -1;

        mOperationsQueue.clear();

        setCycleFinished(false);

        queueNextOperation();

        Thread thread = new Thread(() -> {
            while (!cycleFinished()) {
                try {
                    PipelineOperation queedOperation = mOperationsQueue.take();
                    queedOperation.run(
                            mCurrentJobs,
                            (data) -> {
//                                Log.d("TAG", "WILL_RUN_NEXT_OPERATION");
                                queueNextOperation();
                            },
                            (error) -> {
                                Log.d("TAG", "ERROR_OCCURRED");
                            }
                    );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

//                Log.d("TAG", "=======> AFTER <======");
            }
        });
        thread.start();

        return thread;
    }

    private void queueNextOperation() {
        ++mCurrentOperationIndex;
        if (mCurrentOperationIndex >= mOperationsList.size()) {
//            Log.d("TAG", "PipelineCycle->queueNextOperation()->ALL_OPERATIONS_FINISHED");
            setCycleFinished(true);
            return;
        }

        PipelineOperation operation = mOperationsList.get(mCurrentOperationIndex);
        try {
            mOperationsQueue.put(operation);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized boolean cycleFinished() {
        return mCycleFinished;
    }

    private synchronized void setCycleFinished(boolean finished) {
        mCycleFinished = finished;
    }

    private boolean runNextOperation() {
        ++mCurrentOperationIndex;
        if (mCurrentOperationIndex >= mOperationsList.size()) {
            Log.d("TAG", "PipelineCycle->run()->ALL_OPERATIONS_FINISHED");
            return true;
        }

        PipelineOperation operation = mOperationsList.get(mCurrentOperationIndex);
        try {
            mOperationsQueue.put(operation);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        PipelineOperation operation = mOperationsList.get(mCurrentOperationIndex);
//        operation.run(
//                mCurrentJobs,
//                (data) -> {
//                    Log.d("TAG", "WILL_RUN_NEXT_OPERATION");
////                    runNextOperation();
//                },
//                (error) -> {
//                    Log.d("TAG", "ERROR_OCCURRED");
//                }
//        );

        return false;
    }
}
