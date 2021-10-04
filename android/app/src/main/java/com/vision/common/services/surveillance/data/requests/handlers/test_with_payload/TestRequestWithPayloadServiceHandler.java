package com.vision.common.services.surveillance.data.requests.handlers.test_with_payload;


import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_request_handler.ServiceRequestHandler;
import com.vision.common.services.firebase_communication.FBSCommunicationService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.common.services.surveillance.data.responses.payloads.SurveillanceServiceResponsePayloads;
import com.vision.common.services.surveillance.data.responses.payloads.payloads.TestRequestWithPayloadResponsePayload;

import java.util.List;

public class TestRequestWithPayloadServiceHandler implements ServiceRequestHandler {
    @Override
    public void handle(Context context, ServiceRequest request) {
        Log.d("tag", "TestRequestWithPayloadServiceHandler->handle(): " + request.stringify());

        SurveillanceService surveillanceService = SurveillanceService.get();

        String currentGroupName = surveillanceService.currentGroupName();
        String currentGroupPassword = surveillanceService.currentGroupPassword();
        String currentDeviceName = surveillanceService.currentDeviceName();

        // ===
        String requestSenderDeviceName = request.senderDeviceName();

        TestRequestWithPayloadResponsePayload responsePayload =
                SurveillanceServiceResponsePayloads.testRequestWithPayloadResponsePayload("My Result");

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
            Log.d("tag", "TestRequestServiceHandler->handle()->BAD_REQUEST_KEY: " + request.stringify());
        }
    }
}
