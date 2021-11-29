package com.vision.services.camera.data.state;

import android.util.Log;

import com.vision.common.interfaces.service_state.ServiceState;

import org.json.JSONException;
import org.json.JSONObject;

public class CameraServiceState implements ServiceState {
    public static final String STATE_ID = "DeviceMovementServiceState";

    private final String ID_FIELD = "id";
    private final String IS_FRONT_CAMERA_RECOGNIZE_PERSON_RUNNING = "isFrontCameraRecognizePersonRunning";
    private final String IS_BACK_CAMERA_RECOGNIZE_PERSON_RUNNING = "isBackCameraRecognizePersonRunning";
    private final String IMAGE_ROTATION_DEGREES = "imageRotationDegrees";

    private JSONObject mStateData;

    public CameraServiceState(boolean isFrontCameraRecognizePersonRunning,
                              boolean isBackCameraRecognizePersonRunning,
                              int imageRotationDegrees) {
        mStateData = new JSONObject();
        try {
            mStateData.put(ID_FIELD, STATE_ID);
            mStateData.put(IS_FRONT_CAMERA_RECOGNIZE_PERSON_RUNNING, isFrontCameraRecognizePersonRunning);
            mStateData.put(IS_BACK_CAMERA_RECOGNIZE_PERSON_RUNNING, isBackCameraRecognizePersonRunning);
            mStateData.put(IMAGE_ROTATION_DEGREES, imageRotationDegrees);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public CameraServiceState(CameraServiceState other) {
        try {
            mStateData = new JSONObject(other.mStateData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public CameraServiceState(String serializedState) {
        try {
            mStateData = new JSONObject(serializedState);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isFrontCameraRecognizePersonRunning() {
        if (mStateData == null) {
            Log.d("tag", "CameraServiceState->isFrontCameraRecognizePersonRunning()->STATE_DATA_IS_NULL");
            return false;
        }

        boolean isFrontCameraRecognizePersonRunning = false;
        try {
            isFrontCameraRecognizePersonRunning = mStateData.getBoolean(IS_FRONT_CAMERA_RECOGNIZE_PERSON_RUNNING);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isFrontCameraRecognizePersonRunning;
    }

    public boolean isBackCameraRecognizePersonRunning() {
        if (mStateData == null) {
            Log.d("tag", "CameraServiceState->isBackCameraRecognizePersonRunning()->STATE_DATA_IS_NULL");
            return false;
        }

        boolean isBackCameraRecognizePersonRunning = false;
        try {
            isBackCameraRecognizePersonRunning = mStateData.getBoolean(IS_BACK_CAMERA_RECOGNIZE_PERSON_RUNNING);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isBackCameraRecognizePersonRunning;
    }

    public int imageRotationDegrees() {
        if (mStateData == null) {
            Log.d("tag", "CameraServiceState->imageRotationDegrees()->STATE_DATA_IS_NULL");
            return 0;
        }

        int imageRotationDegrees = 0;
        try {
            imageRotationDegrees = mStateData.getInt(IMAGE_ROTATION_DEGREES);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imageRotationDegrees;
    }

    @Override
    public String stateId() {
        return STATE_ID;
    }

    @Override
    public ServiceState copy() {
        return new CameraServiceState(this);
    }

    @Override
    public String stringify() {
        return mStateData.toString();
    }
}
