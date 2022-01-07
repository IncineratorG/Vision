package com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_cycle_result_builder;

import android.content.Context;

import com.vision.services.surveillance.pipeline.commons.data.pipeline_operation_state.PipelineOperationState;

import org.json.JSONObject;

import java.util.List;

public interface PipelineCycleResultBuilder {
    JSONObject build(Context context, List<PipelineOperationState> states);
}
