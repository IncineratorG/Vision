package com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_cycle_result_processor;

import android.content.Context;

import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.surveillance.pipeline.commons.data.pipeline_operation_state.PipelineOperationState;

import org.json.JSONObject;

import java.util.List;

public interface PipelineCycleResultProcessor {
    void process(Context context,
                 JSONObject cycleResult,
                 List<PipelineOperationState> states,
                 OnTaskSuccess<Void> onSuccess,
                 OnTaskError<Object> onError);
}
