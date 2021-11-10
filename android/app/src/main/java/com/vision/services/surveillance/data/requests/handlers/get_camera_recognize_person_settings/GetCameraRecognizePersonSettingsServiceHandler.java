package com.vision.services.surveillance.data.requests.handlers.get_camera_recognize_person_settings;

import android.content.Context;
import android.util.Log;

import com.vision.common.constants.AppConstants;
import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_request_handler.ServiceRequestHandler;
import com.vision.services.camera.CameraService;
import com.vision.services.firebase_communication.FBSCommunicationService;
import com.vision.services.firebase_paths.FBSPathsService;
import com.vision.services.surveillance.SurveillanceService;
import com.vision.services.surveillance.data.requests.payloads.SurveillanceServiceRequestPayloads;
import com.vision.services.surveillance.data.requests.payloads.payloads.GetCameraRecognizePersonSettingsRequestPayload;
import com.vision.services.surveillance.data.responses.payloads.SurveillanceServiceResponsePayloads;
import com.vision.services.surveillance.data.responses.payloads.payloads.GetCameraRecognizePersonSettingsResponsePayload;

import java.util.List;

public class GetCameraRecognizePersonSettingsServiceHandler implements ServiceRequestHandler {
    @Override
    public void handle(Context context, ServiceRequest request) {
        Log.d("tag", "GetCameraRecognizePersonSettingsServiceHandler->handle(): " + request.type());

        GetCameraRecognizePersonSettingsRequestPayload requestPayload =
                SurveillanceServiceRequestPayloads.getCameraRecognizePersonSettingsRequestPayload(request.payload());

        SurveillanceService surveillanceService = SurveillanceService.get();

        String currentGroupName = surveillanceService.currentGroupName();
        String currentGroupPassword = surveillanceService.currentGroupPassword();
        String currentDeviceName = surveillanceService.currentDeviceName();

        String requestSenderDeviceName = request.senderDeviceName();

        CameraService cameraService = CameraService.get();

        // ===
        String imageQuality = AppConstants.CAMERA_IMAGE_QUALITY_HIGH;
        CameraService.OnImageTaken imageTakenCallback = (base64String) -> {
            Log.d("tag", "GetCameraRecognizePersonSettingsServiceHandler->OnImageTaken()");

            GetCameraRecognizePersonSettingsResponsePayload responsePayload =
                    SurveillanceServiceResponsePayloads.getCameraRecognizePersonSettingsResponsePayload(base64String);

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
        };
        CameraService.OnImageTakeError errorCallback = (code, message) -> {
            Log.d("tag", "GetCameraRecognizePersonSettingsServiceHandler->OnImageTakeError(): " + code + " - " + message);
        };

        if (requestPayload.cameraType().equalsIgnoreCase("front")) {
            cameraService.takeFrontCameraImage(imageQuality, imageTakenCallback, errorCallback);
        } else if (requestPayload.cameraType().equalsIgnoreCase("back")) {
            cameraService.takeBackCameraImage(imageQuality, imageTakenCallback, errorCallback);
        } else {
            Log.d("tag", "GetCameraRecognizePersonSettingsServiceHandler->handle()->UNKNOWN_CAMERA_TYPE: " + requestPayload.cameraType());
        }
        // ===

        List<String> requestsPath = FBSPathsService.get().currentRequestsPath();
        if (request.key() != null) {
            FBSCommunicationService.get().removeValueFromList(requestsPath, request.key());
        } else {
            Log.d("tag", "GetCameraRecognizePersonSettingsServiceHandler->handle()->BAD_REQUEST_KEY: " + request.stringify());
        }
    }
}
