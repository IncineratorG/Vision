package com.vision.services.surveillance.restore_service_manager.handlers;

import android.content.Context;
import android.os.PowerManager;

import com.vision.common.constants.AppConstants;
import com.vision.common.data.hybrid_objects.authentication_data.AuthenticationData;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.common.interfaces.service_restore_handler.ServiceRestoreHandler;
import com.vision.services.surveillance.data.internal_data.SurveillanceServiceInternalData;
import com.vision.services.surveillance.service_internal_tasks.tasks.SurveillanceServiceInternalTasks;

public class RestoreSurveillanceServiceHandler implements ServiceRestoreHandler {
    @Override
    public void handle(Context context,
                       AuthenticationData authenticationData,
                       OnTaskSuccess<Object> onSuccess,
                       OnTaskError<ServiceError> onError) {
        SurveillanceServiceInternalData mInternalData = SurveillanceServiceInternalData.get();

        if (mInternalData.serviceWakeLock != null && mInternalData.serviceWakeLock.isHeld()) {
            mInternalData.serviceWakeLock.release();
            mInternalData.serviceWakeLock = null;
        }

        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        mInternalData.serviceWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, AppConstants.SERVICE_WAKE_LOCK_TAG);
        mInternalData.serviceWakeLock.acquire();

        mInternalData.currentServiceMode = AppConstants.DEVICE_MODE_SERVICE;

        OnTaskSuccess<Void> successCallback = (result) -> {
            onSuccess.onSuccess(null);
        };
        SurveillanceServiceInternalTasks.updateAndPublishDeviceInfoTask(
                context,
                true,
                authenticationData.groupName(),
                authenticationData.groupPassword(),
                authenticationData.deviceName(),
                mInternalData.currentServiceMode,
                successCallback,
                onError
        ).run(null);
    }
}
