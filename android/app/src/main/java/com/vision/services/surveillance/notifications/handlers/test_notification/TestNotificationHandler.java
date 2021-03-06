package com.vision.services.surveillance.notifications.handlers.test_notification;


import android.app.Notification;
import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_notification.ServiceNotification;
import com.vision.common.interfaces.service_notification_handler.ServiceNotificationHandler;
import com.vision.services.surveillance.notifications.payloads.SurveillanceServiceNotificationPayloads;
import com.vision.services.surveillance.notifications.payloads.payloads.TestNotificationPayload;

public class TestNotificationHandler extends ServiceNotificationHandler {
    @Override
    protected void handleServiceNotification(Context context, ServiceNotification notification) {
        Log.d("tag", "TestNotificationHandler->handleServiceNotification()");

        TestNotificationPayload payload = SurveillanceServiceNotificationPayloads.testNotificationPayload(notification.payload());

        Log.d("tag", "TestNotificationHandler->handleServiceNotification(): " + payload.testValue());
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
        return "Тестовое уведомление";
    }
}
