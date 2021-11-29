package com.vision.services.surveillance.restore_service_manager.handlers;

import android.content.Context;
import android.util.Log;

import com.vision.common.data.hybrid_objects.authentication_data.AuthenticationData;
import com.vision.common.data.serialized_service_state.SerializedServiceState;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.common.interfaces.service_restore_handler.ServiceRestoreHandler;
import com.vision.services.app_storages.AppStorages;

import java.util.List;

public class RestoreDeviceMovementServiceHandler implements ServiceRestoreHandler {
    @Override
    public void handle(Context context,
                       AuthenticationData authenticationData,
                       OnTaskSuccess<Object> onSuccess,
                       OnTaskError<ServiceError> onError) {
        List<SerializedServiceState> serializedServiceStates =
                AppStorages.get().surveillanceStorage().getServiceStates(context);

        for (int i = 0; i < serializedServiceStates.size(); ++i) {
            SerializedServiceState serializedServiceState = serializedServiceStates.get(i);

            if (!serializedServiceState.isEmpty()) {
                Log.d("tag", "RestoreDeviceMovementServiceHandler->handle(): " + serializedServiceState.stateId() + " - " + serializedServiceState.serializedState());
            } else {
                Log.d("tag", "RestoreDeviceMovementServiceHandler->handle()->SERIALIZED_SERVICE_STATE_IS_NULL");
            }
        }

        onSuccess.onSuccess(null);
    }
}
