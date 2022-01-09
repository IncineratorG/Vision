package com.vision.services.surveillance.pipeline.operations.recognize_person.state_description;

import android.util.Log;

import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_operation_state_description.PipelineOperationStateDescription;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RecognizePersonOperationStateDescription implements PipelineOperationStateDescription {
    public static final String NAME_FIELD = "recognizePerson";
    public static final String BACK_CAMERA_RECOGNIZE_PERSON_RUNNING_FIELD = "backCameraRecognizePersonRunning";
    public static final String FRONT_CAMERA_RECOGNIZE_PERSON_RUNNING_FIELD = "frontCameraRecognizePersonRunning";

    private boolean mBackCameraRecognizePersonRunning;
    private boolean mFrontCameraRecognizePersonRunning;

    public RecognizePersonOperationStateDescription() {
        mBackCameraRecognizePersonRunning = false;
        mFrontCameraRecognizePersonRunning = false;
    }

    public RecognizePersonOperationStateDescription(boolean backCameraRecognizePersonRunning,
                                                    boolean frontCameraRecognizePersonRunning) {
        mBackCameraRecognizePersonRunning = backCameraRecognizePersonRunning;
        mFrontCameraRecognizePersonRunning = frontCameraRecognizePersonRunning;
    }

    public RecognizePersonOperationStateDescription(RecognizePersonOperationStateDescription other) {
        mBackCameraRecognizePersonRunning = other.mBackCameraRecognizePersonRunning;
        mFrontCameraRecognizePersonRunning = other.mFrontCameraRecognizePersonRunning;
    }

    @Override
    public Map<String, Object> toServiceObject() {
        Map<String, Object> serviceObject = new HashMap<>();

        Map<String, Object> stateDescription = new HashMap<>();
        stateDescription.put(BACK_CAMERA_RECOGNIZE_PERSON_RUNNING_FIELD, mBackCameraRecognizePersonRunning);
        stateDescription.put(FRONT_CAMERA_RECOGNIZE_PERSON_RUNNING_FIELD, mFrontCameraRecognizePersonRunning);

        serviceObject.put(NAME_FIELD, stateDescription);

        return serviceObject;
    }

    @Override
    public String name() {
        return NAME_FIELD;
    }

    @Override
    public boolean includeInResult() {
        return true;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(BACK_CAMERA_RECOGNIZE_PERSON_RUNNING_FIELD, mBackCameraRecognizePersonRunning);
            jsonObject.put(FRONT_CAMERA_RECOGNIZE_PERSON_RUNNING_FIELD, mFrontCameraRecognizePersonRunning);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    public boolean isValid(JSONObject stateResult) {
        if (stateResult == null) {
            Log.d("TAG", "RecognizePersonOperationStateDescription->isValid()->RESULT_IS_NULL");
            return false;
        }

        if (!stateResult.has(BACK_CAMERA_RECOGNIZE_PERSON_RUNNING_FIELD)) {
            Log.d("TAG", "RecognizePersonOperationStateDescription->isValid()->INSUFFICIENT_FIELD: " + BACK_CAMERA_RECOGNIZE_PERSON_RUNNING_FIELD);
            return false;
        }

        if (!stateResult.has(FRONT_CAMERA_RECOGNIZE_PERSON_RUNNING_FIELD)) {
            Log.d("TAG", "RecognizePersonOperationStateDescription->isValid()->INSUFFICIENT_FIELD: " + FRONT_CAMERA_RECOGNIZE_PERSON_RUNNING_FIELD);
            return false;
        }

        return true;
    }
}
