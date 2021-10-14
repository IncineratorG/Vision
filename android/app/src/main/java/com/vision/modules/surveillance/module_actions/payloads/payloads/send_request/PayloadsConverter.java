package com.vision.modules.surveillance.module_actions.payloads.payloads.send_request;


import com.facebook.react.bridge.ReadableMap;
import com.vision.common.interfaces.service_request_payload.ServiceRequestPayload;
import com.vision.common.services.surveillance.data.requests.payloads.SurveillanceServiceRequestPayloads;
import com.vision.common.services.surveillance.data.requests.types.SurveillanceServiceRequestTypes;

public class PayloadsConverter {
    public static ServiceRequestPayload toServiceRequestPayload(String requestType, ReadableMap requestPayloadMap) {
        switch (requestType) {
            case (SurveillanceServiceRequestTypes.TEST_REQUEST_WITH_PAYLOAD): {
                String valueOne = requestPayloadMap.getString("valueOne");
                String valueTwo = requestPayloadMap.getString("valueTwo");

                return SurveillanceServiceRequestPayloads.testRequestWithPayloadRequestPayload(valueOne, valueTwo);
            }

            case (SurveillanceServiceRequestTypes.IS_DEVICE_ALIVE): {
                return SurveillanceServiceRequestPayloads.isDeviceAliveRequestPayload();
            }

            case (SurveillanceServiceRequestTypes.TAKE_BACK_CAMERA_IMAGE): {
                String imageQuality = requestPayloadMap.getString("imageQuality");
                if (imageQuality == null) {
                    return null;
                }

                return SurveillanceServiceRequestPayloads.takeBackCameraImageRequestPayload(imageQuality);
            }

            case (SurveillanceServiceRequestTypes.TAKE_FRONT_CAMERA_IMAGE): {
                String imageQuality = requestPayloadMap.getString("imageQuality");
                if (imageQuality == null) {
                    return null;
                }

                return SurveillanceServiceRequestPayloads.takeFrontCameraImageRequestPayload(imageQuality);
            }

            case (SurveillanceServiceRequestTypes.TOGGLE_DETECT_DEVICE_MOVEMENT): {
                return SurveillanceServiceRequestPayloads.toggleDetectDeviceMovementRequestPayload();
            }

            case (SurveillanceServiceRequestTypes.START_RECOGNIZE_PERSON): {
                String cameraType = requestPayloadMap.getString("cameraType");
                if (cameraType == null) {
                    return null;
                }

                return SurveillanceServiceRequestPayloads.startRecognizePersonRequestPayload(cameraType);
            }

            case (SurveillanceServiceRequestTypes.STOP_RECOGNIZE_PERSON): {
                String cameraType = requestPayloadMap.getString("cameraType");
                if (cameraType == null) {
                    return null;
                }

                return SurveillanceServiceRequestPayloads.stopRecognizePersonRequestPayload(cameraType);
            }

            default: {
                return null;
            }
        }
    }
}
