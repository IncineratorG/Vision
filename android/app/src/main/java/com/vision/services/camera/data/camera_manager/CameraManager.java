package com.vision.services.camera.data.camera_manager;

import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;

import com.vision.common.constants.AppConstants;
import com.vision.services.camera.data.camera_manager.tasks.recognize_person_with_back_camera.RecognizePersonWithBackCameraCameraManagerTask;
import com.vision.services.camera.data.camera_manager.tasks.recognize_person_with_front_camera.RecognizePersonWithFrontCameraCameraManagerTask;
import com.vision.services.camera.data.camera_manager.tasks.take_back_camera_image.TakeBackCameraImageCameraManagerTask;
import com.vision.services.camera.data.camera_manager.tasks.take_front_camera_image.TakeFrontCameraImageCameraManagerTask;
import com.vision.services.camera.data.camera_manager.camera_manager_tasks.CameraManagerTasks;
import com.vision.services.camera.data.camera_preview_frame_data.CameraPreviewFrameData;
import com.vision.services.camera.data.helpers.OpenCVHelper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CameraManager {
    public static final String TAKE_BACK_CAMERA_IMAGE = "TAKE_BACK_CAMERA_IMAGE";
    public static final String TAKE_FRONT_CAMERA_IMAGE = "TAKE_FRONT_CAMERA_IMAGE";
    public static final String RECOGNIZE_PERSON_WITH_BACK_CAMERA = "RECOGNIZE_PERSON_WITH_BACK_CAMERA";
    public static final String RECOGNIZE_PERSON_WITH_FRONT_CAMERA = "RECOGNIZE_PERSON_WITH_FRONT_CAMERA";

    public interface CameraManagerTask {
        String type();
        boolean onCameraPreviewImageData(CameraPreviewFrameData previewImageData);
    }

    public interface CameraManagerTaskCleanup {
        void cleanup();
    }

    private Camera mFrontCamera;
    private SurfaceTexture mFrontCameraSurfaceTexture;
    private byte[] mFrontCameraBuffer;
    private int mFrontCameraPreviewFormat = ImageFormat.NV21;
    private boolean mFrontCameraRunning = false;
    private String mFrontCameraPreviousImageQuality;
    private CameraManagerTasks mFrontCameraTasks;

    private Camera mBackCamera;
    private SurfaceTexture mBackCameraSurfaceTexture;
    private byte[] mBackCameraBuffer;
    private int mBackCameraPreviewFormat = ImageFormat.NV21;
    private boolean mBackCameraRunning = false;
    private String mBackCameraPreviousImageQuality;
    private CameraManagerTasks mBackCameraTasks;

    public CameraManager() {
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
        return mFrontCameraTasks.tasksOfTypeCount(RECOGNIZE_PERSON_WITH_FRONT_CAMERA) > 0;
    }

    public boolean isBackCameraRecognizePersonRunning() {
        return mBackCameraTasks.tasksOfTypeCount(RECOGNIZE_PERSON_WITH_BACK_CAMERA) > 0;
    }

    public void executeTask(CameraManagerTask task) {
        if (task == null) {
            Log.d("tag", "CameraManager->executeTask(): TASK_IS_NULL");
            return;
        }

        switch (task.type()) {
            case (TAKE_FRONT_CAMERA_IMAGE): {
                if (!(task instanceof TakeFrontCameraImageCameraManagerTask)) {
                    Log.d("tag", "CameraManager->executeTask()->TAKE_FRONT_CAMERA_IMAGE->BAD_TASK_INSTANCE");
                    return;
                }

                boolean backCameraWasRunning = false;
                if (mBackCameraRunning) {
                    backCameraWasRunning = true;
                    pauseBackCameraPreview();
                }

                mFrontCameraTasks.add(task);
                if (backCameraWasRunning) {
                    CameraManagerTaskCleanup cleanup = this::resumeBackCameraPreview;
                    mFrontCameraTasks.addTaskTypeCleanup(TAKE_FRONT_CAMERA_IMAGE, cleanup);
                }

                if (!mFrontCameraRunning) {
                    startFrontCameraPreview(((TakeFrontCameraImageCameraManagerTask) task).imageQuality());
                }

                break;
            }

            case (TAKE_BACK_CAMERA_IMAGE): {
                if (!(task instanceof TakeBackCameraImageCameraManagerTask)) {
                    Log.d("tag", "CameraManager->executeTask()->TAKE_BACK_CAMERA_IMAGE->BAD_TASK_INSTANCE");
                    return;
                }

                boolean frontCameraWasRunning = false;
                if (mFrontCameraRunning) {
                    frontCameraWasRunning = true;
                    pauseFrontCameraPreview();
                }

                mBackCameraTasks.add(task);
                if (frontCameraWasRunning) {
                    CameraManagerTaskCleanup cleanup = this::resumeFrontCameraPreview;
                    mBackCameraTasks.addTaskTypeCleanup(TAKE_BACK_CAMERA_IMAGE, cleanup);
                }

                if (!mBackCameraRunning) {
                    startBackCameraPreview(((TakeBackCameraImageCameraManagerTask) task).imageQuality());
                }

                break;
            }

            case (RECOGNIZE_PERSON_WITH_BACK_CAMERA): {
                if (!(task instanceof RecognizePersonWithBackCameraCameraManagerTask)) {
                    Log.d("tag", "CameraManager->executeTask()->RECOGNIZE_PERSON_WITH_BACK_CAMERA->BAD_TASK_INSTANCE");
                    return;
                }

                if (mBackCameraTasks.tasksOfTypeCount(RECOGNIZE_PERSON_WITH_BACK_CAMERA) > 0) {
                    Log.d("tag", "CameraManager->executeTask()->RECOGNIZE_PERSON_WITH_BACK_CAMERA->TASK_ALREADY_RUNNING");
                    return;
                }

                if (mFrontCameraRunning) {
                    stopFrontCameraPreview();
                }

                mBackCameraTasks.add(task);
                if (!mBackCameraRunning) {
                    startBackCameraPreview(AppConstants.CAMERA_IMAGE_QUALITY_HIGH);
                }

                break;
            }

            case (RECOGNIZE_PERSON_WITH_FRONT_CAMERA): {
                if (!(task instanceof RecognizePersonWithFrontCameraCameraManagerTask)) {
                    Log.d("tag", "CameraManager->executeTask()->RECOGNIZE_PERSON_WITH_FRONT_CAMERA->BAD_TASK_INSTANCE");
                    return;
                }

                if (mFrontCameraTasks.tasksOfTypeCount(RECOGNIZE_PERSON_WITH_FRONT_CAMERA) > 0) {
                    Log.d("tag", "CameraManager->executeTask()->RECOGNIZE_PERSON_WITH_FRONT_CAMERA->TASK_ALREADY_RUNNING");
                    return;
                }

                if (mBackCameraRunning) {
                    stopBackCameraPreview();
                }

                mFrontCameraTasks.add(task);
                if (!mFrontCameraRunning) {
                    startFrontCameraPreview(AppConstants.CAMERA_IMAGE_QUALITY_HIGH);
                }

                break;
            }

            default: {
                Log.d("tag", "CameraManager->executeTask()->UNKNOWN_TASK: " + task.type());
            }
        }
    }

    public void stopTaskOfType(String taskType) {
        switch (taskType) {
            case (RECOGNIZE_PERSON_WITH_BACK_CAMERA): {
                mBackCameraTasks.removeTasksOfType(RECOGNIZE_PERSON_WITH_BACK_CAMERA);
                break;
            }

            case (RECOGNIZE_PERSON_WITH_FRONT_CAMERA): {
                mFrontCameraTasks.removeTasksOfType(RECOGNIZE_PERSON_WITH_FRONT_CAMERA);
                break;
            }

            default: {
                Log.d("tag", "CameraManager->stopTaskOfType()->UNKNOWN_TASK_TYPE: " + taskType);
            }
        }
    }

    public void stopAllTasks() {
        stopFrontCameraPreview();
        stopBackCameraPreview();
    }

    private void startFrontCameraPreview(String quality) {
        if (mFrontCameraRunning) {
            return;
        }
        if (mBackCameraRunning) {
            Log.d("tag", "CameraManager->startFrontCameraPreview()->CAN_NOT_START->BACK_CAMERA_RUNNING");
            return;
        }

        int frontCameraId = getFrontCameraId();
        if (frontCameraId < 0) {
            Log.d("tag", "CameraManager->startFrontCameraPreview(): BAD_FRONT_CAMERA_ID");
            return;
        }

        mFrontCamera = Camera.open(frontCameraId);
        if (mFrontCamera == null) {
            Log.d("tag", "CameraManager->startFrontCameraPreview(): BAD_CURRENT_CAMERA");
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
            Log.d("tag", "CameraManager->startFrontCameraPreview()->HIGH_IMAGE_QUALITY: " + cameraPreviewSize.width + " - " + cameraPreviewSize.height);
        } else if (quality.equalsIgnoreCase(AppConstants.CAMERA_IMAGE_QUALITY_MEDIUM)) {
            cameraPreviewSize = previewSizes.get(previewSizes.size() / 2);
            Log.d("tag", "CameraManager->startFrontCameraPreview()->MEDIUM_IMAGE_QUALITY: " + cameraPreviewSize.width + " - " + cameraPreviewSize.height);
        } else if (quality.equalsIgnoreCase(AppConstants.CAMERA_IMAGE_QUALITY_LOW)) {
            cameraPreviewSize = previewSizes.get(previewSizes.size() - 1);
            Log.d("tag", "CameraManager->startFrontCameraPreview()->LOW_IMAGE_QUALITY: " + cameraPreviewSize.width + " - " + cameraPreviewSize.height);
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
        if (mFrontCameraRunning) {
            Log.d("tag", "CameraManager->startBackCameraPreview()->CAN_NOT_START->FRONT_CAMERA_RUNNING");
            return;
        }

        int backCameraId = getBackCameraId();
        if (backCameraId < 0) {
            Log.d("tag", "CameraManager->startBackCameraPreview(): BAD_BACK_CAMERA_ID");
            return;
        }

        mBackCamera = Camera.open(backCameraId);
        if (mBackCamera == null) {
            Log.d("tag", "CameraManager->startBackCameraPreview(): BAD_CURRENT_CAMERA");
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
            Log.d("tag", "CameraManager->startBackCameraPreview()->HIGH_IMAGE_QUALITY: " + cameraPreviewSize.width + " - " + cameraPreviewSize.height);
        } else if (quality.equalsIgnoreCase(AppConstants.CAMERA_IMAGE_QUALITY_MEDIUM)) {
            cameraPreviewSize = previewSizes.get(previewSizes.size() / 2);
            Log.d("tag", "CameraManager->startBackCameraPreview()->MEDIUM_IMAGE_QUALITY: " + cameraPreviewSize.width + " - " + cameraPreviewSize.height);
        } else if (quality.equalsIgnoreCase(AppConstants.CAMERA_IMAGE_QUALITY_LOW)) {
            cameraPreviewSize = previewSizes.get(previewSizes.size() - 1);
            Log.d("tag", "CameraManager->startBackCameraPreview()->LOW_IMAGE_QUALITY: " + cameraPreviewSize.width + " - " + cameraPreviewSize.height);
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

    private void resumeFrontCameraPreview() {
        if (mFrontCameraPreviousImageQuality != null) {
            startFrontCameraPreview(mFrontCameraPreviousImageQuality);
        } else {
            startFrontCameraPreview(AppConstants.CAMERA_IMAGE_QUALITY_HIGH);
        }
    }

    private void resumeBackCameraPreview() {
        if (mBackCameraPreviousImageQuality != null) {
            startBackCameraPreview(mBackCameraPreviousImageQuality);
        } else {
            startBackCameraPreview(AppConstants.CAMERA_IMAGE_QUALITY_HIGH);
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
            mFrontCameraTasks.clear(true);

            mFrontCameraRunning = false;

            mFrontCameraPreviousImageQuality = null;
        } catch (Exception e) {
            Log.d("tag", "CameraManager->stopFrontCameraPreview()->ERROR");
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
            mBackCameraTasks.clear(true);

            mBackCameraRunning = false;

            mBackCameraPreviousImageQuality = null;
        } catch (Exception e) {
            Log.d("tag", "CameraManager->stopBackCameraPreview()->ERROR");
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

            mFrontCameraRunning = false;
        } catch (Exception e) {
            Log.d("tag", "CameraManager->pauseFrontCameraPreview()->ERROR");
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

            mBackCameraRunning = false;
        } catch (Exception e) {
            Log.d("tag", "CameraManager->pauseBackCameraPreview()->ERROR");
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

        CameraPreviewFrameData previewImageData = new CameraPreviewFrameData(
                previewImageBytes,
                imageWidth,
                imageHeight,
                imageFormat
        );

        int remainingTasks = mBackCameraTasks.onFrameChanged(previewImageData);
        if (remainingTasks == 0) {
            stopBackCameraPreview();
            mBackCameraTasks.runCleanups();
            mBackCameraTasks.clear(false);
        }
    }

    private synchronized void onFrontCameraPreviewFrameChanged(byte[] previewImageBytes,
                                                               int imageWidth,
                                                               int imageHeight,
                                                               int imageFormat) {
        if (previewImageBytes == null) {
            return;
        }

        CameraPreviewFrameData previewImageData = new CameraPreviewFrameData(
                previewImageBytes,
                imageWidth,
                imageHeight,
                imageFormat
        );

        int remainingTasks = mFrontCameraTasks.onFrameChanged(previewImageData);
        if (remainingTasks == 0) {
            stopFrontCameraPreview();
            mFrontCameraTasks.runCleanups();
            mFrontCameraTasks.clear(false);
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
