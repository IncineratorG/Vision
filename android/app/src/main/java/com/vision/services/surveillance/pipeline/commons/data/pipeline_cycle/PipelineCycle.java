package com.vision.services.surveillance.pipeline.commons.data.pipeline_cycle;

import android.util.Log;

import com.vision.services.surveillance.pipeline.commons.data.pipeline_jobs.PipelineJobs;
import com.vision.services.surveillance.pipeline.commons.data.pipeline_operation_state.PipelineOperationState;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_job.PipelineJob;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation.PipelineOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class PipelineCycle {
    private int mCurrentOperationIndex;
    private List<PipelineOperation> mOperationsList;
    private PipelineJobs mCurrentJobs;
    private PipelineJobs mScheduledJobs;
    private Map<String, PipelineOperationState> mOperationsCycleStates;

    private LinkedBlockingQueue<PipelineOperation> mOperationsQueue;
    private boolean mCycleFinished;

    public PipelineCycle() {
        mCycleFinished = false;

        mOperationsQueue = new LinkedBlockingQueue<>();

        mCurrentOperationIndex = -1;
        mOperationsList = new ArrayList<>();

        mCurrentJobs = new PipelineJobs();
        mScheduledJobs = new PipelineJobs();

        mOperationsCycleStates = new HashMap<>();
    }

    public void addOperation(PipelineOperation operation) {
        mOperationsList.add(operation);
    }

    public void scheduleJob(PipelineJob job) {
        mScheduledJobs.addJob(job);
    }

    public PipelineJobs getCurrentCycleJobs() {
        return mCurrentJobs;
    }

    public List<PipelineOperationState> getCurrentCycleOperationStates() {
        List<PipelineOperationState> states = new ArrayList<>();

        for (Map.Entry<String, PipelineOperationState> cycleStatesEntry : mOperationsCycleStates.entrySet()) {
            states.add(cycleStatesEntry.getValue());
        }

        return states;
    }

    public Thread run() {
        setCurrentCycleJobs();
        prepareCycleState();
        queueNextOperation();

        Thread thread = new Thread(() -> {
            while (!cycleFinished()) {
                try {
                    PipelineOperation queuedOperation = mOperationsQueue.take();
                    queuedOperation.run(
                            mCurrentJobs,
                            (data) -> {
                                mOperationsCycleStates.put(data.operation().id(), data);
                                queueNextOperation();
                            },
                            (error) -> {
                                Log.d("TAG", "PipelineCycle->ERROR_OCCURRED");
                            }
                    );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        return thread;
    }

    private void queueNextOperation() {
        ++mCurrentOperationIndex;
        if (mCurrentOperationIndex >= mOperationsList.size()) {
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

    private void setCurrentCycleJobs() {
        Log.d("TAG", "PipelineCycle->setCurrentCycleJobs(): " + mScheduledJobs.distinctJobsCount());

        mCurrentJobs.copyFrom(mScheduledJobs);
        mScheduledJobs.clear();
    }

    private void prepareCycleState() {
        mCurrentOperationIndex = -1;
        mOperationsQueue.clear();
        setCycleFinished(false);
        mOperationsCycleStates.clear();
    }

    private synchronized boolean cycleFinished() {
        return mCycleFinished;
    }

    private synchronized void setCycleFinished(boolean finished) {
        mCycleFinished = finished;
    }
}
