package com.vision.common.data.serialized_service_state;

public class SerializedServiceState {
    private String mStateId;
    private String mSerializedState;

    public SerializedServiceState(String stateId, String serializedState) {
        mStateId = stateId;
        mSerializedState = serializedState;
    }

    public boolean isEmpty() {
        if (mStateId == null || mStateId.isEmpty()) {
            return true;
        }

        if (mSerializedState == null || mSerializedState.isEmpty()) {
            return true;
        }

        return false;
    }

    public String stateId() {
        return mStateId;
    }

    public String serializedState() {
        return mSerializedState;
    }
}
