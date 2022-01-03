package com.vision.services.surveillance.service_internal_tasks.task_results;

import com.vision.services.surveillance.service_internal_tasks.tasks.authentication.GetCurrentDeviceNameTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.authentication.GetCurrentGroupNameTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.authentication.GetCurrentGroupPasswordTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.detect_device_movement.IsDetectDeviceMovementServiceRunningTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.foreground_service.IsForegroundServiceRunningTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.init_and_dispose_service.GetCurrentServiceModeTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.init_and_dispose_service.IsServiceInitializedTask;
import com.vision.services.surveillance.service_internal_tasks.tasks.recognize_person_with_camera.IsRecognizePersonWithCameraServiceRunningTask;

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
