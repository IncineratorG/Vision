package com.vision.common.data.service_request_callbacks;


import com.vision.common.interfaces.service_request_sender.callbacks.OnDeliveredCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnErrorCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnResponseCallback;

public class ServiceRequestCallbacks {
    private OnDeliveredCallback mOnDeliveredCallback;
    private OnResponseCallback mOnResponseCallback;
    private OnErrorCallback mOnErrorCallback;

    public ServiceRequestCallbacks(OnDeliveredCallback onDeliveredCallback,
                                   OnResponseCallback onResponseCallback,
                                   OnErrorCallback onErrorCallback) {
        mOnDeliveredCallback = onDeliveredCallback;
        mOnResponseCallback = onResponseCallback;
        mOnErrorCallback = onErrorCallback;
    }

    public OnDeliveredCallback deliveredCallback() {
        return mOnDeliveredCallback;
    }

    public OnResponseCallback responseCallback() {
        return mOnResponseCallback;
    }

    public OnErrorCallback errorCallback() {
        return mOnErrorCallback;
    }
}
