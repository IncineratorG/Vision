package com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_cycle_result_processor;

import android.content.Context;

import org.json.JSONObject;

public interface PipelineCycleResultProcessor {
    void process(Context context, JSONObject cycleResult);
}
