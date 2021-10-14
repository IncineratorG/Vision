package com.vision.common.services.surveillance.data.requests.handlers.start_recognize_person;

import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_request_handler.ServiceRequestHandler;
import com.vision.common.services.firebase_communication.FBSCommunicationService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.common.services.surveillance.data.requests.payloads.SurveillanceServiceRequestPayloads;
import com.vision.common.services.surveillance.data.requests.payloads.payloads.StartRecognizePersonRequestPayload;
import com.vision.common.services.surveillance.data.responses.payloads.SurveillanceServiceResponsePayloads;
import com.vision.common.services.surveillance.data.responses.payloads.payloads.StartRecognizePersonResponsePayload;

import java.util.List;

public class StartRecognizePersonServiceHandler implements ServiceRequestHandler {
    @Override
    public void handle(Context context, ServiceRequest request) {
        Log.d("tag", "StartRecognizePersonServiceHandler->handle(): " + request.stringify());

        StartRecognizePersonRequestPayload requestPayload =
                SurveillanceServiceRequestPayloads.startRecognizePersonRequestPayload(request.payload());
        Log.d("tag", "StartRecognizePersonServiceHandler->handle()->CAMERA_TYPE: " + requestPayload.cameraType());

        SurveillanceService surveillanceService = SurveillanceService.get();

        String currentGroupName = surveillanceService.currentGroupName();
        String currentGroupPassword = surveillanceService.currentGroupPassword();
        String currentDeviceName = surveillanceService.currentDeviceName();

        String requestSenderDeviceName = request.senderDeviceName();

        // ===
        Log.d("tag", "StartRecognizePersonServiceHandler->handle(): NOT_IMPLEMENTED");

        StartRecognizePersonResponsePayload responsePayload =
                SurveillanceServiceResponsePayloads.startRecognizePersonResponsePayload(
                        false, false
                );

        ServiceResponse response = new ServiceResponse(
                ServiceResponse.TYPE_RESULT,
                request.id(),
                responsePayload.jsonObject()
        );

        surveillanceService.sendResponse(
                currentGroupName,
                currentGroupPassword,
                requestSenderDeviceName,
                response
        );
        // ===

        List<String> requestsPath = FBSPathsService.get().requestsPath(currentGroupName, currentGroupPassword, currentDeviceName);
        if (request.key() != null) {
            FBSCommunicationService.get().removeValueFromList(requestsPath, request.key());
        } else {
            Log.d("tag", "StartRecognizePersonServiceHandler->handle()->BAD_REQUEST_KEY: " + request.stringify());
        }
    }
}
