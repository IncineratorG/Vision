package com.vision.common.services.surveillance.data.service_requests.requests;


import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.services.surveillance.data.service_requests.types.SurveillanceServiceRequestTypes;

public class SurveillanceServiceRequests {
    public static ServiceRequest testRequest(String senderDeviceName) {
        return new ServiceRequest(senderDeviceName, SurveillanceServiceRequestTypes.TEST_REQUEST, null);
    }

    public static ServiceRequest testRequestWithPayload(String senderDeviceName, String valueOne, String valueTwo) {
//        JSONObject payload = new JSONObject();
        return null;
    }
}
