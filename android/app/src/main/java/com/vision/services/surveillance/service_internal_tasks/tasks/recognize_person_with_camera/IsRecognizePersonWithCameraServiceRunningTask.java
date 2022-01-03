package com.vision.services.surveillance.service_internal_tasks.tasks.recognize_person_with_camera;

import android.util.Log;

import com.vision.services.camera.CameraService;
import com.vision.common.interfaces.service_sync_task.ServiceSyncTask;

import java.util.Map;

public class IsRecognizePersonWithCameraServiceRunningTask implements ServiceSyncTask {
    private String mCameraType;

    public IsRecognizePersonWithCameraServiceRunningTask(String cameraType) {
        mCameraType = cameraType;
    }

    @Override
    public Object run(Map<String, Object> params) {
        CameraService cameraService = CameraService.get();
        if (mCameraType.equalsIgnoreCase("front")) {
            return cameraService.isFrontCameraRecognizePersonRunning();
        } else if (mCameraType.equalsIgnoreCase("back")) {
            return cameraService.isBackCameraRecognizePersonRunning();
        } else {
            Log.d("tag", "IsRecognizePersonWithCameraServiceRunningSurveillanceServiceTask->run()->UNKNOWN_CAMERA_TYPE: " + mCameraType);
        }

        return false;
    }
}
