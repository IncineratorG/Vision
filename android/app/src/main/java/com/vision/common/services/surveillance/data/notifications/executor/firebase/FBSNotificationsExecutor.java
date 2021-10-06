package com.vision.common.services.surveillance.data.notifications.executor.firebase;


import android.content.Context;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.vision.common.interfaces.service_notification_handler.ServiceNotificationHandler;
import com.vision.common.interfaces.service_notifications_executor.ServiceNotificationsExecutor;

import java.util.Map;

public class FBSNotificationsExecutor implements ServiceNotificationsExecutor {
    private final String UNKNOWN_NOTIFICATION_HANDLER_KEY = "unknown";
    private Map<String, ServiceNotificationHandler> mHandlers;

    public FBSNotificationsExecutor() {

    }

    @Override
    public void execute(Context context, RemoteMessage notificationMessage, Map<String, Object> params) {
        Log.d("tag", "FBSNotificationsExecutor->execute()");
    }
}
