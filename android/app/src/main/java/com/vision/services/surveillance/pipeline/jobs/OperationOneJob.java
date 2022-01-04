package com.vision.services.surveillance.pipeline.jobs;

import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_job.PipelineJob;

public class OperationOneJob implements PipelineJob {
    public static final String TYPE = "OperationOneJob";

    private String mId;

    public OperationOneJob(String id) {
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
