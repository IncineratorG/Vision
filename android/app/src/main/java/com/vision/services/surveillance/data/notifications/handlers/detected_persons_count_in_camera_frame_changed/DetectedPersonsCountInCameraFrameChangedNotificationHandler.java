package com.vision.services.surveillance.data.notifications.handlers.detected_persons_count_in_camera_frame_changed;

import android.app.Notification;
import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_notification.ServiceNotification;
import com.vision.common.interfaces.service_notification_handler.ServiceNotificationHandler;
import com.vision.services.surveillance.data.notifications.payloads.SurveillanceServiceNotificationPayloads;
import com.vision.services.surveillance.data.notifications.payloads.payloads.DetectedPersonsCountInCameraFrameChangedNotificationPayload;

public class DetectedPersonsCountInCameraFrameChangedNotificationHandler extends ServiceNotificationHandler {
    private int mCurrentPersonsCount = 0;

    @Override
    protected void handleServiceNotification(Context context, ServiceNotification notification) {
        Log.d("tag", "DetectedPersonsCountInCameraFrameChangedNotificationHandler->handleServiceNotification()");

        DetectedPersonsCountInCameraFrameChangedNotificationPayload payload =
                SurveillanceServiceNotificationPayloads.detectedPersonsCountInCameraFrameChangedNotificationPayload(notification.payload());

        mCurrentPersonsCount = payload.personsCount();
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
        String message = "";

        if (mCurrentPersonsCount > 0) {
            message = "В объективе камеры " + mCurrentPersonsCount + " человек.";
        } else {
            message = "Нет людей в объективе камеры";
        }

        return message;
    }
}
