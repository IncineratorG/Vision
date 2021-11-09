package com.vision.services.camera.data.camera_manager.tasks.recognize_person_with_front_camera;

import android.util.Log;

import com.vision.services.camera.data.camera_manager.CameraManager;
import com.vision.services.camera.data.camera_preview_frame_data.CameraPreviewFrameData;

public class RecognizePersonWithFrontCameraCameraManagerTask implements CameraManager.CameraManagerTask {
    private String mType;
    private int mImageRotationDeg;
    private long mLastLogTimestamp;

    public RecognizePersonWithFrontCameraCameraManagerTask(int imageRotationDeg) {
        mType = CameraManager.RECOGNIZE_PERSON_WITH_FRONT_CAMERA;
        mImageRotationDeg = imageRotationDeg;
        mLastLogTimestamp = -1;
    }

    @Override
    public String type() {
        return mType;
    }

    @Override
    public boolean onCameraPreviewImageData(CameraPreviewFrameData previewImageData) {
        long currentTimestamp = System.currentTimeMillis();
        Log.d("tag", "RecognizePersonWithFrontCameraCameraManagerTask->TIMESTAMP: " + currentTimestamp);

        if (mLastLogTimestamp < 0) {
            log();
            mLastLogTimestamp = currentTimestamp;
            return false;
        }

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
