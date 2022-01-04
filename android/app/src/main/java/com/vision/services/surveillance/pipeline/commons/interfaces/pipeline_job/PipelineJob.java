package com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_job;

public interface PipelineJob {
    String id();
    String type();
    boolean finished();
}
