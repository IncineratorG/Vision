package com.vision.common.services.surveillance.data.response_sender.firebase;


import android.util.Log;

import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_response_sender.ServiceResponseSender;
import com.vision.common.services.firebase.FBSService;
import com.vision.common.services.firebase_paths.FBSPathsService;

import java.util.List;

public class FBSResponseSender implements ServiceResponseSender {
    public FBSResponseSender() {

    }

    @Override
    public void sendResponse(String groupName, String groupPassword, String receiverDeviceName, ServiceResponse response) {
        Log.d("tag", "FBSResponseSender->sendResponse()");

        List<String> requestSenderResponsePath = FBSPathsService.get().responsesPath(groupName, groupPassword, receiverDeviceName);

        FBSService fbsService = FBSService.get();
        fbsService.addValueToList(requestSenderResponsePath, response.stringify());
    }
}
