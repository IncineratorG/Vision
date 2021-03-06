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
import com.vision.services.camera.data.state.CameraServiceState;
import com.vision.services.surveillance.data.internal_data.SurveillanceServiceInternalData;
import com.vision.services.surveillance.service_internal_tasks.tasks.SurveillanceServiceInternalTasks;

import java.util.List;

public class RestoreCameraServiceHandler implements ServiceRestoreHandler {
    @Override
    public void handle(Context context,
                       AuthenticationData authenticationData,
                       OnTaskSuccess<Object> onSuccess,
                       OnTaskError<ServiceError> onError) {
        Log.d("tag", "RestoreCameraServiceHandler->handle()");

        List<SerializedServiceState> serializedServiceStates =
                AppStorages.get().surveillanceStorage().getServiceStates(context);

        CameraServiceState cameraServiceState = null;
        for (int i = 0; i < serializedServiceStates.size(); ++i) {
            SerializedServiceState serializedServiceState = serializedServiceStates.get(i);
            if (!serializedServiceState.isEmpty()) {
                if (serializedServiceState.stateId().equalsIgnoreCase(CameraServiceState.STATE_ID)) {
                    cameraServiceState = new CameraServiceState(serializedServiceState.serializedState());
                    break;
                }
            }
        }

        if (cameraServiceState == null) {
            Log.d("tag", "RestoreCameraServiceHandler->handle()->STATE_IS_NULL");
            onSuccess.onSuccess(null);
            return;
        }

        if (cameraServiceState.isBackCameraRecognizePersonRunning() ||
                cameraServiceState.isFrontCameraRecognizePersonRunning()) {
            if (cameraServiceState.isBackCameraRecognizePersonRunning()) {
                SurveillanceServiceInternalData mInternalData = SurveillanceServiceInternalData.get();

                OnTaskSuccess<Void> successCallback = (result) -> {
                    onSuccess.onSuccess(null);
                };

                SurveillanceServiceInternalTasks.startRecognizePersonWithCameraTask(
                        context,
                        "back",
                        cameraServiceState.imageRotationDegrees(),
                        false,
                        authenticationData.groupName(),
                        authenticationData.groupPassword(),
                        authenticationData.deviceName(),
                        mInternalData.currentServiceMode,
                        successCallback,
                        onError
                ).run(null);
            } else if (cameraServiceState.isFrontCameraRecognizePersonRunning()) {
                SurveillanceServiceInternalData mInternalData = SurveillanceServiceInternalData.get();

                OnTaskSuccess<Void> successCallback = (result) -> {
                    onSuccess.onSuccess(null);
                };

                SurveillanceServiceInternalTasks.startRecognizePersonWithCameraTask(
                        context,
                        "front",
                        cameraServiceState.imageRotationDegrees(),
                        false,
                        authenticationData.groupName(),
                        authenticationData.groupPassword(),
                        authenticationData.deviceName(),
                        mInternalData.currentServiceMode,
                        successCallback,
                        onError
                ).run(null);
            }
        } else {
            onSuccess.onSuccess(null);
        }
    }
}
