package com.vision.common.data.service_request_callbacks;


import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestDeliveredCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestErrorCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestResponseCallback;

public class ServiceRequestCallbacks {
    private OnRequestDeliveredCallback mOnDeliveredCallback;
    private OnRequestResponseCallback mOnResponseCallback;
    private OnRequestErrorCallback mOnErrorCallback;

    public ServiceRequestCallbacks(OnRequestDeliveredCallback onDeliveredCallback,
                                   OnRequestResponseCallback onResponseCallback,
                                   OnRequestErrorCallback onErrorCallback) {
        mOnDeliveredCallback = onDeliveredCallback;
        mOnResponseCallback = onResponseCallback;
        mOnErrorCallback = onErrorCallback;
    }

    public OnRequestDeliveredCallback deliveredCallback() {
        return mOnDeliveredCallback;
    }

    public OnRequestResponseCallback responseCallback() {
        return mOnResponseCallback;
    }

    public OnRequestErrorCallback errorCallback() {
        return mOnErrorCallback;
    }
}
