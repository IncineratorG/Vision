package com.vision.services.surveillance.pipeline.jobs;

import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_job.PipelineJob;

public class StartDetectDeviceMovementJob implements PipelineJob {
    public static final String TYPE = "StartDetectDeviceMovementJob";

    private String mId;
    private String mResult;
    private boolean mFinished;
    private OnTaskSuccess<Object> mSuccessCallback;
    private OnTaskError<Object> mErrorCallback;

    public StartDetectDeviceMovementJob(String id,
                                        OnTaskSuccess<Object> successCallback,
                                        OnTaskError<Object> errorCallback) {
        mId = id;
        mSuccessCallback = successCallback;
        mErrorCallback = errorCallback;

        mFinished = false;
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
        return mFinished;
    }

    @Override
    public void setFinished(boolean finished) {
        mFinished = finished;
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
        return mSuccessCallback;
    }

    @Override
    public OnTaskError<Object> errorCallback() {
        return mErrorCallback;
    }
}
