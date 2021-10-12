package com.vision.common.services.camera_motion_detection;


import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CameraMotionDetectionService {
    public static final String NAME = "CameraMotionDetectionService";

    private static CameraMotionDetectionService sInstance;

    private String TAG = "tag";
    private Camera mCurrentCamera;
    private int mPreviewFormat = ImageFormat.NV21;
    private SurfaceTexture mCurrentSurfaceTexture;
    private byte mBuffer[];
    private boolean mCameraRunning;

    public interface RequestImageCallback {
        void onRequestedImage(byte[] imageBytes, int width, int height, int previewFormat);
    }

    private RequestImageCallback mRequestImageCallback;

    private CameraMotionDetectionService() {
        mCameraRunning = false;
    }

    public static CameraMotionDetectionService get() {
        if (sInstance == null) {
            sInstance = new CameraMotionDetectionService();
        }

        return sInstance;
    }

    public boolean isRunning() {
        return mCameraRunning;
    }

    public void test() {
        Log.d("tag", "CameraMotionDetectionService->test()");

        if (mCameraRunning) {
            disposeCurrentCamera();
        } else {
            disposeCurrentCamera();
            try {
                startCurrentCamera();
            } catch (Exception e) {
                mCameraRunning = false;
                Log.d("tag", "CameraMotionDetectionService->test()->EXCEPTION");
                e.printStackTrace();
            }
        }
    }

    public void requestImage(RequestImageCallback requestImageCallback) {
        mRequestImageCallback = requestImageCallback;
    }

    private void startCurrentCamera() {
        Log.d("tag", "CameraMotionDetectionService->startCurrentCamera()");

        mCameraRunning = true;

        int backCameraId = getBackCameraId();
        if (backCameraId < 0) {
            Log.d("tag", "CameraMotionDetectionService->startCurrentCamera(): BAD_BACK_CAMERA_ID");
            return;
        }

        mCurrentCamera = Camera.open(backCameraId);
        if (mCurrentCamera == null) {
            Log.d("tag", "CameraMotionDetectionService->startCurrentCamera(): BAD_CURRENT_CAMERA");
            return;
        }

        Camera.Parameters parameters = mCurrentCamera.getParameters();

        List<android.hardware.Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
        Collections.sort(previewSizes, (a, b) -> (b.height * b.width) - (a.height * a.width));
        Camera.Size cameraPreviewSize = previewSizes.get(previewSizes.size() - 1);

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
        Log.d("tag", "PREVIEW_FORMAT: " + mPreviewFormat);

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

        // ===
        int width = parameters.getPreviewSize().width;
        int height = parameters.getPreviewSize().height;

        int size = width * height;
        size  = size * ImageFormat.getBitsPerPixel(parameters.getPreviewFormat()) / 8;
        Log.d(TAG, "CameraMotionDetectionService->startCurrentCamera()->BUFFER_SIZE: " + size);
        mBuffer = new byte[size];
        // ===

        mCurrentCamera.addCallbackBuffer(mBuffer);
        mCurrentCamera.setPreviewCallbackWithBuffer((bytes, camera) -> {
//            Log.d("tag", "ON_PREVIEW_CALLBACK: " + bytes.length);

            if (mRequestImageCallback != null) {
                mRequestImageCallback.onRequestedImage(bytes, width, height, mPreviewFormat);
                mRequestImageCallback = null;
            }

            if (mCurrentCamera != null) {
                mCurrentCamera.addCallbackBuffer(mBuffer);
            }

//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            if (mCurrentCamera != null) {
//                mCurrentCamera.addCallbackBuffer(mBuffer);
//            }
        });

//        mCurrentCamera.addCallbackBuffer(mBuffer);
//        mCurrentCamera.setPreviewCallback((bytes, camera) -> {
//            Log.d("tag", "ON_PREVIEW_CALLBACK: " + bytes.length);
//
////            try {
////                Log.d("tag", "ON_PREVIEW_CALLBACK->SLEEP");
////                Thread.sleep(500);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
//
//            if (mCurrentCamera != null) {
//                mCurrentCamera.addCallbackBuffer(mBuffer);
//            }
//        });

        mCurrentSurfaceTexture = new SurfaceTexture(1);
        try {
            mCurrentCamera.setPreviewTexture(mCurrentSurfaceTexture);
            mCurrentCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disposeCurrentCamera() {
        Log.d("tag", "CameraMotionDetectionService->disposeCurrentCamera()");

        mCameraRunning = false;

        try {
            if (mCurrentCamera != null) {
                mCurrentCamera.stopPreview();
                mCurrentCamera.setPreviewCallback(null);
                mCurrentCamera.release();
                mCurrentCamera = null;

                mCurrentSurfaceTexture.release();
            }
        } catch (Exception e) {
            Log.d("tag", "CameraMotionDetectionService->dispose()->ERROR");
            e.printStackTrace();
        }
    }

    private int getBackCameraId() {
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
}
