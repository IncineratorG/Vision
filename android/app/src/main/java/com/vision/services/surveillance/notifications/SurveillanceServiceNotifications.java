package com.vision.services.surveillance.notifications;

import com.vision.common.data.service_notification.ServiceNotification;
import com.vision.services.surveillance.notifications.payloads.SurveillanceServiceNotificationPayloads;
import com.vision.services.surveillance.notifications.payloads.payloads.DetectedPersonsCountInCameraFrameChangedNotificationPayload;
import com.vision.services.surveillance.notifications.payloads.payloads.TestNotificationPayload;
import com.vision.services.surveillance.notifications.types.SurveillanceServiceNotificationTypes;

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

    public static ServiceNotification detectedPersonsCountInCameraFrameChangedNotification(
            String senderGroupName,
            String senderDeviceName,
            int personsInCameraFrameCount
    ) {
        DetectedPersonsCountInCameraFrameChangedNotificationPayload payload =
                SurveillanceServiceNotificationPayloads.detectedPersonsCountInCameraFrameChangedNotificationPayload(personsInCameraFrameCount);

        return new ServiceNotification(
                SurveillanceServiceNotificationTypes.DETECTED_PERSONS_COUNT_IN_CAMERA_FRAME_CHANGED,
                senderGroupName,
                senderDeviceName,
                payload.jsonObject()
        );
    }
}
