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
import com.vision.services.device_movement.data.state.DeviceMovementServiceState;
import com.vision.services.surveillance.data.internal_data.SurveillanceServiceInternalData;
import com.vision.services.surveillance.service_internal_tasks.tasks.SurveillanceServiceInternalTasks;

import java.util.List;

public class RestoreDeviceMovementServiceHandler implements ServiceRestoreHandler {
    @Override
    public void handle(Context context,
                       AuthenticationData authenticationData,
                       OnTaskSuccess<Object> onSuccess,
                       OnTaskError<ServiceError> onError) {
        List<SerializedServiceState> serializedServiceStates =
                AppStorages.get().surveillanceStorage().getServiceStates(context);

        // ===
        for (int i = 0; i < serializedServiceStates.size(); ++i) {
            SerializedServiceState serializedServiceState = serializedServiceStates.get(i);
            if (!serializedServiceState.isEmpty()) {
                Log.d("tag", "RestoreDeviceMovementServiceHandler->HERE: " + serializedServiceState.stateId() + " - " + serializedServiceState.serializedState());
            } else {
                Log.d("tag", "RestoreDeviceMovementServiceHandler->STATE_IS_EMPTY: " + i);
            }
        }
        // ===

        DeviceMovementServiceState deviceMovementServiceState = null;
        for (int i = 0; i < serializedServiceStates.size(); ++i) {
            SerializedServiceState serializedServiceState = serializedServiceStates.get(i);
            if (!serializedServiceState.isEmpty()) {
                if (serializedServiceState.stateId().equalsIgnoreCase(DeviceMovementServiceState.STATE_ID)) {
                    deviceMovementServiceState = new DeviceMovementServiceState(serializedServiceState.serializedState());
                    break;
                }
            }
        }

        if (deviceMovementServiceState == null) {
            Log.d("tag", "RestoreDeviceMovementServiceHandler->handle()->STATE_IS_NULL");
            onSuccess.onSuccess(null);
            return;
        }

        if (deviceMovementServiceState.isRunning()) {
            SurveillanceServiceInternalData mInternalData = SurveillanceServiceInternalData.get();

            OnTaskSuccess<Void> successCallback = (result) -> {
                onSuccess.onSuccess(null);
            };

            SurveillanceServiceInternalTasks.startDetectDeviceMovementTask(
                    context,
                    false,
                    authenticationData.groupName(),
                    authenticationData.groupPassword(),
                    authenticationData.deviceName(),
                    mInternalData.currentServiceMode,
                    successCallback,
                    onError
            ).run(null);
        } else {
            onSuccess.onSuccess(null);
        }
    }
}
