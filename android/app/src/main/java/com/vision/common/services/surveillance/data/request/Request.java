package com.vision.common.services.surveillance.data.request;


public class Request {
    private String mType;
    private String mPayload;

    public Request() {
        mType = "";
        mPayload = "";
    }

    public Request(String stringifiedRequest) {
        mType = stringifiedRequest;
    }

    public boolean empty() {
        return mType == null || mType.isEmpty();
    }

    public String type() {
        return mType;
    }

    public String payload() {
        return mPayload;
    }
}
