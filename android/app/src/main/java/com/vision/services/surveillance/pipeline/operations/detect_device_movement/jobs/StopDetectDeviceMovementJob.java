package com.vision.services.surveillance.pipeline.operations.detect_device_movement.jobs;

import android.content.Context;

import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_job.PipelineJob;

public class StopDetectDeviceMovementJob implements PipelineJob {
    public static final String TYPE = "StopDetectDeviceMovementJob";

    private String mId;
    private boolean mFinished;
    private Context mContext;
    private OnTaskSuccess<Object> mSuccessCallback;
    private OnTaskError<Object> mErrorCallback;

    public StopDetectDeviceMovementJob(String id,
                                       Context context,
                                       OnTaskSuccess<Object> successCallback,
                                       OnTaskError<Object> errorCallback) {
        mId = id;
        mContext = context;

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

    public Context context() {
        return mContext;
    }
}