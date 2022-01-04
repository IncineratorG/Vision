package com.vision.services.surveillance.pipeline.commons.data.pipeline_cycle;

import android.util.Log;

import com.vision.services.surveillance.pipeline.commons.data.pipeline_jobs.PipelineJobs;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_job.PipelineJob;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation.PipelineOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PipelineCycle {
    public interface OnCycleFinished {
        void finished();
    }

    private int mCurrentOperationIndex;
    private List<PipelineOperation> mOperationsList;
    private OnCycleFinished mCycleFinishedCallback;
    private PipelineJobs mCurrentJobs;
    private PipelineJobs mScheduledJobs;

    public PipelineCycle() {
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

    public void run(OnCycleFinished finishedCallback) {
        mCurrentOperationIndex = -1;
        mCycleFinishedCallback = finishedCallback;
        runNextOperation();
    }

    private void runNextOperation() {
        ++mCurrentOperationIndex;
        if (mCurrentOperationIndex >= mOperationsList.size()) {
            Log.d("TAG", "PipelineCycle->run()->ALL_OPERATIONS_FINISHED");
            mCycleFinishedCallback.finished();
            Log.d("TAG", "PipelineCycle->run()->ALL_OPERATIONS_FINISHED->AFTER_FINISHED");
            return;
        }

        PipelineOperation operation = mOperationsList.get(mCurrentOperationIndex);
        operation.run(
                mCurrentJobs,
                (data) -> {
                    Log.d("TAG", "WILL_RUN_NEXT_OPERATION");
                    runNextOperation();
                },
                (error) -> {
                    Log.d("TAG", "ERROR_OCCURRED");
                }
        );
    }
}
