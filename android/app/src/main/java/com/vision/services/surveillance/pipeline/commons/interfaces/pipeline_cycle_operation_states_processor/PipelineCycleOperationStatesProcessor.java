package com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_cycle_operation_states_processor;

import android.content.Context;

import com.vision.services.surveillance.pipeline.commons.data.pipeline_operation_state.PipelineOperationState;

import java.util.List;

public interface PipelineCycleOperationStatesProcessor {
    void process(Context context, List<PipelineOperationState> states);
}
