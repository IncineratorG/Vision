package com.vision.common.services.surveillance.data.notifications;

import com.vision.common.data.service_notification.ServiceNotification;
import com.vision.common.services.surveillance.data.notifications.payloads.SurveillanceServiceNotificationPayloads;
import com.vision.common.services.surveillance.data.notifications.payloads.payloads.TestNotificationPayload;
import com.vision.common.services.surveillance.data.notifications.types.SurveillanceServiceNotificationTypes;

public class SurveillanceServiceNotifications {
    public static ServiceNotification testNotification(String testValue) {
        TestNotificationPayload payload =
                SurveillanceServiceNotificationPayloads.testNotificationPayload(testValue);

        return new ServiceNotification(
                SurveillanceServiceNotificationTypes.TEST_NOTIFICATION,
                payload.jsonObject()
        );
    }
}
