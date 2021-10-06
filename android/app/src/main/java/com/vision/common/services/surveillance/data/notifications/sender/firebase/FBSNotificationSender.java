package com.vision.common.services.surveillance.data.notifications.sender.firebase;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vision.common.data.service_notification.ServiceNotification;
import com.vision.common.interfaces.service_notification_sender.ServiceNotificationSender;
import com.vision.common.services.firebase_messaging.FBSMessagingService;
import com.vision.common.services.surveillance.SurveillanceService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FBSNotificationSender implements ServiceNotificationSender {
    private final String URL = "https://fcm.googleapis.com/fcm/send";
    private final int METHOD = Request.Method.POST;
    private final String CONTENT_TYPE = "application/json";
    private final String FIREBASE_SERVER_KEY = "AAAANMMmzyI:APA91bGYvLIW2NzCYruRbejEwT5IzQe5n6bLXUSwTz42qu5lRiqu71zPDIw129OiNuG1knwMLyGsMQpNaeWGRihkSXKw6iJMBWcasJ8ark1K5oCmRvgxIir1PCPcp_fpfPueU6zmcQKZ";

    private RequestQueue mRequestQueue;
    private Map<String, String> mRequestHeaders;

    public FBSNotificationSender(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);

        mRequestHeaders = new HashMap<>();
        mRequestHeaders.put("content-type", CONTENT_TYPE);
        mRequestHeaders.put("authorization", "key=" + FIREBASE_SERVER_KEY);
    }

    @Override
    public void sendNotificationToAll(Context context, ServiceNotification notification) {
        Log.d("tag","FBSNotificationSender->sendToAll()");

        SurveillanceService service = SurveillanceService.get();

        String currentGroupName = service.currentGroupName();
        String currentGroupPassword = service.currentGroupPassword();
        String currentDeviceName = service.currentDeviceName();

        String topic = FBSMessagingService.get().globalTopic(currentGroupName, currentGroupPassword);
//        String title = currentGroupName + "." + currentDeviceName;

        Log.d("tag", "FBSNotificationSender->sendNotificationToAll(): " + topic);

        send(notification, topic);
    }

    private void send(ServiceNotification notification, String topic) {
        JSONObject notificationJson = new JSONObject();
        try {
            notificationJson.put("to", "/topics/" + topic);

//            JSONObject data = new JSONObject();
//            data.put("title", "Data Title");
//            data.put("message", "Data Message");
//            data.put("Custom Id", "My Custom Id");
//            notificationJson.put("data", data);
            JSONObject data = new JSONObject();
//            data.put("title", title);
            data.put("notification", notification.stringify());
            notificationJson.put("data", data);

            JsonObjectRequest request = new JsonObjectRequest(
                    METHOD,
                    URL,
                    notificationJson,
                    response -> {
                        Log.d("tag", "FBSNotificationSender->send()->onResponse");
                    },
                    error -> {
                        Log.d("tag", "FBSNotificationSender->send()->onError: " + error.networkResponse);
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() {
                    return mRequestHeaders;
                }
            };

            mRequestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
