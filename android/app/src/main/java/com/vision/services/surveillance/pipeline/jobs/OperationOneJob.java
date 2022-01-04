package com.vision.services.surveillance.pipeline.jobs;

import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_job.PipelineJob;

public class OperationOneJob implements PipelineJob {
    public static final String TYPE = "OperationOneJob";

    private String mId;
    private boolean mIsFinished;

    public OperationOneJob(String id) {
        mId = id;
        mIsFinished = false;
    }

    @Override
    public String id() {
        return mId;
    }

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public boolean finished() {
        return mIsFinished;
    }

    public void setFinished() {
        mIsFinished = true;
    }
}
