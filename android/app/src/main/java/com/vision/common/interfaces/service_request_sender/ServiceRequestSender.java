package com.vision.common.interfaces.service_request_sender;


import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.interfaces.service_request_sender.callbacks.OnDeliveredCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnErrorCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnResponseCallback;

public interface ServiceRequestSender {
    void sendRequest(String groupName,
                     String groupPassword,
                     String receiverDeviceName,
                     ServiceRequest request,
                     OnDeliveredCallback onDeliveredCallback,
                     OnResponseCallback onResponseCallback,
                     OnErrorCallback onErrorCallback);

}
