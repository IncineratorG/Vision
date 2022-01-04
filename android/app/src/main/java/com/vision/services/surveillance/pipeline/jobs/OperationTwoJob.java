package com.vision.services.surveillance.pipeline.jobs;

import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
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

    @Override
    public boolean finished() {
        return false;
    }

    @Override
    public void setFinished(boolean finished) {

    }

    @Override
    public Object result() {
        return null;
    }

    @Override
    public void setResult(Object result) {

    }

    @Override
    public Object error() {
        return null;
    }

    @Override
    public void setError(Object error) {

    }

    @Override
    public OnTaskSuccess<Object> successCallback() {
        return null;
    }

    @Override
    public OnTaskError<Object> errorCallback() {
        return null;
    }
}
