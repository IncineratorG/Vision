package com.vision.services.surveillance.requests.handlers.toggle_detect_device_movement;


import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_request_handler.ServiceRequestHandler;
import com.vision.services.firebase_communication.FBSCommunicationService;
import com.vision.services.firebase_paths.FBSPathsService;
import com.vision.services.surveillance.SurveillanceService;
import com.vision.services.surveillance.responses.payloads.SurveillanceServiceResponsePayloads;
import com.vision.services.surveillance.responses.payloads.payloads.ErrorResponsePayload;
import com.vision.services.surveillance.responses.payloads.payloads.ToggleDetectDeviceMovementResponsePayload;

import java.util.List;

public class ToggleDetectDeviceMovementServiceHandler implements ServiceRequestHandler {
    @Override
    public void handle(Context context, ServiceRequest request) {
        Log.d("tag", "ToggleDetectDeviceMovementServiceHandler->handle(): " + request.stringify());

        SurveillanceService surveillanceService = SurveillanceService.get();

        String currentGroupName = surveillanceService.currentGroupName();
        String currentGroupPassword = surveillanceService.currentGroupPassword();
        String currentDeviceName = surveillanceService.currentDeviceName();

        String requestSenderDeviceName = request.senderDeviceName();

        // ===
        if (!surveillanceService.isDetectDeviceMovementServiceRunning()) {
            surveillanceService.startDetectDeviceMovement(
                    context,
                    (result) -> {
                        ToggleDetectDeviceMovementResponsePayload responsePayload =
                                SurveillanceServiceResponsePayloads.toggleDetectDeviceMovementResponsePayload(true);

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
                    },
                    (error) -> {
                        Log.d("tag", "ToggleDetectDeviceMovementServiceHandler->handle()->ERROR: " + error.code() + " - " + error.message());

                        ErrorResponsePayload errorResponsePayload =
                                SurveillanceServiceResponsePayloads.errorResponsePayload(SurveillanceService.NAME, error);

                        ServiceResponse errorResponse = new ServiceResponse(
                                ServiceResponse.TYPE_ERROR,
                                request.id(),
                                errorResponsePayload.jsonObject()
                        );

                        surveillanceService.sendResponse(
                                currentGroupName,
                                currentGroupPassword,
                                requestSenderDeviceName,
                                errorResponse
                        );
                    }
            );
        } else {
            surveillanceService.stopDetectDeviceMovement(
                    context,
                    (result) -> {
                        ToggleDetectDeviceMovementResponsePayload responsePayload =
                                SurveillanceServiceResponsePayloads.toggleDetectDeviceMovementResponsePayload(false);

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
                    },
                    (error) -> {
                        Log.d("tag", "ToggleDetectDeviceMovementServiceHandler->handle()->ERROR: " + error.code() + " - " + error.message());

                        ErrorResponsePayload errorResponsePayload =
                                SurveillanceServiceResponsePayloads.errorResponsePayload(SurveillanceService.NAME, error);

                        ServiceResponse errorResponse = new ServiceResponse(
                                ServiceResponse.TYPE_ERROR,
                                request.id(),
                                errorResponsePayload.jsonObject()
                        );

                        surveillanceService.sendResponse(
                                currentGroupName,
                                currentGroupPassword,
                                requestSenderDeviceName,
                                errorResponse
                        );
                    }
            );
        }
        // ===

        List<String> requestsPath = FBSPathsService.get().requestsPath(currentGroupName, currentGroupPassword, currentDeviceName);
        if (request.key() != null) {
            FBSCommunicationService.get().removeValueFromList(requestsPath, request.key());
        } else {
            Log.d("tag", "ToggleDetectDeviceMovementServiceHandler->handle()->BAD_REQUEST_KEY: " + request.stringify());
        }
    }
}
