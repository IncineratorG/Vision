package com.vision.common.services.surveillance.data.responses.payloads;


import com.vision.common.data.service_error.ServiceError;
import com.vision.common.services.surveillance.data.responses.payloads.payloads.ErrorResponsePayload;
import com.vision.common.services.surveillance.data.responses.payloads.payloads.IsDeviceAliveResponsePayload;
import com.vision.common.services.surveillance.data.responses.payloads.payloads.TakeBackCameraImageResponsePayload;
import com.vision.common.services.surveillance.data.responses.payloads.payloads.TakeFrontCameraImageResponsePayload;
import com.vision.common.services.surveillance.data.responses.payloads.payloads.TestRequestWithPayloadResponsePayload;
import com.vision.common.services.surveillance.data.responses.payloads.payloads.ToggleDetectDeviceMovementResponsePayload;
import com.vision.common.services.surveillance.data.responses.payloads.payloads.ToggleRecognizePersonResponsePayload;

import org.json.JSONObject;

public class SurveillanceServiceResponsePayloads {
    public static ErrorResponsePayload errorResponsePayload(String serviceName, ServiceError serviceError) {
        return new ErrorResponsePayload(serviceName, serviceError);
    }

    public static ErrorResponsePayload errorResponsePayload(JSONObject jsonObject) {
        return new ErrorResponsePayload(jsonObject);
    }

    public static TestRequestWithPayloadResponsePayload testRequestWithPayloadResponsePayload(String resultOne) {
        return new TestRequestWithPayloadResponsePayload(resultOne);
    }

    public static TestRequestWithPayloadResponsePayload testRequestWithPayloadResponsePayload(JSONObject jsonObject) {
        return new TestRequestWithPayloadResponsePayload(jsonObject);
    }

    public static IsDeviceAliveResponsePayload isDeviceAliveResponsePayload(boolean isAlive) {
        return new IsDeviceAliveResponsePayload(isAlive);
    }

    public static IsDeviceAliveResponsePayload isDeviceAliveResponsePayload(JSONObject jsonObject) {
        return new IsDeviceAliveResponsePayload(jsonObject);
    }

    public static TakeBackCameraImageResponsePayload takeBackCameraImageResponsePayload(String base64Image) {
        return new TakeBackCameraImageResponsePayload(base64Image);
    }

    public static TakeBackCameraImageResponsePayload takeBackCameraImageResponsePayload(JSONObject jsonObject) {
        return new TakeBackCameraImageResponsePayload(jsonObject);
    }

    public static TakeFrontCameraImageResponsePayload takeFrontCameraImageResponsePayload(String base64Image) {
        return new TakeFrontCameraImageResponsePayload(base64Image);
    }

    public static TakeFrontCameraImageResponsePayload takeFrontCameraImageResponsePayload(JSONObject jsonObject) {
        return new TakeFrontCameraImageResponsePayload(jsonObject);
    }

    public static ToggleDetectDeviceMovementResponsePayload toggleDetectDeviceMovementResponsePayload(
            boolean detectDeviceMovementServiceRunning
    ) {
        return new ToggleDetectDeviceMovementResponsePayload(detectDeviceMovementServiceRunning);
    }

    public static ToggleDetectDeviceMovementResponsePayload toggleDetectDeviceMovementResponsePayload(
            JSONObject jsonObject
    ) {
        return new ToggleDetectDeviceMovementResponsePayload(jsonObject);
    }

    public static ToggleRecognizePersonResponsePayload toggleRecognizePersonResponsePayload(
            boolean frontCameraRecognizeServiceRunning,
            boolean backCameraRecognizeServiceRunning
    ) {
        return new ToggleRecognizePersonResponsePayload(
                frontCameraRecognizeServiceRunning, backCameraRecognizeServiceRunning
        );
    }

    public static ToggleRecognizePersonResponsePayload toggleRecognizePersonResponsePayload(
            JSONObject jsonObject
    ) {
        return new ToggleRecognizePersonResponsePayload(jsonObject);
    }
}
