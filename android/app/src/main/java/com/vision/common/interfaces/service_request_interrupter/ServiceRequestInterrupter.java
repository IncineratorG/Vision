package com.vision.common.interfaces.service_request_interrupter;


public interface ServiceRequestInterrupter {
    boolean cancelRequest(String requestId);
}
