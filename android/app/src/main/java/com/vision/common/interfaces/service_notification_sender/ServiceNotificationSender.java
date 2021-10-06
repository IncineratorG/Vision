package com.vision.common.interfaces.service_notification_sender;

import android.content.Context;

import com.vision.common.data.service_notification.ServiceNotification;

public interface ServiceNotificationSender {
    void sendNotificationToAll(Context context, ServiceNotification notification);
}
