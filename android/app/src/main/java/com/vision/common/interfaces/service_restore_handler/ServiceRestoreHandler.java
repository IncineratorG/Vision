package com.vision.common.interfaces.service_restore_handler;

import android.content.Context;

import com.vision.common.data.hybrid_objects.authentication_data.AuthenticationData;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;

public interface ServiceRestoreHandler {
    void handle(Context context,
                AuthenticationData authenticationData,
                OnTaskSuccess<Object> onSuccess,
                OnTaskError<ServiceError> onError);
}
