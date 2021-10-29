package com.vision.services.camera;


import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;

import com.vision.services.camera.data.camera_preview_image_data.CameraPreviewImageData;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CameraService_V3 {
    public static final String NAME = "CameraService_V3";

    public static CameraService_V3 sInstance;

    private Camera mCurrentCamera;
    private SurfaceTexture mCurrentSurfaceTexture;
    private byte mBuffer[];

    private CameraPreviewImageData mCurrentCameraPreviewImageData;

    private int mPreviewFormat = ImageFormat.NV21;
    private boolean mCameraRunning = false;

    private CameraService_V3() {

    }

    public static CameraService_V3 get() {
        if (sInstance == null) {
            sInstance = new CameraService_V3();
        }

        return sInstance;
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

    public boolean isCameraPreviewRunning() {
        return mCameraRunning;
    }

    public void startCameraPreview() {
        if (mCameraRunning) {
            return;
        }

        int backCameraId = getBackCameraId();
        if (backCameraId < 0) {
            Log.d("tag", "CameraService_V3->startCameraPreview(): BAD_BACK_CAMERA_ID");
            return;
        }

        mCurrentCamera = Camera.open(backCameraId);
        if (mCurrentCamera == null) {
            Log.d("tag", "CameraService_V3->startCameraPreview(): BAD_CURRENT_CAMERA");
            return;
        }

        Camera.Parameters parameters = mCurrentCamera.getParameters();

        /* Image format NV21 causes issues in the Android emulators */
        if (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT)) {
            parameters.setPreviewFormat(ImageFormat.YV12);  // "generic" or "android" = android emulator
        } else {
            parameters.setPreviewFormat(ImageFormat.NV21);
        }
        mPreviewFormat = parameters.getPreviewFormat();

        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
        Collections.sort(previewSizes, (a, b) -> (b.height * b.width) - (a.height * a.width));
        Camera.Size cameraPreviewSize = previewSizes.get(0);
        parameters.setPreviewSize(cameraPreviewSize.width, cameraPreviewSize.height);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && !android.os.Build.MODEL.equals("GT-I9100")) {
            parameters.setRecordingHint(true);
        }

        List<String> FocusModes = parameters.getSupportedFocusModes();
        if (FocusModes != null && FocusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        }

        try {
            mCurrentCamera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int width = parameters.getPreviewSize().width;
        int height = parameters.getPreviewSize().height;

        int size = width * height;
        size  = size * ImageFormat.getBitsPerPixel(parameters.getPreviewFormat()) / 8;
        Log.d("tag", "CameraService_V3->startCameraPreview()->BUFFER_SIZE: " + size);
        mBuffer = new byte[size];

        mCurrentCamera.addCallbackBuffer(mBuffer);
        mCurrentCamera.setPreviewCallbackWithBuffer((bytes, camera) -> {
            setPreviewImageData(bytes, width, height, mPreviewFormat);

            if (mCurrentCamera != null) {
                mCurrentCamera.addCallbackBuffer(mBuffer);
            }
        });

        mCurrentSurfaceTexture = new SurfaceTexture(1);
        try {
            mCurrentCamera.setPreviewTexture(mCurrentSurfaceTexture);
            mCurrentCamera.startPreview();

            mCameraRunning = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopCameraPreview() {
        try {
            if (mCurrentCamera != null) {
                mCurrentCamera.stopPreview();
                mCurrentCamera.setPreviewCallback(null);
                mCurrentCamera.release();
                mCurrentCamera = null;

                if (mCurrentSurfaceTexture != null) {
                    mCurrentSurfaceTexture.release();
                }
            }
            mCurrentCameraPreviewImageData = null;

            mCameraRunning = false;
        } catch (Exception e) {
            Log.d("tag", "CameraService_V3->stopCameraPreview()->ERROR");
            e.printStackTrace();
        }
    }

    public synchronized CameraPreviewImageData getPreviewImageData() {
        if (mCurrentCameraPreviewImageData == null) {
            return null;
        }

        return new CameraPreviewImageData(mCurrentCameraPreviewImageData);
    }

    private synchronized void setPreviewImageData(byte[] previewImageBytes,
                                                  int imageWidth,
                                                  int imageHeight,
                                                  int imageFormat) {
        if (previewImageBytes != null) {
            mCurrentCameraPreviewImageData = new CameraPreviewImageData(
                    previewImageBytes, imageWidth, imageHeight, imageFormat
            );
        }
    }
}
