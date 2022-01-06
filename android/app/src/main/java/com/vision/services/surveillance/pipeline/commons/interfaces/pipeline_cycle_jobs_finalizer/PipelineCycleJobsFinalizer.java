package com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_cycle_jobs_finalizer;

import android.content.Context;

import com.vision.services.surveillance.pipeline.commons.data.pipeline_jobs.PipelineJobs;

public interface PipelineCycleJobsFinalizer {
    void finalize(Context context, PipelineJobs jobs);
}
