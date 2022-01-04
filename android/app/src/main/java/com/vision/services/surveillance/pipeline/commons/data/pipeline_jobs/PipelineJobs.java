package com.vision.services.surveillance.pipeline.commons.data.pipeline_jobs;

import android.util.Log;

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

    public List<PipelineJob> getJobs(String type) {
        List<PipelineJob> jobs = mJobs.get(type);
        if (jobs == null) {
            jobs = new ArrayList<>();
        }

        return jobs;
    }

    public List<PipelineJob> getAllJobs() {
        Log.d("TAG", "PipelineJobs->getAllJobs(): " + mJobs.size());

        List<PipelineJob> allJobs = new ArrayList<>();
        for (Map.Entry<String, List<PipelineJob>> jobsMapEntry : mJobs.entrySet()) {
            allJobs.addAll(jobsMapEntry.getValue());
        }
        return allJobs;
    }

    public void clear() {
        mJobs.clear();
    }

    public void copyFrom(PipelineJobs other) {
        mJobs.clear();
        mJobs = new HashMap<>(other.mJobs);
    }

    public int distinctJobsCount() {
        return mJobs.size();
    }
}
