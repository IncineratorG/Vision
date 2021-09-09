package com.vision.common.services.surveillance.data.request_sender;


import com.vision.common.services.surveillance.data.response.Response;

public interface OnResponseCallback {
    void handle(Response response);
}
