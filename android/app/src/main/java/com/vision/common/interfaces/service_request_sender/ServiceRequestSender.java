package com.vision.common.interfaces.service_request_sender;


import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestDeliveredCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestErrorCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestResponseCallback;

public interface ServiceRequestSender {
    void sendRequest(String groupName,
                     String groupPassword,
                     String receiverDeviceName,
                     ServiceRequest request,
                     OnRequestDeliveredCallback onDeliveredCallback,
                     OnRequestResponseCallback onResponseCallback,
                     OnRequestErrorCallback onErrorCallback);
}
