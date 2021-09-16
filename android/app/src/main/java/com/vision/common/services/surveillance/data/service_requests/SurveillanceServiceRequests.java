package com.vision.common.services.surveillance.data.service_requests;


import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.services.surveillance.data.service_request_types.SurveillanceServiceRequestTypes;

public class SurveillanceServiceRequests {
    public ServiceRequest testRequest(String senderDeviceName) {
        return new ServiceRequest(senderDeviceName, SurveillanceServiceRequestTypes.TEST_REQUEST, null);
    }
}
