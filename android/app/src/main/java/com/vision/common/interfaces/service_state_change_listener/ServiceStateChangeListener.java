package com.vision.common.interfaces.service_state_change_listener;

import com.vision.common.interfaces.service_state.ServiceState;

public interface ServiceStateChangeListener {
    void onStateChange(ServiceState state);
}
