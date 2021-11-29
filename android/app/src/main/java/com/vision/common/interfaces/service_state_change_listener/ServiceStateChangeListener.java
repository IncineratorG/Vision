package com.vision.common.interfaces.service_state_change_listener;

import android.content.Context;

import com.vision.common.interfaces.service_state.ServiceState;

public interface ServiceStateChangeListener {
    void onStateChange(Context context, ServiceState state);
}
