package com.vision.common.services.surveillance.data.notifications.sender.firebase;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.vision.common.data.service_notification.ServiceNotification;
import com.vision.common.interfaces.service_notification_sender.ServiceNotificationSender;

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
    public void sendNotificationToAll(ServiceNotification notification) {
        Log.d("tag","FBSNotificationSender->sendToAll()");
    }
}
