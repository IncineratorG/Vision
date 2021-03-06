package com.vision.services.surveillance.requests.handlers.is_device_alive;

import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_request_handler.ServiceRequestHandler;
import com.vision.services.firebase_communication.FBSCommunicationService;
import com.vision.services.firebase_paths.FBSPathsService;
import com.vision.services.surveillance.SurveillanceService;
import com.vision.services.surveillance.responses.payloads.SurveillanceServiceResponsePayloads;
import com.vision.services.surveillance.responses.payloads.payloads.IsDeviceAliveResponsePayload;

import java.util.List;

public class IsDeviceAliveServiceHandler implements ServiceRequestHandler {
    @Override
    public void handle(Context context, ServiceRequest request) {
        Log.d("tag", "IsDeviceAliveServiceHandler->handle(): " + request.stringify());

        SurveillanceService surveillanceService = SurveillanceService.get();

        String currentGroupName = surveillanceService.currentGroupName();
        String currentGroupPassword = surveillanceService.currentGroupPassword();
        String currentDeviceName = surveillanceService.currentDeviceName();

        String requestSenderDeviceName = request.senderDeviceName();

        // ===
        IsDeviceAliveResponsePayload responsePayload =
                SurveillanceServiceResponsePayloads.isDeviceAliveResponsePayload(true);

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
            Log.d("tag", "IsDeviceAliveServiceHandler->handle()->BAD_REQUEST_KEY: " + request.stringify());
        }

        // ===
//        int a = 2 / 0;
        // ===
    }
}
