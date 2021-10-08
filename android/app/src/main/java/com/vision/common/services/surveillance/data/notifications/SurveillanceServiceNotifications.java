package com.vision.common.services.surveillance.data.notifications;

import com.vision.common.data.service_notification.ServiceNotification;
import com.vision.common.services.surveillance.data.notifications.payloads.SurveillanceServiceNotificationPayloads;
import com.vision.common.services.surveillance.data.notifications.payloads.payloads.TestNotificationPayload;
import com.vision.common.services.surveillance.data.notifications.types.SurveillanceServiceNotificationTypes;

public class SurveillanceServiceNotifications {
    public static ServiceNotification testNotification(String senderGroupName,
                                                       String senderDeviceName,
                                                       String testValue) {
        TestNotificationPayload payload =
                SurveillanceServiceNotificationPayloads.testNotificationPayload(testValue);

        return new ServiceNotification(
                SurveillanceServiceNotificationTypes.TEST_NOTIFICATION,
                senderGroupName,
                senderDeviceName,
                payload.jsonObject()
        );
    }

    public static ServiceNotification deviceMovementStartNotification(String senderGroupName,
                                                                      String senderDeviceName) {
        return new ServiceNotification(
                SurveillanceServiceNotificationTypes.DEVICE_MOVEMENT_START,
                senderGroupName,
                senderDeviceName,
                null
        );
    }

    public static ServiceNotification deviceMovementEndNotification(String senderGroupName,
                                                                    String senderDeviceName) {
        return new ServiceNotification(
                SurveillanceServiceNotificationTypes.DEVICE_MOVEMENT_END,
                senderGroupName,
                senderDeviceName,
                null
        );
    }
}
