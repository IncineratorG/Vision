package com.vision.services.camera.camera_manager;

import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;

import com.vision.common.constants.AppConstants;
import com.vision.services.camera.camera_manager.tasks.take_back_camera_image.TakeBackCameraImageCameraManagerTask;
import com.vision.services.camera.data.camera_manager_tasks.CameraManagerTasks;
import com.vision.services.camera.data.camera_preview_image_data.CameraPreviewImageData;
import com.vision.services.camera.data.opencv.OpenCVHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CameraManager_V2 {
    public static final String TAKE_BACK_CAMERA_IMAGE = "TAKE_BACK_CAMERA_IMAGE";
    public static final String TAKE_FRONT_CAMERA_IMAGE = "TAKE_FRONT_CAMERA_IMAGE";

    public interface CameraManagerTask {
        String type();
        boolean onCameraPreviewImageData(CameraPreviewImageData previewImageData);
    }

    private Camera mFrontCamera;
    private SurfaceTexture mFrontCameraSurfaceTexture;
    private byte[] mFrontCameraBuffer;
    private int mFrontCameraPreviewFormat = ImageFormat.NV21;
    private boolean mFrontCameraRunning = false;
    private boolean mFrontCameraRecognizePersonRunning;
    private String mFrontCameraPreviousImageQuality;
    private CameraManagerTasks mFrontCameraTasks;

    private Camera mBackCamera;
    private SurfaceTexture mBackCameraSurfaceTexture;
    private byte[] mBackCameraBuffer;
    private int mBackCameraPreviewFormat = ImageFormat.NV21;
    private boolean mBackCameraRunning = false;
    private boolean mBackCameraRecognizePersonRunning;
    private String mBackCameraPreviousImageQuality;
    private CameraManagerTasks mBackCameraTasks;

    public CameraManager_V2() {
        mFrontCameraTasks = new CameraManagerTasks();
        mBackCameraTasks = new CameraManagerTasks();
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

    public boolean canRecognizePerson() {
        return OpenCVHelper.available();
    }

    public boolean isFrontCameraRecognizePersonRunning() {
        return mFrontCameraRecognizePersonRunning;
    }

    public boolean isBackCameraRecognizePersonRunning() {
        return mBackCameraRecognizePersonRunning;
    }

    public void executeTask(CameraManagerTask task) {
        if (task == null) {
            Log.d("tag", "CameraManager_V2->executeTask(): TASK_IS_NULL");
            return;
        }

        switch (task.type()) {
            case (TAKE_BACK_CAMERA_IMAGE): {
                if (!(task instanceof TakeBackCameraImageCameraManagerTask)) {
                    Log.d("tag", "CameraManager_V2->executeTask(): BAD_TASK_INSTANCE");
                    return;
                }

                break;
            }

            default: {
                Log.d("tag", "CameraManager_V2->executeTask()->UNKNOWN_TASK: " + task.type());
            }
        }
    }

    private void startFrontCameraPreview(String quality) {
        if (mFrontCameraRunning) {
            return;
        }

        int frontCameraId = getFrontCameraId();
        if (frontCameraId < 0) {
            Log.d("tag", "CameraService_V4->startFrontCameraPreview(): BAD_FRONT_CAMERA_ID");
            return;
        }

        mFrontCamera = Camera.open(frontCameraId);
        if (mFrontCamera == null) {
            Log.d("tag", "CameraService_V4->startFrontCameraPreview(): BAD_CURRENT_CAMERA");
            return;
        }

        Camera.Parameters parameters = mFrontCamera.getParameters();

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
        mFrontCameraPreviewFormat = parameters.getPreviewFormat();

        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
        Collections.sort(previewSizes, (a, b) -> (b.height * b.width) - (a.height * a.width));
        Camera.Size cameraPreviewSize = previewSizes.get(0);
        if (quality.equalsIgnoreCase(AppConstants.CAMERA_IMAGE_QUALITY_HIGH)) {
            cameraPreviewSize = previewSizes.get(0);
            Log.d("tag", "CameraService_V4->startFrontCameraPreview()->HIGH_IMAGE_QUALITY: " + cameraPreviewSize.width + " - " + cameraPreviewSize.height);
        } else if (quality.equalsIgnoreCase(AppConstants.CAMERA_IMAGE_QUALITY_MEDIUM)) {
            cameraPreviewSize = previewSizes.get(previewSizes.size() / 2);
            Log.d("tag", "CameraService_V4->startFrontCameraPreview()->MEDIUM_IMAGE_QUALITY: " + cameraPreviewSize.width + " - " + cameraPreviewSize.height);
        } else if (quality.equalsIgnoreCase(AppConstants.CAMERA_IMAGE_QUALITY_LOW)) {
            cameraPreviewSize = previewSizes.get(previewSizes.size() - 1);
            Log.d("tag", "CameraService_V4->startFrontCameraPreview()->LOW_IMAGE_QUALITY: " + cameraPreviewSize.width + " - " + cameraPreviewSize.height);
        }
        parameters.setPreviewSize(cameraPreviewSize.width, cameraPreviewSize.height);
        mFrontCameraPreviousImageQuality = quality;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && !android.os.Build.MODEL.equals("GT-I9100")) {
            parameters.setRecordingHint(true);
        }

        List<String> FocusModes = parameters.getSupportedFocusModes();
        if (FocusModes != null && FocusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        }

        try {
            mFrontCamera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int width = parameters.getPreviewSize().width;
        int height = parameters.getPreviewSize().height;

        int size = width * height;
        size  = size * ImageFormat.getBitsPerPixel(parameters.getPreviewFormat()) / 8;
        mFrontCameraBuffer = new byte[size];

        mFrontCamera.addCallbackBuffer(mFrontCameraBuffer);
        mFrontCamera.setPreviewCallbackWithBuffer((bytes, camera) -> {
            onFrontCameraPreviewFrameChanged(bytes, width, height, mFrontCameraPreviewFormat);

            if (mFrontCamera != null) {
                mFrontCamera.addCallbackBuffer(mFrontCameraBuffer);
            }
        });


        mFrontCameraSurfaceTexture = new SurfaceTexture(1);
        try {
            mFrontCamera.setPreviewTexture(mFrontCameraSurfaceTexture);
            mFrontCamera.startPreview();

            mFrontCameraRunning = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startBackCameraPreview(String quality) {
        if (mBackCameraRunning) {
            return;
        }

        int backCameraId = getBackCameraId();
        if (backCameraId < 0) {
            Log.d("tag", "CameraService_V4->startBackCameraPreview(): BAD_BACK_CAMERA_ID");
            return;
        }

        mBackCamera = Camera.open(backCameraId);
        if (mBackCamera == null) {
            Log.d("tag", "CameraService_V4->startBackCameraPreview(): BAD_CURRENT_CAMERA");
            return;
        }

        Camera.Parameters parameters = mBackCamera.getParameters();

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
        mBackCameraPreviewFormat = parameters.getPreviewFormat();

        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
        Collections.sort(previewSizes, (a, b) -> (b.height * b.width) - (a.height * a.width));
        Camera.Size cameraPreviewSize = previewSizes.get(0);
        if (quality.equalsIgnoreCase(AppConstants.CAMERA_IMAGE_QUALITY_HIGH)) {
            cameraPreviewSize = previewSizes.get(0);
            Log.d("tag", "CameraService_V4->startBackCameraPreview()->HIGH_IMAGE_QUALITY: " + cameraPreviewSize.width + " - " + cameraPreviewSize.height);
        } else if (quality.equalsIgnoreCase(AppConstants.CAMERA_IMAGE_QUALITY_MEDIUM)) {
            cameraPreviewSize = previewSizes.get(previewSizes.size() / 2);
            Log.d("tag", "CameraService_V4->startBackCameraPreview()->MEDIUM_IMAGE_QUALITY: " + cameraPreviewSize.width + " - " + cameraPreviewSize.height);
        } else if (quality.equalsIgnoreCase(AppConstants.CAMERA_IMAGE_QUALITY_LOW)) {
            cameraPreviewSize = previewSizes.get(previewSizes.size() - 1);
            Log.d("tag", "CameraService_V4->startBackCameraPreview()->LOW_IMAGE_QUALITY: " + cameraPreviewSize.width + " - " + cameraPreviewSize.height);
        }
        parameters.setPreviewSize(cameraPreviewSize.width, cameraPreviewSize.height);
        mBackCameraPreviousImageQuality = quality;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && !android.os.Build.MODEL.equals("GT-I9100")) {
            parameters.setRecordingHint(true);
        }

        List<String> FocusModes = parameters.getSupportedFocusModes();
        if (FocusModes != null && FocusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        }

        try {
            mBackCamera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int width = parameters.getPreviewSize().width;
        int height = parameters.getPreviewSize().height;

        int size = width * height;
        size  = size * ImageFormat.getBitsPerPixel(parameters.getPreviewFormat()) / 8;
        mBackCameraBuffer = new byte[size];

        mBackCamera.addCallbackBuffer(mBackCameraBuffer);
        mBackCamera.setPreviewCallbackWithBuffer((bytes, camera) -> {
            onBackCameraPreviewFrameChanged(bytes, width, height, mBackCameraPreviewFormat);

            if (mBackCamera != null) {
                mBackCamera.addCallbackBuffer(mBackCameraBuffer);
            }
        });


        mBackCameraSurfaceTexture = new SurfaceTexture(1);
        try {
            mBackCamera.setPreviewTexture(mBackCameraSurfaceTexture);
            mBackCamera.startPreview();

            mBackCameraRunning = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopFrontCameraPreview() {
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
            mFrontCameraTasks.clear();

//            clearFrontCameraPreviewImageData();
//            clearFrontCameraFrameChangeTasks();

            mFrontCameraRunning = false;

            mFrontCameraPreviousImageQuality = null;
        } catch (Exception e) {
            Log.d("tag", "CameraService_V4->stopFrontCameraPreview()->ERROR");
            e.printStackTrace();
        }
    }

    private void stopBackCameraPreview() {
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
            mBackCameraTasks.clear();

//            clearBackCameraPreviewImageData();
//            clearBackCameraFrameChangeTasks();

            mBackCameraRunning = false;

            mBackCameraPreviousImageQuality = null;
        } catch (Exception e) {
            Log.d("tag", "CameraService_V4->stopBackCameraPreview()->ERROR");
            e.printStackTrace();
        }
    }

    private void pauseFrontCameraPreview() {
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
//            clearFrontCameraPreviewImageData();

            mFrontCameraRunning = false;
        } catch (Exception e) {
            Log.d("tag", "CameraService_V4->pauseFrontCameraPreview()->ERROR");
            e.printStackTrace();
        }
    }

    private void pauseBackCameraPreview() {
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
//            clearBackCameraPreviewImageData();

            mBackCameraRunning = false;
        } catch (Exception e) {
            Log.d("tag", "CameraService_V4->pauseBackCameraPreview()->ERROR");
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

    private int getFrontCameraId() {
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

    private synchronized void onBackCameraPreviewFrameChanged(byte[] previewImageBytes,
                                                              int imageWidth,
                                                              int imageHeight,
                                                              int imageFormat) {
        if (previewImageBytes == null) {
            return;
        }

        CameraPreviewImageData previewImageData = new CameraPreviewImageData(
                previewImageBytes,
                imageWidth,
                imageHeight,
                imageFormat
        );

        int remainingTasks = mBackCameraTasks.onFrameChanged(previewImageData);
        if (remainingTasks == 0) {
            stopBackCameraPreview();
        }
    }

    private synchronized void onFrontCameraPreviewFrameChanged(byte[] previewImageBytes,
                                                               int imageWidth,
                                                               int imageHeight,
                                                               int imageFormat) {
        if (previewImageBytes == null) {
            return;
        }

        CameraPreviewImageData previewImageData = new CameraPreviewImageData(
                previewImageBytes,
                imageWidth,
                imageHeight,
                imageFormat
        );

        int remainingTasks = mFrontCameraTasks.onFrameChanged(previewImageData);
        if (remainingTasks == 0) {
            stopFrontCameraPreview();
        }
    }
}

//    private synchronized CameraPreviewImageData getFrontCameraPreviewImageData() {
//        if (mFrontCameraPreviewImageData == null) {
//            return null;
//        }
//
//        return new CameraPreviewImageData(mFrontCameraPreviewImageData);
//    }
//
//    private synchronized CameraPreviewImageData getBackCameraPreviewImageData() {
//        if (mBackCameraPreviewImageData == null) {
//            return null;
//        }
//
//        return new CameraPreviewImageData(mBackCameraPreviewImageData);
//    }
//
//    private synchronized void setFrontCameraPreviewImageData(byte[] previewImageBytes,
//                                                             int imageWidth,
//                                                             int imageHeight,
//                                                             int imageFormat) {
//        if (previewImageBytes != null) {
//            mFrontCameraPreviewImageData = new CameraPreviewImageData(
//                    previewImageBytes, imageWidth, imageHeight, imageFormat
//            );
//        }
//    }
//
//    private synchronized void setBackCameraPreviewImageData(byte[] previewImageBytes,
//                                                            int imageWidth,
//                                                            int imageHeight,
//                                                            int imageFormat) {
//        if (previewImageBytes != null) {
//            mBackCameraPreviewImageData = new CameraPreviewImageData(
//                    previewImageBytes, imageWidth, imageHeight, imageFormat
//            );
//        }
//    }
//
//    private synchronized void clearFrontCameraPreviewImageData() {
//        mFrontCameraPreviewImageData = null;
//    }
//
//    private synchronized void clearBackCameraPreviewImageData() {
//        mBackCameraPreviewImageData = null;
//    }

//    private synchronized void clearFrontCameraFrameChangeTasks() {
//        mFrontCameraFrameChangeTasks.clear();
//    }
//
//    private synchronized void clearBackCameraFrameChangeTasks() {
//        mBackCameraFrameChangeTasks.clear();
//    }
