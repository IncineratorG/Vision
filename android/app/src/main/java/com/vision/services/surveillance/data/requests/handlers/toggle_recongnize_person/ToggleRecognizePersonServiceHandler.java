package com.vision.services.surveillance.data.requests.handlers.toggle_recongnize_person;


import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_request_handler.ServiceRequestHandler;
import com.vision.services.firebase_communication.FBSCommunicationService;
import com.vision.services.firebase_paths.FBSPathsService;
import com.vision.services.surveillance.SurveillanceService;
import com.vision.services.surveillance.data.requests.payloads.SurveillanceServiceRequestPayloads;
import com.vision.services.surveillance.data.requests.payloads.payloads.ToggleRecognizePersonRequestPayload;
import com.vision.services.surveillance.data.responses.payloads.SurveillanceServiceResponsePayloads;
import com.vision.services.surveillance.data.responses.payloads.payloads.ErrorResponsePayload;
import com.vision.services.surveillance.data.responses.payloads.payloads.ToggleRecognizePersonResponsePayload;

import java.util.List;

public class ToggleRecognizePersonServiceHandler implements ServiceRequestHandler {
    @Override
    public void handle(Context context, ServiceRequest request) {
        Log.d("tag", "ToggleRecognizePersonServiceHandler->handle(): " + request.stringify());

        ToggleRecognizePersonRequestPayload requestPayload =
                SurveillanceServiceRequestPayloads.toggleRecognizePersonRequestPayload(request.payload());
        Log.d("tag", "ToggleRecognizePersonServiceHandler->handle()->CAMERA_TYPE: " + requestPayload.cameraType());

        SurveillanceService surveillanceService = SurveillanceService.get();

        String currentGroupName = surveillanceService.currentGroupName();
        String currentGroupPassword = surveillanceService.currentGroupPassword();
        String currentDeviceName = surveillanceService.currentDeviceName();

        String requestSenderDeviceName = request.senderDeviceName();

        // ===
        String cameraType = requestPayload.cameraType();
        if (surveillanceService.isRecognizePersonWithCameraServiceRunning(cameraType)) {
            surveillanceService.stopRecognizePersonWithCamera(
                    context,
                    requestPayload.cameraType(),
                    (result) -> {
                        boolean frontCameraRecognizeServiceRunning = surveillanceService.isRecognizePersonWithFrontCameraServiceRunning();
                        boolean backCameraRecognizeServiceRunning = surveillanceService.isRecognizePersonWithBackCameraServiceRunning();

                        ToggleRecognizePersonResponsePayload responsePayload =
                                SurveillanceServiceResponsePayloads.toggleRecognizePersonResponsePayload(
                                        frontCameraRecognizeServiceRunning, backCameraRecognizeServiceRunning
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
                    },
                    (error) -> {
                        Log.d("tag", "ToggleRecognizePersonServiceHandler->handle()->ERROR: " + error.code() + " - " + error.message());

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
            surveillanceService.startRecognizePersonWithCamera(
                    context,
                    requestPayload.cameraType(),
                    (result) -> {
                        boolean frontCameraRecognizeServiceRunning = surveillanceService.isRecognizePersonWithFrontCameraServiceRunning();
                        boolean backCameraRecognizeServiceRunning = surveillanceService.isRecognizePersonWithBackCameraServiceRunning();

                        ToggleRecognizePersonResponsePayload responsePayload =
                                SurveillanceServiceResponsePayloads.toggleRecognizePersonResponsePayload(
                                        frontCameraRecognizeServiceRunning, backCameraRecognizeServiceRunning
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
                    },
                    (error) -> {
                        Log.d("tag", "ToggleRecognizePersonServiceHandler->handle()->ERROR: " + error.code() + " - " + error.message());

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
            Log.d("tag", "ToggleRecognizePersonServiceHandler->handle()->BAD_REQUEST_KEY: " + request.stringify());
        }
    }
}


//package com.vision.services.surveillance.data.requests.handlers.toggle_recongnize_person;
//
//
//import android.content.Context;
//import android.util.Log;
//
//import com.vision.common.data.service_request.ServiceRequest;
//import com.vision.common.data.service_response.ServiceResponse;
//import com.vision.common.interfaces.service_request_handler.ServiceRequestHandler;
//import com.vision.services.firebase_communication.FBSCommunicationService;
//import com.vision.services.firebase_paths.FBSPathsService;
//import com.vision.services.surveillance.SurveillanceService;
//import com.vision.services.surveillance.data.requests.payloads.SurveillanceServiceRequestPayloads;
//import com.vision.services.surveillance.data.requests.payloads.payloads.ToggleRecognizePersonRequestPayload;
//import com.vision.services.surveillance.data.responses.payloads.SurveillanceServiceResponsePayloads;
//import com.vision.services.surveillance.data.responses.payloads.payloads.ErrorResponsePayload;
//import com.vision.services.surveillance.data.responses.payloads.payloads.ToggleRecognizePersonResponsePayload;
//
//import java.util.List;
//
//public class ToggleRecognizePersonServiceHandler implements ServiceRequestHandler {
//    @Override
//    public void handle(Context context, ServiceRequest request) {
//        Log.d("tag", "ToggleRecognizePersonServiceHandler->handle(): " + request.stringify());
//
//        ToggleRecognizePersonRequestPayload requestPayload =
//                SurveillanceServiceRequestPayloads.toggleRecognizePersonRequestPayload(request.payload());
//        Log.d("tag", "ToggleRecognizePersonServiceHandler->handle()->CAMERA_TYPE: " + requestPayload.cameraType());
//
//        SurveillanceService surveillanceService = SurveillanceService.get();
//
//        String currentGroupName = surveillanceService.currentGroupName();
//        String currentGroupPassword = surveillanceService.currentGroupPassword();
//        String currentDeviceName = surveillanceService.currentDeviceName();
//
//        String requestSenderDeviceName = request.senderDeviceName();
//
//        if (surveillanceService.isRecognizePersonServiceRunning()) {
//            surveillanceService.stopRecognizePersonWithCamera(
//                    context,
//                    requestPayload.cameraType(),
//                    (result) -> {
//                        boolean frontCameraRecognizeServiceRunning = surveillanceService.isRecognizePersonWithFrontCameraServiceRunning();
//                        boolean backCameraRecognizeServiceRunning = surveillanceService.isRecognizePersonWithBackCameraServiceRunning();
//
//                        ToggleRecognizePersonResponsePayload responsePayload =
//                                SurveillanceServiceResponsePayloads.toggleRecognizePersonResponsePayload(
//                                        frontCameraRecognizeServiceRunning, backCameraRecognizeServiceRunning
//                                );
//
//                        ServiceResponse response = new ServiceResponse(
//                                ServiceResponse.TYPE_RESULT,
//                                request.id(),
//                                responsePayload.jsonObject()
//                        );
//
//                        surveillanceService.sendResponse(
//                                currentGroupName,
//                                currentGroupPassword,
//                                requestSenderDeviceName,
//                                response
//                        );
//                    },
//                    (error) -> {
//                        Log.d("tag", "ToggleRecognizePersonServiceHandler->handle()->ERROR: " + error.code() + " - " + error.message());
//
//                        ErrorResponsePayload errorResponsePayload =
//                                SurveillanceServiceResponsePayloads.errorResponsePayload(SurveillanceService.NAME, error);
//
//                        ServiceResponse errorResponse = new ServiceResponse(
//                                ServiceResponse.TYPE_ERROR,
//                                request.id(),
//                                errorResponsePayload.jsonObject()
//                        );
//
//                        surveillanceService.sendResponse(
//                                currentGroupName,
//                                currentGroupPassword,
//                                requestSenderDeviceName,
//                                errorResponse
//                        );
//                    }
//            );
//        } else {
//            surveillanceService.startRecognizePersonWithCamera(
//                    context,
//                    requestPayload.cameraType(),
//                    (result) -> {
//                        boolean frontCameraRecognizeServiceRunning = surveillanceService.isRecognizePersonWithFrontCameraServiceRunning();
//                        boolean backCameraRecognizeServiceRunning = surveillanceService.isRecognizePersonWithBackCameraServiceRunning();
//
//                        ToggleRecognizePersonResponsePayload responsePayload =
//                                SurveillanceServiceResponsePayloads.toggleRecognizePersonResponsePayload(
//                                        frontCameraRecognizeServiceRunning, backCameraRecognizeServiceRunning
//                                );
//
//                        ServiceResponse response = new ServiceResponse(
//                                ServiceResponse.TYPE_RESULT,
//                                request.id(),
//                                responsePayload.jsonObject()
//                        );
//
//                        surveillanceService.sendResponse(
//                                currentGroupName,
//                                currentGroupPassword,
//                                requestSenderDeviceName,
//                                response
//                        );
//                    },
//                    (error) -> {
//                        Log.d("tag", "ToggleRecognizePersonServiceHandler->handle()->ERROR: " + error.code() + " - " + error.message());
//
//                        ErrorResponsePayload errorResponsePayload =
//                                SurveillanceServiceResponsePayloads.errorResponsePayload(SurveillanceService.NAME, error);
//
//                        ServiceResponse errorResponse = new ServiceResponse(
//                                ServiceResponse.TYPE_ERROR,
//                                request.id(),
//                                errorResponsePayload.jsonObject()
//                        );
//
//                        surveillanceService.sendResponse(
//                                currentGroupName,
//                                currentGroupPassword,
//                                requestSenderDeviceName,
//                                errorResponse
//                        );
//                    }
//            );
//        }
//
//        List<String> requestsPath = FBSPathsService.get().requestsPath(currentGroupName, currentGroupPassword, currentDeviceName);
//        if (request.key() != null) {
//            FBSCommunicationService.get().removeValueFromList(requestsPath, request.key());
//        } else {
//            Log.d("tag", "ToggleRecognizePersonServiceHandler->handle()->BAD_REQUEST_KEY: " + request.stringify());
//        }
//    }
//}
