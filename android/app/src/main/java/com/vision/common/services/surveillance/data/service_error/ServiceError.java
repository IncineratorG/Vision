package com.vision.common.services.surveillance.data.service_error;


public class ServiceError {
    private String mCode;
    private String mMessage;

    public ServiceError(String code, String message) {
        mCode = code;
        mMessage = message;
    }

    public String code() {
        return mCode;
    }

    public String message() {
        return mMessage;
    }
}
