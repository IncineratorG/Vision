package com.vision.common.services.surveillance.data.service_requests.response_payloads;


import com.vision.common.services.surveillance.data.service_requests.response_payloads.payloads.GetDeviceAvailableActionsResponsePayload;
import com.vision.common.services.surveillance.data.service_requests.response_payloads.payloads.TestRequestWithPayloadResponsePayload;

import org.json.JSONObject;

import java.util.List;

public class SurveillanceServiceResponsePayloads {
    public static TestRequestWithPayloadResponsePayload testRequestWithPayloadResponsePayload(String resultOne) {
        return new TestRequestWithPayloadResponsePayload(resultOne);
    }

    public static TestRequestWithPayloadResponsePayload testRequestWithPayloadResponsePayload(JSONObject jsonObject) {
        return new TestRequestWithPayloadResponsePayload(jsonObject);
    }

    public static GetDeviceAvailableActionsResponsePayload getDeviceAvailableActionsResponsePayload(List<String> actionsList) {
        return new GetDeviceAvailableActionsResponsePayload(actionsList);
    }

    public static GetDeviceAvailableActionsResponsePayload getDeviceAvailableActionsResponsePayload(JSONObject jsonObject) {
        return new GetDeviceAvailableActionsResponsePayload(jsonObject);
    }
}
