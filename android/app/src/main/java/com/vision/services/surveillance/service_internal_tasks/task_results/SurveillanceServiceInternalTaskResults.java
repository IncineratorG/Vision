package com.vision.services.surveillance.service_internal_tasks.task_results;

import com.vision.services.surveillance.service_internal_tasks.tasks.authentication.get_current_device_name.GetCurrentDeviceNameTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.authentication.get_current_group_name.GetCurrentGroupNameTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.authentication.get_current_group_password.GetCurrentGroupPasswordTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.detect_device_movement.is_detect_device_movement_service_running.IsDetectDeviceMovementServiceRunningTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.foreground_service.is_foreground_service_running.IsForegroundServiceRunningTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.init_and_dispose_service.get_current_service_mode.GetCurrentServiceModeTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.init_and_dispose_service.is_service_initialized.IsServiceInitializedTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.recognize_person_with_camera.is_recognize_person_with_camera_service_running.IsRecognizePersonWithCameraServiceRunningTask;

public class SurveillanceServiceInternalTaskResults {
    public static boolean result(IsForegroundServiceRunningTask task, Object result) {
        return (boolean) result;
    }

    public static boolean result(IsServiceInitializedTask task, Object result) {
        return (boolean) result;
    }

    public static String result(GetCurrentGroupNameTask task, Object result) {
        return (String) result;
    }

    public static String result(GetCurrentGroupPasswordTask task, Object result) {
        return (String) result;
    }

    public static String result(GetCurrentDeviceNameTask task, Object result) {
        return (String) result;
    }

    public static String result(GetCurrentServiceModeTask task, Object result) {
        return (String) result;
    }

    public static boolean result(IsDetectDeviceMovementServiceRunningTask task,
                                 Object result) {
        return (boolean) result;
    }

    public static boolean result(IsRecognizePersonWithCameraServiceRunningTask task,
                                 Object result) {
        return (boolean) result;
    }
}
