package com.vision.common.services.surveillance.data.notifications.handlers.unknown_notification;


import android.app.Notification;
import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_notification.ServiceNotification;
import com.vision.common.interfaces.service_notification_handler.ServiceNotificationHandler;

public class UnknownNotificationHandler extends ServiceNotificationHandler {
    @Override
    protected void handleServiceNotification(Context context, ServiceNotification notification) {
        Log.d("tag", "UnknownNotificationHandler->handleServiceNotification()");
    }

    @Override
    protected String systemNotificationTitle(Context context, ServiceNotification notification) {
        String notificationGroupName = notification.senderGroupName();
        String notificationDeviceName = notification.senderDeviceName();

        return notificationGroupName + "." + notificationDeviceName;
    }

    @Override
    protected String systemNotificationMessage(Context context, ServiceNotification notification) {
        return "Неизвестное уведомдение";
    }

    @Override
    protected Notification createSystemNotification(Context context, ServiceNotification notification) {
        return null;
    }
}
