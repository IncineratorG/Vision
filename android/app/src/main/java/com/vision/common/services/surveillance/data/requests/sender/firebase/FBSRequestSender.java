package com.vision.common.services.surveillance.data.requests.sender.firebase;


import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.vision.common.services.firebase.FBSService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.interfaces.service_request_sender.callbacks.OnDeliveredCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnErrorCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnResponseCallback;
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
                            OnDeliveredCallback onDeliveredCallback,
                            OnResponseCallback onResponseCallback,
                            OnErrorCallback onErrorCallback) {
        Log.d("tag", "FBSRequestSender->sendRequest(): " + receiverDeviceName + " - " + request.stringify());

        List<String> receiverRequestsPath = FBSPathsService.get().requestsPath(groupName, groupPassword, receiverDeviceName);

        OnCompleteListener<Void> onCompleteListener = task -> {
            Log.d("tag", "FBRequestSender->send()");
//            onDeliveredCallback.handle();
        };

        OnFailureListener onFailureListener = e -> {
            onErrorCallback.handle(SurveillanceServiceErrors.requestFailure());
        };

        FBSService fbsService = FBSService.get();
        fbsService.addValueToList(receiverRequestsPath, request.stringify(), onCompleteListener, onFailureListener);
    }
}
