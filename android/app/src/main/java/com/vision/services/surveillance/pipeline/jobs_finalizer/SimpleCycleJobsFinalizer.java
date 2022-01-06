package com.vision.services.surveillance.pipeline.jobs_finalizer;

import android.content.Context;
import android.util.Log;

import com.vision.services.surveillance.pipeline.commons.data.pipeline_jobs.PipelineJobs;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_cycle_jobs_finalizer.PipelineCycleJobsFinalizer;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_job.PipelineJob;

import java.util.List;

public class SimpleCycleJobsFinalizer implements PipelineCycleJobsFinalizer {
    public SimpleCycleJobsFinalizer() {

    }

    @Override
    public void finalize(Context context, PipelineJobs jobs) {
        List<PipelineJob> jobsList = jobs.getAllJobs();
        Log.d("TAG", "SimpleCycleJobsFinalizer->finalize()->JOBS_COUNT: " + jobsList.size());
        for (int i = 0; i < jobsList.size(); ++i) {
            PipelineJob job = jobsList.get(i);
            if (job.error() != null) {
                job.errorCallback().onError(job.error());
            } else if (job.finished()) {
                job.successCallback().onSuccess(job.result());
            }
        }
    }
}
