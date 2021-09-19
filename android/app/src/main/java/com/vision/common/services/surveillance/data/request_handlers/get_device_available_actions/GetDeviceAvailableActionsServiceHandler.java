package com.vision.common.services.surveillance.data.request_handlers.get_device_available_actions;

import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_request_handler.ServiceRequestHandler;
import com.vision.common.services.firebase.FBSService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.common.services.surveillance.data.service_requests.response_payloads.SurveillanceServiceResponsePayloads;
import com.vision.common.services.surveillance.data.service_requests.response_payloads.payloads.GetDeviceAvailableActionsResponsePayload;

import java.util.ArrayList;
import java.util.List;

public class GetDeviceAvailableActionsServiceHandler implements ServiceRequestHandler {
    @Override
    public void handle(Context context, ServiceRequest request) {
        Log.d("tag", "GetDeviceAvailableActionsServiceHandler->handle(): " + request.stringify());

        SurveillanceService surveillanceService = SurveillanceService.get();

        String currentGroupName = surveillanceService.currentGroupName();
        String currentGroupPassword = surveillanceService.currentGroupPassword();
        String currentDeviceName = surveillanceService.currentDeviceName();

        String requestSenderDeviceName = request.senderDeviceName();

        // ===
        GetDeviceAvailableActionsResponsePayload responsePayload =
                SurveillanceServiceResponsePayloads.getDeviceAvailableActionsResponsePayload(new ArrayList<>());

        ServiceResponse response = new ServiceResponse(request.id(), responsePayload.jsonObject());

        surveillanceService.sendResponse(
                currentGroupName,
                currentGroupPassword,
                requestSenderDeviceName,
                response
        );
        // ===

        List<String> requestsPath = FBSPathsService.get().requestsPath(currentGroupName, currentGroupPassword, currentDeviceName);
        if (request.key() != null) {
            FBSService.get().removeValueFromList(requestsPath, request.key());
        } else {
            Log.d("tag", "GetDeviceAvailableActionsServiceHandler->handle()->BAD_REQUEST_KEY: " + request.stringify());
        }
    }
}
