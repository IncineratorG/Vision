package com.vision.services.surveillance.restore_service_manager.handlers;

import android.content.Context;
import android.util.Log;

import com.vision.common.data.hybrid_objects.authentication_data.AuthenticationData;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.common.interfaces.service_restore_handler.ServiceRestoreHandler;

public class RestoreCameraServiceHandler implements ServiceRestoreHandler {
    @Override
    public void handle(Context context,
                       AuthenticationData authenticationData,
                       OnTaskSuccess<Object> onSuccess,
                       OnTaskError<ServiceError> onError) {
        Log.d("tag", "RestoreCameraServiceHandler->handle()");

        onSuccess.onSuccess(null);
    }
}
