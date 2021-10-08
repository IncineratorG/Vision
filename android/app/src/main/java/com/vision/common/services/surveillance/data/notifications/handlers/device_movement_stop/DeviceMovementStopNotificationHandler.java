package com.vision.common.services.surveillance.data.notifications.handlers.device_movement_stop;


import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_notification.ServiceNotification;
import com.vision.common.interfaces.service_notification_handler.ServiceNotificationHandler;

public class DeviceMovementStopNotificationHandler implements ServiceNotificationHandler {
    @Override
    public void handle(Context context, ServiceNotification notification) {
        Log.d("tag", "DeviceMovementStopNotificationHandler->handle()");
    }
}
