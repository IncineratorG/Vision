package com.vision.common.services.surveillance.data.responses.payloads;


import com.vision.common.data.service_error.ServiceError;
import com.vision.common.services.surveillance.data.responses.payloads.payloads.ErrorResponsePayload;
import com.vision.common.services.surveillance.data.responses.payloads.payloads.IsDeviceAliveResponsePayload;
import com.vision.common.services.surveillance.data.responses.payloads.payloads.StartRecognizePersonResponsePayload;
import com.vision.common.services.surveillance.data.responses.payloads.payloads.StopRecognizePersonResponsePayload;
import com.vision.common.services.surveillance.data.responses.payloads.payloads.TakeBackCameraImageResponsePayload;
import com.vision.common.services.surveillance.data.responses.payloads.payloads.TakeFrontCameraImageResponsePayload;
import com.vision.common.services.surveillance.data.responses.payloads.payloads.TestRequestWithPayloadResponsePayload;
import com.vision.common.services.surveillance.data.responses.payloads.payloads.ToggleDetectDeviceMovementResponsePayload;

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

    public static StartRecognizePersonResponsePayload startRecognizePersonResponsePayload(
            boolean frontCameraRecognizeServiceRunning,
            boolean backCameraRecognizeServiceRunning
    ) {
        return new StartRecognizePersonResponsePayload(
                frontCameraRecognizeServiceRunning, backCameraRecognizeServiceRunning
        );
    }

    public static StartRecognizePersonResponsePayload startRecognizePersonResponsePayload(
            JSONObject jsonObject
    ) {
        return new StartRecognizePersonResponsePayload(jsonObject);
    }


    public static StopRecognizePersonResponsePayload stopRecognizePersonResponsePayload(
            boolean frontCameraRecognizeServiceRunning,
            boolean backCameraRecognizeServiceRunning
    ) {
        return new StopRecognizePersonResponsePayload(
                frontCameraRecognizeServiceRunning, backCameraRecognizeServiceRunning
        );
    }

    public static StopRecognizePersonResponsePayload stopRecognizePersonResponsePayload(
            JSONObject jsonObject
    ) {
        return new StopRecognizePersonResponsePayload(jsonObject);
    }
}
