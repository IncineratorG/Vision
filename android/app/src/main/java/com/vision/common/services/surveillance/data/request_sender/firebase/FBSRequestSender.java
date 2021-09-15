package com.vision.common.services.surveillance.data.request_sender.firebase;


import android.util.Log;

import com.vision.common.services.firebase.FBSService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.interfaces.service_request_sender.callbacks.OnDeliveredCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnErrorCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnResponseCallback;
import com.vision.common.interfaces.service_request_sender.ServiceRequestSender;

import java.util.List;

public class FBSRequestSender implements ServiceRequestSender {
    public FBSRequestSender() {

    }

    @Override
    public void sendRequest(String receiverLogin,
                            ServiceRequest request,
                            OnDeliveredCallback deliveredCallback,
                            OnResponseCallback onResponseCallback,
                            OnErrorCallback onErrorCallback) {
        Log.d("tag", "FBSRequestSender->sendRequest(): " + receiverLogin + " - " + request.stringify());

        List<String> receiverRequestsPath = FBSPathsService.get().receiverRequestsPath(receiverLogin);

        FBSService.get().addValueToList(receiverRequestsPath, request.stringify());
    }
}
