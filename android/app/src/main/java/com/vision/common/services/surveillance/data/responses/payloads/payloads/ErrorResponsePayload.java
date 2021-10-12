package com.vision.common.services.surveillance.data.responses.payloads.payloads;


import com.vision.common.data.service_error.ServiceError;
import com.vision.common.interfaces.service_response_payload.ServiceResponsePayload;

import org.json.JSONException;
import org.json.JSONObject;

public class ErrorResponsePayload implements ServiceResponsePayload {
    private final String SERVICE_NAME_FIELD = "serviceName";
    private final String SERVICE_ERROR_CODE = "serviceErrorCode";
    private final String SERVICE_ERROR_MESSAGE = "serviceErrorMessage";

    private JSONObject mJsonObject;

    public ErrorResponsePayload(String serviceName, ServiceError serviceError) {
        mJsonObject = new JSONObject();

        try {
            mJsonObject.put(SERVICE_NAME_FIELD, serviceName);
            mJsonObject.put(SERVICE_ERROR_CODE, serviceError.code());
            mJsonObject.put(SERVICE_ERROR_MESSAGE, serviceError.message());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ErrorResponsePayload(JSONObject jsonObject) {
        try {
            mJsonObject = new JSONObject(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String serviceName() {
        if (mJsonObject == null) {
            return "";
        }

        String serviceName = "";
        try {
            serviceName = mJsonObject.getString(SERVICE_NAME_FIELD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return serviceName;
    }

    public String serviceErrorCode() {
        if (mJsonObject == null) {
            return "";
        }

        String serviceErrorCode = "";
        try {
            serviceErrorCode = mJsonObject.getString(SERVICE_ERROR_CODE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return serviceErrorCode;
    }

    public String serviceErrorMessage() {
        if (mJsonObject == null) {
            return "";
        }

        String serviceErrorMessage = "";
        try {
            serviceErrorMessage = mJsonObject.getString(SERVICE_ERROR_MESSAGE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return serviceErrorMessage;
    }

    @Override
    public JSONObject jsonObject() {
        return mJsonObject;
    }
}