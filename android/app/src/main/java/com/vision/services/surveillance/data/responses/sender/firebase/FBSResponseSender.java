package com.vision.services.surveillance.data.responses.sender.firebase;


import android.util.Log;

import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_response_sender.ServiceResponseSender;
import com.vision.services.firebase_communication.FBSCommunicationService;
import com.vision.services.firebase_paths.FBSPathsService;

import java.util.List;

public class FBSResponseSender implements ServiceResponseSender {
    public FBSResponseSender() {

    }

    @Override
    public void sendResponse(String groupName, String groupPassword, String receiverDeviceName, ServiceResponse response) {
        Log.d("tag", "FBSResponseSender->sendResponse(): " + response.type());

        List<String> requestSenderResponsePath = FBSPathsService.get().responsesPath(groupName, groupPassword, receiverDeviceName);

        FBSCommunicationService fbsService = FBSCommunicationService.get();
        fbsService.addValueToList(requestSenderResponsePath, response.stringify());
    }
}
