package com.vision.common.interfaces.stateful_service;

import android.content.Context;

import com.vision.common.interfaces.service.Service;
import com.vision.common.interfaces.service_state.ServiceState;
import com.vision.common.interfaces.service_state_change_listener.ServiceStateChangeListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class StatefulService implements Service {
    private Map<String, ServiceStateChangeListener> mStateChangeListeners = new HashMap<>();

    public abstract ServiceState getCurrentState();

    public String addStateChangeListener(ServiceStateChangeListener listener) {
        String listenerId = UUID.randomUUID().toString() + "_" + System.currentTimeMillis();

        mStateChangeListeners.put(listenerId, listener);

        return listenerId;
    }

    public boolean removeStateChangeListener(String listenerId) {
        mStateChangeListeners.remove(listenerId);
        return true;
    }

    protected synchronized void notifyStateListeners(Context context) {
        ServiceState currentState = getCurrentState().copy();

        for (Map.Entry<String, ServiceStateChangeListener> listenerEntry : mStateChangeListeners.entrySet()) {
            ServiceStateChangeListener listener = listenerEntry.getValue();
            if (listener != null) {
                listener.onStateChange(context, currentState);
            }
        }
    }
}
