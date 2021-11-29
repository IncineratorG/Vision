package com.vision.services.device_movement.data.state;

import android.util.Log;

import com.vision.common.interfaces.service_state.ServiceState;

import org.json.JSONException;
import org.json.JSONObject;

public class DeviceMovementServiceState implements ServiceState {
    public static final String STATE_ID = "DeviceMovementServiceState";

    private final String ID_FIELD = "id";
    private final String IS_RUNNING_FIELD = "isRunning";

    private JSONObject mStateData;

    public DeviceMovementServiceState(boolean isRunning) {
        mStateData = new JSONObject();

        try {
            mStateData.put(ID_FIELD, STATE_ID);
            mStateData.put(IS_RUNNING_FIELD, isRunning);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public DeviceMovementServiceState(DeviceMovementServiceState other) {
        try {
            mStateData = new JSONObject(other.mStateData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public DeviceMovementServiceState(JSONObject stateData) {
        try {
            mStateData = new JSONObject(stateData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public DeviceMovementServiceState(String serializedState) {
        try {
            mStateData = new JSONObject(serializedState);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setIsRunning(boolean isRunning) {
        if (mStateData == null) {
            Log.d("tag", "DeviceMovementServiceState->setIsRunning()->STATE_DATA_IS_NULL");
            return;
        }

        try {
            mStateData.put(IS_RUNNING_FIELD, isRunning);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isRunning() {
        if (mStateData == null) {
            Log.d("tag", "DeviceMovementServiceState->isRunning()->STATE_DATA_IS_NULL");
            return false;
        }

        boolean isRunning = false;
        try {
            isRunning = mStateData.getBoolean(IS_RUNNING_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isRunning;
    }

    @Override
    public String stateId() {
        return STATE_ID;
    }

    @Override
    public ServiceState copy() {
        return new DeviceMovementServiceState(this);
    }

    @Override
    public String stringify() {
        return mStateData.toString();
    }
}
