package com.vision.common.services.surveillance.data.request_sender;


import com.vision.common.services.surveillance.data.request.Request;

public interface RequestSender {
    void sendRequest(Request request,
                     OnDeliveredCallback deliveredCallback,
                     OnResponseCallback onResponseCallback,
                     OnErrorCallback onErrorCallback);
}
