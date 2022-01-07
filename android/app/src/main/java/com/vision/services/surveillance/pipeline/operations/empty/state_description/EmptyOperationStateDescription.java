package com.vision.services.surveillance.pipeline.operations.empty.state_description;

import android.util.Log;

import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation_state_description.PipelineOperationStateDescription;

import org.json.JSONObject;

public class EmptyOperationStateDescription implements PipelineOperationStateDescription {
    public EmptyOperationStateDescription() {

    }

    @Override
    public String name() {
        return "";
    }

    @Override
    public JSONObject toJSONObject() {
        return new JSONObject();
    }

//    @Override
//    public boolean isValid(JSONObject result) {
//        if (result == null) {
//            Log.d("TAG", "EmptyOperationStateDescription->isValid()->RESULT_IS_NULL");
//            return false;
//        }
//
//        return true;
//    }
}
