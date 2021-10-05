package com.vision.common.services.surveillance.data.notifications.handlers.test_notification;


import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_notification.ServiceNotification;
import com.vision.common.interfaces.service_notification_handler.ServiceNotificationHandler;
import com.vision.common.services.surveillance.data.notifications.payloads.SurveillanceServiceNotificationPayloads;
import com.vision.common.services.surveillance.data.notifications.payloads.payloads.TestNotificationPayload;

public class TestNotificationHandler implements ServiceNotificationHandler {
    @Override
    public void handle(Context context, ServiceNotification notification) {
        Log.d("tag", "TestNotificationHandler->handle()");

        TestNotificationPayload payload = SurveillanceServiceNotificationPayloads.testNotificationPayload(notification.payload());

        Log.d("tag", "TestNotificationHandler->handle(): " + payload.testValue());
    }
}
