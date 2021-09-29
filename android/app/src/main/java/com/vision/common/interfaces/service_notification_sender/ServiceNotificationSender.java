package com.vision.common.interfaces.service_notification_sender;

import com.vision.common.data.service_notification.ServiceNotification;

public interface ServiceNotificationSender {
    void sendNotificationToAll(ServiceNotification notification);
}
