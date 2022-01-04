package com.vision.services.surveillance.pipeline.commons.data.pipeline_jobs;

import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_job.PipelineJob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PipelineJobs {
    private Map<String, List<PipelineJob>> mJobs;

    public PipelineJobs() {
        mJobs = new HashMap<>();
    }

    public void addJob(PipelineJob job) {
        List<PipelineJob> jobs = mJobs.get(job.type());
        if (jobs == null) {
            jobs = new ArrayList<>();
        }
        jobs.add(job);

        mJobs.put(job.type(), jobs);
    }

    public List<PipelineJob> getAndRemoveJobs(String type) {
        List<PipelineJob> jobs = mJobs.get(type);
        if (jobs == null) {
            jobs = new ArrayList<>();
        }

        mJobs.remove(type);

        return jobs;
    }
}
