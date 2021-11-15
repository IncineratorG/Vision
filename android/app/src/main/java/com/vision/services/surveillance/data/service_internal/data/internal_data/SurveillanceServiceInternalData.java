package com.vision.services.surveillance.data.service_internal.data.internal_data;


import android.os.PowerManager;

import com.vision.common.constants.AppConstants;
import com.vision.common.interfaces.foreground_service_work.ForegroundServiceWork;
import com.vision.common.interfaces.service_communication_manager.ServiceCommunicationManager;
import com.vision.common.interfaces.service_notifications_manager.ServiceNotificationsManager;
import com.vision.common.interfaces.service_request_sender.ServiceRequestSender;
import com.vision.common.interfaces.service_requests_executor.ServiceRequestsExecutor;
import com.vision.common.interfaces.service_response_sender.ServiceResponseSender;
import com.vision.common.interfaces.service_responses_executor.ServiceResponsesExecutor;
import com.vision.services.surveillance.data.service_errors.external_service_errors_mapper.ExternalServiceErrorsMapper;

public class SurveillanceServiceInternalData {
    private static SurveillanceServiceInternalData sInstance;

    public ExternalServiceErrorsMapper errorsMapper;
    public String currentServiceMode = AppConstants.DEVICE_MODE_UNKNOWN;
    public ForegroundServiceWork foregroundServiceWork;
    public ServiceRequestsExecutor requestsExecutor;
    public ServiceRequestSender requestsSender;
    public ServiceResponsesExecutor responsesExecutor;
    public ServiceResponseSender responseSender;
    public ServiceCommunicationManager communicationManager;
    public ServiceNotificationsManager notificationsManager;
    public PowerManager.WakeLock serviceWakeLock;

    private SurveillanceServiceInternalData() {

    }

    public static synchronized SurveillanceServiceInternalData get() {
        if (sInstance == null) {
            sInstance = new SurveillanceServiceInternalData();
        }

        return sInstance;
    }
}
