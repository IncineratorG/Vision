package com.vision.common.services.surveillance.data.requests.sender.firebase;


import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.vision.common.services.firebase_communication.FBSCommunicationService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestDeliveredCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestErrorCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestResponseCallback;
import com.vision.common.interfaces.service_request_sender.ServiceRequestSender;
import com.vision.common.services.surveillance.data.service_errors.SurveillanceServiceErrors;

import java.util.List;

public class FBSRequestSender implements ServiceRequestSender {
    public FBSRequestSender() {

    }

    @Override
    public void sendRequest(String groupName,
                            String groupPassword,
                            String receiverDeviceName,
                            ServiceRequest request,
                            OnRequestDeliveredCallback onDeliveredCallback,
                            OnRequestResponseCallback onResponseCallback,
                            OnRequestErrorCallback onErrorCallback) {
        Log.d("tag", "FBSRequestSender->sendRequest(): " + receiverDeviceName + " - " + request.stringify());

        List<String> receiverRequestsPath = FBSPathsService.get().requestsPath(groupName, groupPassword, receiverDeviceName);

        OnCompleteListener<Void> onCompleteListener = task -> {
            Log.d("tag", "FBRequestSender->send()");
//            onDeliveredCallback.handle();
        };

        OnFailureListener onFailureListener = e -> {
            onErrorCallback.handle(SurveillanceServiceErrors.requestFailure());
        };

        FBSCommunicationService fbsService = FBSCommunicationService.get();
        fbsService.addValueToList(receiverRequestsPath, request.stringify(), onCompleteListener, onFailureListener);
    }
}
