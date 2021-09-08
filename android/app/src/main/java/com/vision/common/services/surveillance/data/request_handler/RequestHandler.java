package com.vision.common.services.surveillance.data.request_handler;


import com.vision.common.services.surveillance.data.request.Request;

public interface RequestHandler {
    void handle(Request request);
}
