package com.vision.services.camera.camera_manager.tasks.recognize_person_with_back_camera;

import android.content.Context;
import android.util.Log;

import com.vision.services.camera.CameraService;
import com.vision.services.camera.data.camera_frame_detections.CameraFrameDetections;
import com.vision.services.camera.camera_manager.CameraManager;
import com.vision.services.camera.data.camera_preview_frame_data.CameraPreviewFrameData;
import com.vision.services.camera.helpers.OpenCVHelper;

import org.opencv.core.Mat;

public class RecognizePersonWithBackCameraCameraManagerTask implements CameraManager.CameraManagerTask {
    private String mType;
    private int mImageRotationDeg;
    private Context mContext;
    private CameraService.OnFrameDetections mOnFrameDetections;
    private long mLastLogTimestamp;

    public RecognizePersonWithBackCameraCameraManagerTask(Context context,
                                                          int imageRotationDeg,
                                                          CameraService.OnFrameDetections onFrameDetections) {
        mType = CameraManager.RECOGNIZE_PERSON_WITH_BACK_CAMERA;

        mContext = context;
        mImageRotationDeg = imageRotationDeg;
        mOnFrameDetections = onFrameDetections;

        mLastLogTimestamp = -1;
    }

    @Override
    public String type() {
        return mType;
    }

    @Override
    public boolean onCameraPreviewImageData(CameraPreviewFrameData previewImageData) {
        long currentTimestamp = System.currentTimeMillis();

        if (mLastLogTimestamp < 0) {
            timedProcessImage(previewImageData);
            mLastLogTimestamp = currentTimestamp;
            return false;
        }

        if (currentTimestamp > mLastLogTimestamp + 3000) {
            timedProcessImage(previewImageData);
            mLastLogTimestamp = currentTimestamp;
            return false;
        }

        return false;
    }

    private void timedProcessImage(CameraPreviewFrameData previewImageData) {
        long start = System.currentTimeMillis();
        processImage(previewImageData);
        long end = System.currentTimeMillis();

        Log.d(
                "tag",
                "RecognizePersonWithBackCameraCameraManagerTask_V2->timedProcessImage()->TIME_ELAPSED: " + (end - start)
        );
    }

    private void processImage(CameraPreviewFrameData previewImageData) {
        if (previewImageData == null || !previewImageData.hasImage() || mContext == null) {
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

        CameraFrameDetections detections = OpenCVHelper.detectObjectsOnImageMat(mContext, rotatedMat);
        mOnFrameDetections.onFrameDetections(detections);
    }
}
