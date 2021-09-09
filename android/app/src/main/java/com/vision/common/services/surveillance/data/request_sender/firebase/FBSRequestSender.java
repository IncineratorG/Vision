package com.vision.common.services.surveillance.data.request_sender.firebase;


import com.vision.common.services.surveillance.data.request.Request;
import com.vision.common.services.surveillance.data.request_sender.OnDeliveredCallback;
import com.vision.common.services.surveillance.data.request_sender.OnErrorCallback;
import com.vision.common.services.surveillance.data.request_sender.OnResponseCallback;
import com.vision.common.services.surveillance.data.request_sender.RequestSender;

public class FBSRequestSender implements RequestSender {
    @Override
    public void sendRequest(Request request,
                            OnDeliveredCallback deliveredCallback,
                            OnResponseCallback onResponseCallback,
                            OnErrorCallback onErrorCallback) {
        
    }
}
