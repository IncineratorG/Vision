package com.vision.services.surveillance.data.notifications.handlers.device_movement_stop;


import android.app.Notification;
import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_notification.ServiceNotification;
import com.vision.common.interfaces.service_notification_handler.ServiceNotificationHandler;

public class DeviceMovementEndNotificationHandler extends ServiceNotificationHandler {
    @Override
    protected void handleServiceNotification(Context context, ServiceNotification notification) {
        Log.d("tag", "DeviceMovementStopNotificationHandler->handleServiceNotification()");
    }

    @Override
    protected Notification createSystemNotification(Context context, ServiceNotification notification) {
        return null;
    }

    @Override
    protected String systemNotificationTitle(Context context, ServiceNotification notification) {
        String notificationGroupName = notification.senderGroupName();
        String notificationDeviceName = notification.senderDeviceName();

        return notificationGroupName + "." + notificationDeviceName;
    }

    @Override
    protected String systemNotificationMessage(Context context, ServiceNotification notification) {
        return "Движение устройства закончено";
    }
}
