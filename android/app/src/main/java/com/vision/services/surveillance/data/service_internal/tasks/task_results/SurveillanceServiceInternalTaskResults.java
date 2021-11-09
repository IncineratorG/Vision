package com.vision.services.surveillance.data.service_internal.tasks.task_results;

import com.vision.services.surveillance.data.service_internal.tasks.tasks.authentication.get_current_device_name.GetCurrentDeviceNameSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.authentication.get_current_group_name.GetCurrentGroupNameSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.authentication.get_current_group_password.GetCurrentGroupPasswordSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.detect_device_movement.is_detect_device_movement_service_running.IsDetectDeviceMovementServiceRunningSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.foreground_service.is_foreground_service_running.IsForegroundServiceRunningSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.init_and_dispose_service.get_current_service_mode.GetCurrentServiceModeSurveillanceServiceTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.init_and_dispose_service.is_service_initialized.IsServiceInitializedSurveillanceServiceTask;

public class SurveillanceServiceInternalTaskResults {
    public static boolean result(IsForegroundServiceRunningSurveillanceServiceTask task, Object result) {
        return (boolean) result;
    }

    public static boolean result(IsServiceInitializedSurveillanceServiceTask task, Object result) {
        return (boolean) result;
    }

    public static String result(GetCurrentGroupNameSurveillanceServiceTask task, Object result) {
        return (String) result;
    }

    public static String result(GetCurrentGroupPasswordSurveillanceServiceTask task, Object result) {
        return (String) result;
    }

    public static String result(GetCurrentDeviceNameSurveillanceServiceTask task, Object result) {
        return (String) result;
    }

    public static String result(GetCurrentServiceModeSurveillanceServiceTask task, Object result) {
        return (String) result;
    }

    public static boolean result(IsDetectDeviceMovementServiceRunningSurveillanceServiceTask task,
                                 Object result) {
        return (boolean) result;
    }
}
