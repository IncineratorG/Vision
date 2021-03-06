package com.vision.common.interfaces.service_communication_manager;


import android.content.Context;

import com.vision.common.interfaces.service_notification_sender.ServiceNotificationSender;
import com.vision.common.interfaces.service_request_interrupter.ServiceRequestInterrupter;
import com.vision.common.interfaces.service_request_sender.ServiceRequestSender;
import com.vision.common.interfaces.service_response_sender.ServiceResponseSender;

public interface ServiceCommunicationManager extends
        ServiceRequestSender,
        ServiceResponseSender,
        ServiceRequestInterrupter {
    void startIsAliveSignaling(Context context);
    void stopIsAliveSignaling(Context context);
    void startRequestsListener(Context context);
    void stopRequestsListener(Context context);
    void startResponsesListener(Context context);
    void stopResponsesListener(Context context);
}
