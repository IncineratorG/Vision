package com.vision.services.surveillance.service_internal_tasks.tasks;

import android.content.Context;

import com.vision.common.data.hybrid_objects.device_info_list.DeviceInfoList;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.surveillance.service_internal_tasks.tasks.authentication.GetCurrentDeviceNameTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.authentication.GetCurrentGroupNameTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.authentication.GetCurrentGroupPasswordTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.authentication.RestoreDeviceInGroupTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.authentication.RestoreServiceTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.create_group_with_device.CreateGroupWithDeviceTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.detect_device_movement.IsDetectDeviceMovementServiceRunningTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.foreground_service.IsForegroundServiceRunningTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.init_and_dispose_service.DisposeSurveillanceServiceTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.get_devices_in_group.GetDevicesInGroupTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.init_and_dispose_service.GetCurrentServiceModeTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.init_and_dispose_service.InitSurveillanceServiceTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.authentication.LoginDeviceInGroupTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.authentication.LogoutDeviceFromGroupTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.authentication.RegisterDeviceInGroupTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.detect_device_movement.StartDetectDeviceMovementTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.foreground_service.StartForegroundServiceTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.init_and_dispose_service.IsServiceInitializedTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.recognize_person_with_camera.IsRecognizePersonWithCameraServiceRunningTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.recognize_person_with_camera.StartRecognizePersonWithCameraTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.recognize_person_with_camera.StopRecognizePersonWithCameraTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.responses.StartListenToResponsesTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.detect_device_movement.StopDetectDeviceMovementTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.foreground_service.StopForegroundServiceTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.responses.StopListenToResponsesTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.notifications.SubscribeToGlobalNotificationsTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.notifications.UnsubscribeFromGlobalNotificationsTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.update_and_publish_device_info.UpdateAndPublishDeviceInfoTask;

public class SurveillanceServiceInternalTasks {
    public static InitSurveillanceServiceTask initSurveillanceServiceTask(
            Context context,
            String groupName,
            String groupPassword,
            String deviceName,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new InitSurveillanceServiceTask(
                context,
                groupName,
                groupPassword,
                deviceName,
                onSuccess,
                onError
        );
    }

    public static DisposeSurveillanceServiceTask disposeSurveillanceServiceTask(
            Context context,
            String currentGroupName,
            String currentGroupPassword,
            String currentDeviceName
    ) {
        return new DisposeSurveillanceServiceTask(
                context, currentGroupName, currentGroupPassword, currentDeviceName
        );
    }

    public static IsServiceInitializedTask isServiceInitializedTask() {
        return new IsServiceInitializedTask();
    }

    public static GetCurrentServiceModeTask getCurrentServiceModeTask() {
        return new GetCurrentServiceModeTask();
    }

    public static UpdateAndPublishDeviceInfoTask updateAndPublishDeviceInfoTask(
            Context context,
            boolean needUpdateDeviceMode,
            String currentGroupName,
            String currentGroupPassword,
            String currentDeviceName,
            String currentServiceMode,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new UpdateAndPublishDeviceInfoTask(
                context,
                needUpdateDeviceMode,
                currentGroupName,
                currentGroupPassword,
                currentDeviceName,
                currentServiceMode,
                onSuccess,
                onError
        );
    }

    public static StartListenToResponsesTask startListenToResponsesTask(
            Context context
    ) {
        return new StartListenToResponsesTask(context);
    }

    public static StopListenToResponsesTask stopListenToResponsesTask(
            Context context
    ) {
        return new StopListenToResponsesTask(context);
    }

    public static SubscribeToGlobalNotificationsTask subscribeToGlobalNotificationsTask(
            Context context,
            String currentGroupName,
            String currentGroupPassword,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new SubscribeToGlobalNotificationsTask(
                context, currentGroupName, currentGroupPassword, onSuccess, onError
        );
    }

    public static UnsubscribeFromGlobalNotificationsTask unsubscribeFromGlobalNotificationsTask(
            Context context,
            String currentGroupName,
            String currentGroupPassword,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new UnsubscribeFromGlobalNotificationsTask(
                context, currentGroupName, currentGroupPassword, onSuccess, onError
        );
    }

    public static CreateGroupWithDeviceTask createGroupWithDeviceTask(
            Context context,
            String groupName,
            String groupPassword,
            String deviceName,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new CreateGroupWithDeviceTask(
                context, groupName, groupPassword, deviceName, onSuccess, onError
        );
    }

    public static RegisterDeviceInGroupTask registerDeviceInGroupTask(
            Context context,
            String groupName,
            String groupPassword,
            String deviceName,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new RegisterDeviceInGroupTask(
                context, groupName, groupPassword, deviceName, onSuccess, onError
        );
    }

    public static LoginDeviceInGroupTask loginDeviceInGroupTask(
            Context context,
            String groupName,
            String groupPassword,
            String deviceName,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new LoginDeviceInGroupTask(
                context, groupName, groupPassword, deviceName, onSuccess, onError
        );
    }

    public static LogoutDeviceFromGroupTask logoutDeviceFromGroupTask(
            Context context,
            String currentGroupName,
            String currentGroupPassword,
            String currentDeviceName,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new LogoutDeviceFromGroupTask(
                context,
                currentGroupName,
                currentGroupPassword,
                currentDeviceName,
                onSuccess,
                onError
        );
    }

    public static RestoreDeviceInGroupTask restoreDeviceInGroupTask(Context context,
                                                                    String currentGroupName,
                                                                    String currentGroupPassword,
                                                                    String currentDeviceName,
                                                                    OnTaskSuccess<Void> onSuccess,
                                                                    OnTaskError<ServiceError> onError) {
        return new RestoreDeviceInGroupTask(
                context,
                currentGroupName,
                currentGroupPassword,
                currentDeviceName,
                onSuccess,
                onError
        );
    }

    public static RestoreServiceTask restoreServiceTask(Context context,
                                                        OnTaskSuccess<Void> onSuccess,
                                                        OnTaskError<ServiceError> onError) {
        return new RestoreServiceTask(context, onSuccess, onError);
    }

    public static GetCurrentGroupNameTask getCurrentGroupNameTask() {
        return new GetCurrentGroupNameTask();
    }

    public static GetCurrentGroupPasswordTask getCurrentGroupPasswordTask() {
        return new GetCurrentGroupPasswordTask();
    }

    public static GetCurrentDeviceNameTask getCurrentDeviceNameTask() {
        return new GetCurrentDeviceNameTask();
    }

    public static StartForegroundServiceTask startForegroundServiceTask(
            Context context,
            boolean needUpdateDeviceMode,
            String currentGroupName,
            String currentGroupPassword,
            String currentDeviceName,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new StartForegroundServiceTask(
                context,
                needUpdateDeviceMode,
                currentGroupName,
                currentGroupPassword,
                currentDeviceName,
                onSuccess,
                onError
        );
    }

    public static StopForegroundServiceTask stopForegroundServiceTask(
            Context context,
            boolean needUpdateDeviceMode,
            String currentGroupName,
            String currentGroupPassword,
            String currentDeviceName,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new StopForegroundServiceTask(
                context,
                needUpdateDeviceMode,
                currentGroupName,
                currentGroupPassword,
                currentDeviceName,
                onSuccess,
                onError
        );
    }

    public static IsForegroundServiceRunningTask isForegroundServiceRunningTask(
            Context context
    ) {
        return new IsForegroundServiceRunningTask(context);
    }

    public static GetDevicesInGroupTask getDevicesInGroupTask(
            Context context,
            String groupName,
            String groupPassword,
            OnTaskSuccess<DeviceInfoList> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new GetDevicesInGroupTask(
                context, groupName, groupPassword, onSuccess, onError
        );
    }

    public static StartDetectDeviceMovementTask startDetectDeviceMovementTask(
            Context context,
            boolean needUpdateDeviceMode,
            String currentGroupName,
            String currentGroupPassword,
            String currentDeviceName,
            String currentServiceMode,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new StartDetectDeviceMovementTask(
                context,
                needUpdateDeviceMode,
                currentGroupName,
                currentGroupPassword,
                currentDeviceName,
                currentServiceMode,
                onSuccess,
                onError
        );
    }

    public static StopDetectDeviceMovementTask stopDetectDeviceMovementTask(
            Context context,
            boolean needUpdateDeviceMode,
            String currentGroupName,
            String currentGroupPassword,
            String currentDeviceName,
            String currentServiceMode,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new StopDetectDeviceMovementTask(
                context,
                needUpdateDeviceMode,
                currentGroupName,
                currentGroupPassword,
                currentDeviceName,
                currentServiceMode,
                onSuccess,
                onError
        );
    }

    public static IsDetectDeviceMovementServiceRunningTask isDetectDeviceMovementServiceRunningTask() {
        return new IsDetectDeviceMovementServiceRunningTask();
    }

    public static StartRecognizePersonWithCameraTask startRecognizePersonWithCameraTask(
            Context context,
            String cameraType,
            int imageRotationDegrees,
            boolean needUpdateDeviceMode,
            String currentGroupName,
            String currentGroupPassword,
            String currentDeviceName,
            String currentServiceMode,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new StartRecognizePersonWithCameraTask(
                context,
                cameraType,
                imageRotationDegrees,
                needUpdateDeviceMode,
                currentGroupName,
                currentGroupPassword,
                currentDeviceName,
                currentServiceMode,
                onSuccess,
                onError
        );
    }

    public static StopRecognizePersonWithCameraTask stopRecognizePersonWithCameraTask(
            Context context,
            String cameraType,
            boolean needUpdateDeviceMode,
            String currentGroupName,
            String currentGroupPassword,
            String currentDeviceName,
            String currentServiceMode,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new StopRecognizePersonWithCameraTask(
                context,
                cameraType,
                needUpdateDeviceMode,
                currentGroupName,
                currentGroupPassword,
                currentDeviceName,
                currentServiceMode,
                onSuccess,
                onError
        );
    }

    public static IsRecognizePersonWithCameraServiceRunningTask isRecognizePersonWithCameraServiceRunningTask(
        String cameraType
    ) {
        return new IsRecognizePersonWithCameraServiceRunningTask(cameraType);
    }
}
