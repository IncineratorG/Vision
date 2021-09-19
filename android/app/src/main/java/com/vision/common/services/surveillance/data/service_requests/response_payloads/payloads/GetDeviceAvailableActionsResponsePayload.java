package com.vision.common.services.surveillance.data.service_requests.response_payloads.payloads;

import com.vision.common.data.service_response_payload.ServiceResponsePayload;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetDeviceAvailableActionsResponsePayload implements ServiceResponsePayload {
    private final String ACTIONS_LIST_FIELD = "actionsList";

    private JSONObject mJsonObject;

    public GetDeviceAvailableActionsResponsePayload(List<String> actionsList) {
        mJsonObject = new JSONObject();

        JSONArray actionsArray = new JSONArray(actionsList);
        try {
            mJsonObject.put(ACTIONS_LIST_FIELD, actionsArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public GetDeviceAvailableActionsResponsePayload(JSONObject jsonObject) {
        try {
            mJsonObject = new JSONObject(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<String> actionsList() {
        if (mJsonObject == null) {
            return null;
        }

        List<String> actionsList = new ArrayList<>();
        try {
            JSONArray array = mJsonObject.getJSONArray(ACTIONS_LIST_FIELD);
            for (int i = 0; i < array.length(); ++i) {
                actionsList.add(array.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return actionsList;
    }

    @Override
    public JSONObject jsonObject() {
        return mJsonObject;
    }
}
