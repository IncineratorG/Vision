package com.vision.modules.surveillance.module_actions.payloads.payloads.send_request;


import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.vision.common.data.service_request_payload.ServiceRequestPayload;
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

            default: {
                return null;
            }
        }
    }
}
