package com.vision.common.services.camera;


import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Base64;
import android.util.Log;

import com.vision.common.services.camera.callbacks.OnImageTakeError;
import com.vision.common.services.camera.callbacks.OnImageTaken;
import com.vision.common.services.camera.data.opencv.OpenCVHelper;

public class CameraService_V4 {
    public static final String NAME = "CameraService_V4";

    private static CameraService_V4 sInstance;

    private Camera mFrontCamera;
    private SurfaceTexture mFrontCameraSurfaceTexture;
    private byte[] mFrontCameraBuffer;
    private int mFrontCameraPreviewFormat = ImageFormat.NV21;
    private boolean mFrontCameraRunning = false;
    private CameraPreviewImageData mFrontCameraPreviewImageData;

    private Camera mBackCamera;
    private SurfaceTexture mBackCameraSurfaceTexture;
    private byte[] mBackCameraBuffer;
    private int mBackCameraPreviewFormat = ImageFormat.NV21;
    private boolean mBackCameraRunning = false;
    private CameraPreviewImageData mBackCameraPreviewImageData;

    private CameraService_V4() {

    }

    public static CameraService_V4 get() {
        if (sInstance == null) {
            sInstance = new CameraService_V4();
        }

        return sInstance;
    }

    public boolean hasBackCamera() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                return true;
            }
        }
        return false;
    }

    public boolean hasFrontCamera() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                return true;
            }
        }
        return false;
    }

    public boolean canRecognizeObjects() {
        return OpenCVHelper.available();
    }

    public int getBackCameraId() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int camerasCount = Camera.getNumberOfCameras();
        for (int cameraIdx = 0; cameraIdx < camerasCount; ++cameraIdx) {
            Camera.getCameraInfo(cameraIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                return cameraIdx;
            }
        }

        return -1;
    }

    public int getFrontCameraId() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int camerasCount = Camera.getNumberOfCameras();
        for (int cameraIdx = 0; cameraIdx < camerasCount; ++cameraIdx) {
            Camera.getCameraInfo(cameraIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                return cameraIdx;
            }
        }

        return -1;
    }

    public void takeCameraImage(int cameraId,
                                String quality,
                                OnImageTaken imageTakenCallback,
                                OnImageTakeError errorCallback) {
    }

    public void startFrontCameraPreview() {

    }

    public void startBackCameraPreview() {

    }

    public void stopFrontCameraPreview() {
        try {
            if (mFrontCamera != null) {
                mFrontCamera.stopPreview();
                mFrontCamera.setPreviewCallback(null);
                mFrontCamera.release();
                mFrontCamera = null;

                if (mFrontCameraSurfaceTexture != null) {
                    mFrontCameraSurfaceTexture.release();
                }
            }
            mFrontCameraPreviewImageData = null;

            mFrontCameraRunning = false;
        } catch (Exception e) {
            Log.d("tag", "CameraService_V4->stopFrontCameraPreview()->ERROR");
            e.printStackTrace();
        }
    }

    public void stopBackCameraPreview() {
        try {
            if (mBackCamera != null) {
                mBackCamera.stopPreview();
                mBackCamera.setPreviewCallback(null);
                mBackCamera.release();
                mBackCamera = null;

                if (mBackCameraSurfaceTexture != null) {
                    mBackCameraSurfaceTexture.release();
                }
            }
            mBackCameraPreviewImageData = null;

            mBackCameraRunning = false;
        } catch (Exception e) {
            Log.d("tag", "CameraService_V4->stopBackCameraPreview()->ERROR");
            e.printStackTrace();
        }
    }
}
