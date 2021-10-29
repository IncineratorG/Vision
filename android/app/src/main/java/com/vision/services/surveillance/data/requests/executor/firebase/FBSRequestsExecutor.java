package com.vision.services.surveillance.data.requests.executor.firebase;


import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_request_handler.ServiceRequestHandler;
import com.vision.services.surveillance.SurveillanceService;
import com.vision.services.surveillance.data.requests.handlers.is_device_alive.IsDeviceAliveServiceHandler;
import com.vision.services.surveillance.data.requests.handlers.take_back_camera_image.TakeBackCameraImageServiceHandler;
import com.vision.services.surveillance.data.requests.handlers.take_fron_camera_image.TakeFrontCameraImageServiceHandler;
import com.vision.services.surveillance.data.requests.handlers.test_with_payload.TestRequestWithPayloadServiceHandler;
import com.vision.services.surveillance.data.requests.handlers.toggle_detect_device_movement.ToggleDetectDeviceMovementServiceHandler;
import com.vision.services.surveillance.data.requests.handlers.toggle_recongnize_person.ToggleRecognizePersonServiceHandler;
import com.vision.services.surveillance.data.requests.handlers.unknown.UnknownRequestServiceHandler;
import com.vision.common.interfaces.service_requests_executor.ServiceRequestsExecutor;
import com.vision.services.surveillance.data.requests.types.SurveillanceServiceRequestTypes;

import java.util.HashMap;
import java.util.Map;

public class FBSRequestsExecutor implements ServiceRequestsExecutor {
    private final String UNKNOWN_REQUEST_HANDLER_KEY = "unknown";
    private Map<String, ServiceRequestHandler> mHandlers;

    public FBSRequestsExecutor() {
        mHandlers = new HashMap<>();
        mHandlers.put(UNKNOWN_REQUEST_HANDLER_KEY, new UnknownRequestServiceHandler());
        mHandlers.put(SurveillanceServiceRequestTypes.TEST_REQUEST_WITH_PAYLOAD, new TestRequestWithPayloadServiceHandler());
        mHandlers.put(SurveillanceServiceRequestTypes.IS_DEVICE_ALIVE, new IsDeviceAliveServiceHandler());
        mHandlers.put(SurveillanceServiceRequestTypes.TAKE_BACK_CAMERA_IMAGE, new TakeBackCameraImageServiceHandler());
        mHandlers.put(SurveillanceServiceRequestTypes.TAKE_FRONT_CAMERA_IMAGE, new TakeFrontCameraImageServiceHandler());
        mHandlers.put(SurveillanceServiceRequestTypes.TOGGLE_DETECT_DEVICE_MOVEMENT, new ToggleDetectDeviceMovementServiceHandler());
        mHandlers.put(SurveillanceServiceRequestTypes.TOGGLE_RECOGNIZE_PERSON, new ToggleRecognizePersonServiceHandler());
    }

    @Override
    public void execute(Context context, String stringifiedRequest, Map<String, Object> params) {
        Log.d("tag", "FBSRequestsExecutor->execute(): " + stringifiedRequest + " - " + (context == null));

        if (!isSafeToProcessRequest(context, stringifiedRequest, params)) {
            Log.d("tag", "FBSRequestsExecutor->execute(): WILL_NOT_PROCESS_REQUEST");
            return;
        }

        ServiceRequest request = getRequestFromString(stringifiedRequest, params);
        sendRequestDeliveredResponse(request);
        handleRequest(context, request);
    }

    private boolean isSafeToProcessRequest(Context context, String stringifiedRequest, Map<String, Object> params) {
        if (context == null) {
            Log.d("tag", "FBSRequestsExecutor->isSafeToProcessRequest(): CONTEXT_IS_NULL");
            return false;
        }

        if (stringifiedRequest == null) {
            Log.d("tag", "FBSRequestsExecutor->isSafeToProcessRequest(): STRINGIFIED_REQUEST_IS_NULL");
            return false;
        }

        if (params == null) {
            Log.d("tag", "FBSRequestsExecutor->isSafeToProcessRequest(): PARAMS_IS_NULL");
            return false;
        }

        if (!params.containsKey("requestKey")) {
            Log.d("tag", "FBSRequestsExecutor->isSafeToProcessRequest(): PARAMS_HAS_NO_KEY");
        }

        return true;
    }

    private ServiceRequest getRequestFromString(String stringifiedRequest, Map<String, Object> params) {
        ServiceRequest request = new ServiceRequest(stringifiedRequest);

        String requestKey = (String) params.get("requestKey");
        if (requestKey != null) {
            request.setKey(requestKey);
        } else {
            Log.d("tag", "FBRequestsExecutor->getRequestFromString(): BAD_REQUEST_KEY");
        }

        return request;
    }

    private void sendRequestDeliveredResponse(ServiceRequest request) {
        SurveillanceService surveillanceService = SurveillanceService.get();

        String currentGroupName = surveillanceService.currentGroupName();
        String currentGroupPassword = surveillanceService.currentGroupPassword();

        String requestSenderDeviceName = request.senderDeviceName();

        ServiceResponse response = new ServiceResponse(
                ServiceResponse.TYPE_RECEIVED,
                request.id(),
                null
        );

        surveillanceService.sendResponse(
                currentGroupName,
                currentGroupPassword,
                requestSenderDeviceName,
                response
        );
    }

    private void handleRequest(Context context, ServiceRequest request) {
        if (mHandlers.containsKey(request.type())) {
            ServiceRequestHandler requestHandler = mHandlers.get(request.type());
            if (requestHandler != null) {
                requestHandler.handle(context, request);
            } else {
                Log.d("tag", "FBRequestsExecutor->handleRequest(): REQUEST_HANDLER_IS_NULL");
            }
        } else {
            ServiceRequestHandler requestHandler = mHandlers.get(UNKNOWN_REQUEST_HANDLER_KEY);
            if (requestHandler != null) {
                requestHandler.handle(context, request);
            } else {
                Log.d("tag", "FBRequestsExecutor->handleRequest(): UNKNOWN_REQUEST_HANDLER_IS_NULL");
            }
        }
    }
}
