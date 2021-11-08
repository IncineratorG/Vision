package com.vision.services.camera.camera_manager.tasks.recognize_person_with_front_camera;

import android.util.Log;

import com.vision.services.camera.camera_manager.CameraManager_V2;
import com.vision.services.camera.data.camera_preview_image_data.CameraPreviewImageData;

public class RecognizePersonWithFrontCameraCameraManagerTask implements CameraManager_V2.CameraManagerTask {
    private String mType;
    private int mImageRotationDeg;
    private long mLastLogTimestamp;

    public RecognizePersonWithFrontCameraCameraManagerTask(int imageRotationDeg) {
        mType = CameraManager_V2.RECOGNIZE_PERSON_WITH_FRONT_CAMERA;
        mImageRotationDeg = imageRotationDeg;
        mLastLogTimestamp = -1;
    }

    @Override
    public String type() {
        return mType;
    }

    @Override
    public boolean onCameraPreviewImageData(CameraPreviewImageData previewImageData) {
        if (mLastLogTimestamp < 0) {
            log();
            mLastLogTimestamp = System.currentTimeMillis();
            return false;
        }

        long currentTimestamp = System.currentTimeMillis();
        if (currentTimestamp > mLastLogTimestamp + 3000) {
            log();
            mLastLogTimestamp = currentTimestamp;
            return false;
        }

        return false;
    }

    private void log() {
        Log.d("tag", "RECOGNIZE_PERSON_WITH_FRONT_CAMERA_RUNNING");
    }
}
