package com.vision.common.services.surveillance.data.request_sender.firebase;


import android.util.Log;

import com.vision.common.services.firebase.FBSService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.common.services.surveillance.data.request.Request;
import com.vision.common.services.surveillance.data.request_sender.OnDeliveredCallback;
import com.vision.common.services.surveillance.data.request_sender.OnErrorCallback;
import com.vision.common.services.surveillance.data.request_sender.OnResponseCallback;
import com.vision.common.services.surveillance.data.request_sender.RequestSender;

import java.util.List;

public class FBSRequestSender implements RequestSender {
    public FBSRequestSender() {

    }

    @Override
    public void sendRequest(String receiverLogin,
                            Request request,
                            OnDeliveredCallback deliveredCallback,
                            OnResponseCallback onResponseCallback,
                            OnErrorCallback onErrorCallback) {
        Log.d("tag", "FBSRequestSender->sendRequest(): " + receiverLogin + " - " + request.stringify());

        List<String> receiverRequestsPath = FBSPathsService.get().receiverRequestsPath(receiverLogin);

        FBSService.get().addValueToList(receiverRequestsPath, request.stringify());
    }
}
