package com.vision.services.surveillance.pipeline.jobs;

import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_job.PipelineJob;

public class OperationTwoJob implements PipelineJob {
    public static final String TYPE = "OperationTwoJob";

    private String mId;

    public OperationTwoJob(String id) {
        mId = id;
    }

    @Override
    public String id() {
        return mId;
    }

    @Override
    public String type() {
        return TYPE;
    }
}
