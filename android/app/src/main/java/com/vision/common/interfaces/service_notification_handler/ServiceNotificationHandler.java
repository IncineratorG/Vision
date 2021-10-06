package com.vision.common.interfaces.service_notification_handler;


import android.content.Context;

import com.vision.common.data.service_notification.ServiceNotification;
import com.vision.common.data.service_request.ServiceRequest;

public interface ServiceNotificationHandler {
    void handle(Context context, ServiceNotification notification);
}
