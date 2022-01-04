package com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation;

import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.surveillance.pipeline.commons.data.pipeline_jobs.PipelineJobs;

public interface PipelineOperation {
    void run(PipelineJobs jobs, OnTaskSuccess<Object> onSuccess, OnTaskError<Object> onError);
}
