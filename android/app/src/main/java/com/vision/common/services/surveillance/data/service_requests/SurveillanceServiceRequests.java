package com.vision.common.services.surveillance.data.service_requests;


import com.vision.common.services.surveillance.data.request.Request;
import com.vision.common.services.surveillance.data.service_request_types.SurveillanceServiceRequestTypes;

public class SurveillanceServiceRequests {
    public Request testRequest() {
        return new Request(SurveillanceServiceRequestTypes.TEST_REQUEST, null);
    }
}
