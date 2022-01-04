package com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_job;

import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;

public interface PipelineJob {
    String id();
    String type();
    boolean finished();
    void setFinished(boolean finished);
    Object result();
    void setResult(Object result);
    Object error();
    void setError(Object error);
    OnTaskSuccess<Object> successCallback();
    OnTaskError<Object> errorCallback();
}
