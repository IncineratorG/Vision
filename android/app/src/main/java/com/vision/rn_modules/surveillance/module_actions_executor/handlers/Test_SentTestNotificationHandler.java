package com.vision.rn_modules.surveillance.module_actions_executor.handlers;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.common.data.service_notification.ServiceNotification;
import com.vision.services.surveillance.SurveillanceService;
import com.vision.services.surveillance.data.notifications.SurveillanceServiceNotifications;
import com.vision.rn_modules.modules_common.interfaces.js_action_handler.JSActionHandler;

public class Test_SentTestNotificationHandler implements JSActionHandler {
    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "SentTestNotificationHandler->handle()");

        String groupName = SurveillanceService.get().currentGroupName();
        String deviceName = SurveillanceService.get().currentDeviceName();

        ServiceNotification testNotification =
                SurveillanceServiceNotifications.testNotification(groupName, deviceName, "My Test Value");

        SurveillanceService.get().sendNotificationToAll(context, testNotification);

        result.resolve(true);
    }
}
