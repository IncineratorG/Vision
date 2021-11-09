package com.vision.services.surveillance.data.requests.handlers.take_front_camera_image;


import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_request_handler.ServiceRequestHandler;
import com.vision.services.camera.CameraService;
import com.vision.services.firebase_communication.FBSCommunicationService;
import com.vision.services.firebase_paths.FBSPathsService;
import com.vision.services.surveillance.SurveillanceService;
import com.vision.services.surveillance.data.requests.payloads.SurveillanceServiceRequestPayloads;
import com.vision.services.surveillance.data.requests.payloads.payloads.TakeFrontCameraImageRequestPayload;
import com.vision.services.surveillance.data.responses.payloads.SurveillanceServiceResponsePayloads;
import com.vision.services.surveillance.data.responses.payloads.payloads.TakeFrontCameraImageResponsePayload;

import java.util.List;

public class TakeFrontCameraImageServiceHandler implements ServiceRequestHandler {
    @Override
    public void handle(Context context, ServiceRequest request) {
        Log.d("tag", "TakeFrontCameraImageServiceHandler->handle(): " + request.stringify());

        TakeFrontCameraImageRequestPayload requestPayload =
                SurveillanceServiceRequestPayloads.takeFrontCameraImageRequestPayload(request.payload());
        Log.d("tag", "TakeFrontCameraImageServiceHandler->handle()->REQUESTED_IMAGE_QUALITY: " + requestPayload.imageQuality());

        SurveillanceService surveillanceService = SurveillanceService.get();

        String currentGroupName = surveillanceService.currentGroupName();
        String currentGroupPassword = surveillanceService.currentGroupPassword();
        String currentDeviceName = surveillanceService.currentDeviceName();

        String requestSenderDeviceName = request.senderDeviceName();

        // ===
        CameraService cameraService = CameraService.get();

        cameraService.takeFrontCameraImage(
                requestPayload.imageQuality(),
                (base64String) -> {
                    Log.d("tag", "TakeFrontCameraImageServiceHandler->OnImageTaken()");

                    TakeFrontCameraImageResponsePayload responsePayload =
                            SurveillanceServiceResponsePayloads.takeFrontCameraImageResponsePayload(base64String);

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
                (code, message) -> {
                    Log.d("tag", "TakeFrontCameraImageServiceHandler->OnImageTakeError(): " + code + " - " + message);
                }
        );
        // ===

        List<String> requestsPath = FBSPathsService.get().requestsPath(currentGroupName, currentGroupPassword, currentDeviceName);
        if (request.key() != null) {
            FBSCommunicationService.get().removeValueFromList(requestsPath, request.key());
        } else {
            Log.d("tag", "TakeFrontCameraImageServiceHandler->handle()->BAD_REQUEST_KEY: " + request.stringify());
        }
    }
}
