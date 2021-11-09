package com.vision.services.surveillance.data.service_internal.tasks.tasks;

import android.content.Context;

import com.vision.common.data.hybrid_objects.device_info_list.DeviceInfoList;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.authentication.get_current_device_name.GetCurrentDeviceNameSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.authentication.get_current_group_name.GetCurrentGroupNameSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.authentication.get_current_group_password.GetCurrentGroupPasswordSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.create_group_with_device.CreateGroupWithDeviceSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.detect_device_movement.is_detect_device_movement_service_running.IsDetectDeviceMovementServiceRunningSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.foreground_service.is_foreground_service_running.IsForegroundServiceRunningSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.init_and_dispose_service.dispose_service.DisposeSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.get_devices_in_group.GetDevicesInGroupSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.init_and_dispose_service.get_current_service_mode.GetCurrentServiceModeSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.init_and_dispose_service.init_service.InitSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.authentication.login_device_in_group.LoginDeviceInGroupSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.authentication.logout_device_from_group.LogoutDeviceFromGroupSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.authentication.register_device_in_group.RegisterDeviceInGroupSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.detect_device_movement.start_detect_device_movement.StartDetectDeviceMovementSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.foreground_service.start_foreground_service.StartForegroundServiceSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.init_and_dispose_service.is_service_initialized.IsServiceInitializedSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.recognize_person_with_camera.start_recognize_person_with_camera.StartRecognizePersonWithCameraSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.recognize_person_with_camera.stop_recognize_person_with_camera.StopRecognizePersonWithCameraSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.responses.start_listen_to_responses.StartListenToResponsesSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.detect_device_movement.stop_detect_device_movement.StopDetectDeviceMovementSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.foreground_service.stop_foreground_service.StopForegroundServiceSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.responses.stop_listen_to_responses.StopListenToResponsesSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.notifications.subscribe_to_global_notifications.SubscribeToGlobalNotificationsSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.notifications.unsubscribe_from_global_notifications.UnsubscribeFromGlobalNotificationsSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.update_and_publish_device_info.UpdateAndPublishDeviceInfoSurveillanceServiceTask;

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
            String currentDeviceName,
            String currentServiceMode
    ) {
        return new DisposeSurveillanceServiceTask(
                context, currentGroupName, currentGroupPassword, currentDeviceName, currentServiceMode
        );
    }

    public static IsServiceInitializedSurveillanceServiceTask isServiceInitializedSurveillanceServiceTask() {
        return new IsServiceInitializedSurveillanceServiceTask();
    }

    public static GetCurrentServiceModeSurveillanceServiceTask getCurrentServiceModeSurveillanceServiceTask() {
        return new GetCurrentServiceModeSurveillanceServiceTask();
    }

    public static UpdateAndPublishDeviceInfoSurveillanceServiceTask updateAndPublishDeviceInfoSurveillanceServiceTask(
            Context context,
            boolean needUpdateDeviceMode,
            String currentGroupName,
            String currentGroupPassword,
            String currentDeviceName,
            String currentServiceMode,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new UpdateAndPublishDeviceInfoSurveillanceServiceTask(
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

    public static StartListenToResponsesSurveillanceServiceTask startListenToResponsesSurveillanceServiceTask(
            Context context
    ) {
        return new StartListenToResponsesSurveillanceServiceTask(context);
    }

    public static StopListenToResponsesSurveillanceServiceTask stopListenToResponsesSurveillanceServiceTask(
            Context context
    ) {
        return new StopListenToResponsesSurveillanceServiceTask(context);
    }

    public static SubscribeToGlobalNotificationsSurveillanceServiceTask subscribeToGlobalNotificationsSurveillanceServiceTask(
            Context context,
            String currentGroupName,
            String currentGroupPassword,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new SubscribeToGlobalNotificationsSurveillanceServiceTask(
                context, currentGroupName, currentGroupPassword, onSuccess, onError
        );
    }

    public static UnsubscribeFromGlobalNotificationsSurveillanceServiceTask unsubscribeFromGlobalNotificationsSurveillanceServiceTask(
            Context context,
            String currentGroupName,
            String currentGroupPassword,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new UnsubscribeFromGlobalNotificationsSurveillanceServiceTask(
                context, currentGroupName, currentGroupPassword, onSuccess, onError
        );
    }

    public static CreateGroupWithDeviceSurveillanceServiceTask createGroupWithDeviceSurveillanceServiceTask(
            Context context,
            String groupName,
            String groupPassword,
            String deviceName,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new CreateGroupWithDeviceSurveillanceServiceTask(
                context, groupName, groupPassword, deviceName, onSuccess, onError
        );
    }

    public static RegisterDeviceInGroupSurveillanceServiceTask registerDeviceInGroupSurveillanceServiceTask(
            Context context,
            String groupName,
            String groupPassword,
            String deviceName,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new RegisterDeviceInGroupSurveillanceServiceTask(
                context, groupName, groupPassword, deviceName, onSuccess, onError
        );
    }

    public static LoginDeviceInGroupSurveillanceServiceTask loginDeviceInGroupSurveillanceServiceTask(
            Context context,
            String groupName,
            String groupPassword,
            String deviceName,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new LoginDeviceInGroupSurveillanceServiceTask(
                context, groupName, groupPassword, deviceName, onSuccess, onError
        );
    }

    public static LogoutDeviceFromGroupSurveillanceServiceTask logoutDeviceFromGroupSurveillanceServiceTask(
            Context context,
            String currentGroupName,
            String currentGroupPassword,
            String currentDeviceName,
            String currentServiceMode,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new LogoutDeviceFromGroupSurveillanceServiceTask(
                context,
                currentGroupName,
                currentGroupPassword,
                currentDeviceName,
                currentServiceMode,
                onSuccess,
                onError
        );
    }

    public static GetCurrentGroupNameSurveillanceServiceTask getCurrentGroupNameSurveillanceServiceTask() {
        return new GetCurrentGroupNameSurveillanceServiceTask();
    }

    public static GetCurrentGroupPasswordSurveillanceServiceTask getCurrentGroupPasswordSurveillanceServiceTask() {
        return new GetCurrentGroupPasswordSurveillanceServiceTask();
    }

    public static GetCurrentDeviceNameSurveillanceServiceTask getCurrentDeviceNameSurveillanceServiceTask() {
        return new GetCurrentDeviceNameSurveillanceServiceTask();
    }

    public static StartForegroundServiceSurveillanceServiceTask startForegroundServiceSurveillanceServiceTask(
            Context context,
            boolean needUpdateDeviceMode,
            String currentGroupName,
            String currentGroupPassword,
            String currentDeviceName,
            String currentServiceMode,
            String serviceWakeLockTag,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new StartForegroundServiceSurveillanceServiceTask(
                context,
                needUpdateDeviceMode,
                currentGroupName,
                currentGroupPassword,
                currentDeviceName,
                currentServiceMode,
                serviceWakeLockTag,
                onSuccess,
                onError
        );
    }

    public static StopForegroundServiceSurveillanceServiceTask stopForegroundServiceSurveillanceServiceTask(
            Context context,
            boolean needUpdateDeviceMode,
            String currentGroupName,
            String currentGroupPassword,
            String currentDeviceName,
            String currentServiceMode,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new StopForegroundServiceSurveillanceServiceTask(
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

    public static IsForegroundServiceRunningSurveillanceServiceTask isForegroundServiceRunningSurveillanceServiceTask(
            Context context
    ) {
        return new IsForegroundServiceRunningSurveillanceServiceTask(context);
    }

    public static GetDevicesInGroupSurveillanceServiceTask getDevicesInGroupSurveillanceServiceTask(
            Context context,
            String groupName,
            String groupPassword,
            OnTaskSuccess<DeviceInfoList> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new GetDevicesInGroupSurveillanceServiceTask(
                context, groupName, groupPassword, onSuccess, onError
        );
    }

    public static StartDetectDeviceMovementSurveillanceServiceTask startDetectDeviceMovementSurveillanceServiceTask(
            Context context,
            boolean needUpdateDeviceMode,
            String currentGroupName,
            String currentGroupPassword,
            String currentDeviceName,
            String currentServiceMode,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new StartDetectDeviceMovementSurveillanceServiceTask(
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

    public static StopDetectDeviceMovementSurveillanceServiceTask stopDetectDeviceMovementSurveillanceServiceTask(
            Context context,
            boolean needUpdateDeviceMode,
            String currentGroupName,
            String currentGroupPassword,
            String currentDeviceName,
            String currentServiceMode,
            OnTaskSuccess<Void> onSuccess,
            OnTaskError<ServiceError> onError
    ) {
        return new StopDetectDeviceMovementSurveillanceServiceTask(
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

    public static IsDetectDeviceMovementServiceRunningSurveillanceServiceTask isDetectDeviceMovementServiceRunningSurveillanceServiceTask() {
        return new IsDetectDeviceMovementServiceRunningSurveillanceServiceTask();
    }

    public static StartRecognizePersonWithCameraSurveillanceServiceTask startRecognizePersonWithCameraSurveillanceServiceTask(
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
        return new StartRecognizePersonWithCameraSurveillanceServiceTask(
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

    public static StopRecognizePersonWithCameraSurveillanceServiceTask stopRecognizePersonWithCameraSurveillanceServiceTask(
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
        return new StopRecognizePersonWithCameraSurveillanceServiceTask(
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
}
