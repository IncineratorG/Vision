package com.vision.common.interfaces.service_notifications_executor;


import android.content.Context;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public interface ServiceNotificationsExecutor {
    void execute(Context context, RemoteMessage notificationMessage, Map<String, Object> params);
}
