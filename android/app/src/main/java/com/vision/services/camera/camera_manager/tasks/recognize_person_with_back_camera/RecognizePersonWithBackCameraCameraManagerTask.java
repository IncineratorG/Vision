package com.vision.services.camera.camera_manager.tasks.recognize_person_with_back_camera;

import android.util.Log;

import com.vision.services.camera.camera_manager.CameraManager_V2;
import com.vision.services.camera.data.camera_preview_image_data.CameraPreviewImageData;
import com.vision.services.camera.data.opencv.OpenCVHelper;

import org.opencv.core.Mat;

public class RecognizePersonWithBackCameraCameraManagerTask implements CameraManager_V2.CameraManagerTask {
    private String mType;
    private int mImageRotationDeg;
    private long mLastLogTimestamp;

    public RecognizePersonWithBackCameraCameraManagerTask(int imageRotationDeg) {
        mType = CameraManager_V2.RECOGNIZE_PERSON_WITH_BACK_CAMERA;
        mImageRotationDeg = imageRotationDeg;
        mLastLogTimestamp = -1;
    }

    @Override
    public String type() {
        return mType;
    }

    @Override
    public boolean onCameraPreviewImageData(CameraPreviewImageData previewImageData) {
        long currentTimestamp = System.currentTimeMillis();
        Log.d("tag", "RecognizePersonWithBackCameraCameraManagerTask->TIMESTAMP: " + currentTimestamp);

        if (mLastLogTimestamp < 0) {
            timedProcessImage(previewImageData);
            mLastLogTimestamp = currentTimestamp;
            return false;
        }

        if (currentTimestamp > mLastLogTimestamp + 3000) {
            timedProcessImage(previewImageData);;
            mLastLogTimestamp = currentTimestamp;
            return false;
        }

        return false;
    }

    private void log() {
        Log.d("tag", "RECOGNIZE_PERSON_WITH_BACK_CAMERA_RUNNING");
    }

    private void timedProcessImage(CameraPreviewImageData previewImageData) {
        long start = System.currentTimeMillis();
        processImage(previewImageData);
        long end = System.currentTimeMillis();

        Log.d(
                "tag",
                "RecognizePersonWithBackCameraCameraManagerTask->timedProcessImage()->TIME_ELAPSED: " + (end - start)
        );
    }

    private void processImage(CameraPreviewImageData previewImageData) {
        Log.d("tag", "RecognizePersonWithBackCameraCameraManagerTask->processImage()");

        if (previewImageData == null || !previewImageData.hasImage()) {
            return;
        }

        byte[] yuvBytes = previewImageData.imageBytes();
        int imageFormat = previewImageData.imageFormat();
        int width = previewImageData.width();
        int height = previewImageData.height();

        Mat rgbaMap = OpenCVHelper.yuvBytesToRgbaMat(yuvBytes, width, height, imageFormat);
        if (rgbaMap == null) {
            return;
        }

        Mat rotatedMat = null;
        if (mImageRotationDeg > 0) {
            rotatedMat = OpenCVHelper.rotateMat(rgbaMap, mImageRotationDeg);
        } else {
            rotatedMat = rgbaMap.clone();
        }
    }
}
