//package com.vision.services.camera;
//
//
//import com.vision.services.camera.data.callbacks.OnImageTakeError;
//import com.vision.services.camera.data.callbacks.OnImageTaken;
//import com.vision.services.camera.camera_manager.CameraManager;
//
//public class CameraService_V4 {
//    public static final String NAME = "CameraService_V4";
//
//    private static CameraService_V4 sInstance;
//
//    private CameraManager mCameraManager;
//
//    private CameraService_V4() {
//        mCameraManager = new CameraManager();
//    }
//
//    public static CameraService_V4 get() {
//        if (sInstance == null) {
//            sInstance = new CameraService_V4();
//        }
//
//        return sInstance;
//    }
//
//    public boolean hasBackCamera() {
//        return mCameraManager.hasBackCamera();
//    }
//
//    public boolean hasFrontCamera() {
//        return mCameraManager.hasFrontCamera();
//    }
//
//    public boolean canRecognizePerson() {
//        return mCameraManager.canRecognizePerson();
//    }
//
//    public boolean isFrontCameraRecognizePersonRunning() {
//        return mCameraManager.isFrontCameraRecognizePersonRunning();
//    }
//
//    public boolean isBackCameraRecognizePersonRunning() {
//        return mCameraManager.isBackCameraRecognizePersonRunning();
//    }
//
//    public void takeBackCameraImage(String quality,
//                                    OnImageTaken imageTakenCallback,
//                                    OnImageTakeError errorCallback) {
////        if (mBackCameraRunning) {
////            CameraPreviewImageData cameraPreviewImageData = getBackCameraPreviewImageData();
////            if (cameraPreviewImageData == null || !cameraPreviewImageData.hasImage()) {
////                errorCallback.onError("1", "BAD_CAMERA_PREVIEW_IMAGE_DATA");
////                return;
////            }
////
////            String base64 = yuvToBase64(
////                    cameraPreviewImageData.imageBytes(),
////                    cameraPreviewImageData.imageFormat(),
////                    cameraPreviewImageData.width(),
////                    cameraPreviewImageData.height()
////            );
////
////            imageTakenCallback.onImageTaken(base64);
////        } else {
////            long taskStartTimestamp = System.currentTimeMillis();
////
////            CameraFrameChangedTask takeBackCameraImageTask = previewFrame -> {
////                if (System.currentTimeMillis() < taskStartTimestamp + 1000) {
////                    return false;
////                }
////
////                if (previewFrame == null || !previewFrame.hasImage()) {
////                    errorCallback.onError("1", "BAD_CAMERA_PREVIEW_IMAGE_DATA");
////                    return true;
////                }
////
////                String base64 = yuvToBase64(
////                        previewFrame.imageBytes(),
////                        previewFrame.imageFormat(),
////                        previewFrame.width(),
////                        previewFrame.height()
////                );
////
////                imageTakenCallback.onImageTaken(base64);
////
////                stopBackCameraPreview();
////
////                return true;
////            };
////
////            String taskId = String.valueOf(System.currentTimeMillis()) + UUID.randomUUID().toString();
////            mBackCameraFrameChangeTasks.put(taskId, takeBackCameraImageTask);
////
////            startBackCameraPreview(quality);
////        }
//    }
//
//    public void takeFrontCameraImage(String quality,
//                                     OnImageTaken imageTakenCallback,
//                                     OnImageTakeError errorCallback) {
////        if (mFrontCameraRunning) {
////            CameraPreviewImageData cameraPreviewImageData = getFrontCameraPreviewImageData();
////            if (cameraPreviewImageData == null || !cameraPreviewImageData.hasImage()) {
////                errorCallback.onError("1", "BAD_CAMERA_PREVIEW_IMAGE_DATA");
////                return;
////            }
////
////            String base64 = yuvToBase64(
////                    cameraPreviewImageData.imageBytes(),
////                    cameraPreviewImageData.imageFormat(),
////                    cameraPreviewImageData.width(),
////                    cameraPreviewImageData.height()
////            );
////
////            imageTakenCallback.onImageTaken(base64);
////        } else {
////            long taskStartTimestamp = System.currentTimeMillis();
////
////            CameraFrameChangedTask takeFrontCameraImageTask = previewFrame -> {
////                if (System.currentTimeMillis() < taskStartTimestamp + 1000) {
////                    return false;
////                }
////
////                if (previewFrame == null || !previewFrame.hasImage()) {
////                    errorCallback.onError("1", "BAD_CAMERA_PREVIEW_IMAGE_DATA");
////                    return true;
////                }
////
////                String base64 = yuvToBase64(
////                        previewFrame.imageBytes(),
////                        previewFrame.imageFormat(),
////                        previewFrame.width(),
////                        previewFrame.height()
////                );
////
////                imageTakenCallback.onImageTaken(base64);
////
////                stopFrontCameraPreview();
////
////                return true;
////            };
////
////            String taskId = String.valueOf(System.currentTimeMillis()) + UUID.randomUUID().toString();
////            mFrontCameraFrameChangeTasks.put(taskId, takeFrontCameraImageTask);
////
////            startFrontCameraPreview(quality);
////        }
//    }
//}


// =================================================================================
// =================================================================================
// =================================================================================
// =================================================================================


//package com.vision.services.camera;
//
//
//import android.graphics.ImageFormat;
//import android.graphics.Rect;
//import android.graphics.SurfaceTexture;
//import android.graphics.YuvImage;
//import android.hardware.Camera;
//import android.os.Build;
//import android.util.Base64;
//import android.util.Log;
//
//import com.vision.common.constants.AppConstants;
//import com.vision.services.camera.data.callbacks.OnImageTakeError;
//import com.vision.services.camera.data.callbacks.OnImageTaken;
//import com.vision.services.camera.data.camera_frame_changed_task.CameraFrameChangedTask;
//import com.vision.services.camera.data.camera_preview_image_data.CameraPreviewImageData;
//import com.vision.services.camera.data.opencv.OpenCVHelper;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class CameraService_V4 {
//    public static final String NAME = "CameraService_V4";
//
//    private static CameraService_V4 sInstance;
//
//    private Camera mFrontCamera;
//    private SurfaceTexture mFrontCameraSurfaceTexture;
//    private byte[] mFrontCameraBuffer;
//    private int mFrontCameraPreviewFormat = ImageFormat.NV21;
//    private boolean mFrontCameraRunning = false;
//    private CameraPreviewImageData mFrontCameraPreviewImageData;
//    private Map<String, CameraFrameChangedTask> mFrontCameraFrameChangeTasks;
//    private boolean mFrontCameraRecognizePersonRunning;
//
//    private Camera mBackCamera;
//    private SurfaceTexture mBackCameraSurfaceTexture;
//    private byte[] mBackCameraBuffer;
//    private int mBackCameraPreviewFormat = ImageFormat.NV21;
//    private boolean mBackCameraRunning = false;
//    private CameraPreviewImageData mBackCameraPreviewImageData;
//    private Map<String, CameraFrameChangedTask> mBackCameraFrameChangeTasks;
//    private boolean mBackCameraRecognizePersonRunning;
//
//    private CameraService_V4() {
//        mFrontCameraFrameChangeTasks = new ConcurrentHashMap<>();
//        mBackCameraFrameChangeTasks = new ConcurrentHashMap<>();
//    }
//
//    public static CameraService_V4 get() {
//        if (sInstance == null) {
//            sInstance = new CameraService_V4();
//        }
//
//        return sInstance;
//    }
//
//    public boolean hasBackCamera() {
//        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
//        int numberOfCameras = Camera.getNumberOfCameras();
//        for (int i = 0; i < numberOfCameras; i++) {
//            Camera.getCameraInfo(i, cameraInfo);
//            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public boolean hasFrontCamera() {
//        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
//        int numberOfCameras = Camera.getNumberOfCameras();
//        for (int i = 0; i < numberOfCameras; i++) {
//            Camera.getCameraInfo(i, cameraInfo);
//            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public boolean canRecognizePerson() {
//        return OpenCVHelper.available();
//    }
//
//    public boolean isFrontCameraRecognizePersonRunning() {
//        return mFrontCameraRecognizePersonRunning;
//    }
//
//    public boolean isBackCameraRecognizePersonRunning() {
//        return mBackCameraRecognizePersonRunning;
//    }
//
//    public void takeBackCameraImage(String quality,
//                                    OnImageTaken imageTakenCallback,
//                                    OnImageTakeError errorCallback) {
//        if (mBackCameraRunning) {
//            CameraPreviewImageData cameraPreviewImageData = getBackCameraPreviewImageData();
//            if (cameraPreviewImageData == null || !cameraPreviewImageData.hasImage()) {
//                errorCallback.onError("1", "BAD_CAMERA_PREVIEW_IMAGE_DATA");
//                return;
//            }
//
//            String base64 = yuvToBase64(
//                    cameraPreviewImageData.imageBytes(),
//                    cameraPreviewImageData.imageFormat(),
//                    cameraPreviewImageData.width(),
//                    cameraPreviewImageData.height()
//            );
//
//            imageTakenCallback.onImageTaken(base64);
//        } else {
//            long taskStartTimestamp = System.currentTimeMillis();
//
//            CameraFrameChangedTask takeBackCameraImageTask = previewFrame -> {
//                if (System.currentTimeMillis() < taskStartTimestamp + 1000) {
//                    return false;
//                }
//
//                if (previewFrame == null || !previewFrame.hasImage()) {
//                    errorCallback.onError("1", "BAD_CAMERA_PREVIEW_IMAGE_DATA");
//                    return true;
//                }
//
//                String base64 = yuvToBase64(
//                        previewFrame.imageBytes(),
//                        previewFrame.imageFormat(),
//                        previewFrame.width(),
//                        previewFrame.height()
//                );
//
//                imageTakenCallback.onImageTaken(base64);
//
//                stopBackCameraPreview();
//
//                return true;
//            };
//
//            String taskId = String.valueOf(System.currentTimeMillis()) + UUID.randomUUID().toString();
//            mBackCameraFrameChangeTasks.put(taskId, takeBackCameraImageTask);
//
//            startBackCameraPreview(quality);
//        }
//    }
//
//    public void takeFrontCameraImage(String quality,
//                                     OnImageTaken imageTakenCallback,
//                                     OnImageTakeError errorCallback) {
//        if (mFrontCameraRunning) {
//            CameraPreviewImageData cameraPreviewImageData = getFrontCameraPreviewImageData();
//            if (cameraPreviewImageData == null || !cameraPreviewImageData.hasImage()) {
//                errorCallback.onError("1", "BAD_CAMERA_PREVIEW_IMAGE_DATA");
//                return;
//            }
//
//            String base64 = yuvToBase64(
//                    cameraPreviewImageData.imageBytes(),
//                    cameraPreviewImageData.imageFormat(),
//                    cameraPreviewImageData.width(),
//                    cameraPreviewImageData.height()
//            );
//
//            imageTakenCallback.onImageTaken(base64);
//        } else {
//            long taskStartTimestamp = System.currentTimeMillis();
//
//            CameraFrameChangedTask takeFrontCameraImageTask = previewFrame -> {
//                if (System.currentTimeMillis() < taskStartTimestamp + 1000) {
//                    return false;
//                }
//
//                if (previewFrame == null || !previewFrame.hasImage()) {
//                    errorCallback.onError("1", "BAD_CAMERA_PREVIEW_IMAGE_DATA");
//                    return true;
//                }
//
//                String base64 = yuvToBase64(
//                        previewFrame.imageBytes(),
//                        previewFrame.imageFormat(),
//                        previewFrame.width(),
//                        previewFrame.height()
//                );
//
//                imageTakenCallback.onImageTaken(base64);
//
//                stopFrontCameraPreview();
//
//                return true;
//            };
//
//            String taskId = String.valueOf(System.currentTimeMillis()) + UUID.randomUUID().toString();
//            mFrontCameraFrameChangeTasks.put(taskId, takeFrontCameraImageTask);
//
//            startFrontCameraPreview(quality);
//        }
//    }
//
//    private void startFrontCameraPreview(String quality) {
//        if (mFrontCameraRunning) {
//            return;
//        }
//
//        int frontCameraId = getFrontCameraId();
//        if (frontCameraId < 0) {
//            Log.d("tag", "CameraService_V4->startFrontCameraPreview(): BAD_FRONT_CAMERA_ID");
//            return;
//        }
//
//        mFrontCamera = Camera.open(frontCameraId);
//        if (mFrontCamera == null) {
//            Log.d("tag", "CameraService_V4->startFrontCameraPreview(): BAD_CURRENT_CAMERA");
//            return;
//        }
//
//        Camera.Parameters parameters = mFrontCamera.getParameters();
//
//        /* Image format NV21 causes issues in the Android emulators */
//        if (Build.FINGERPRINT.startsWith("generic")
//                || Build.FINGERPRINT.startsWith("unknown")
//                || Build.MODEL.contains("google_sdk")
//                || Build.MODEL.contains("Emulator")
//                || Build.MODEL.contains("Android SDK built for x86")
//                || Build.MANUFACTURER.contains("Genymotion")
//                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
//                || "google_sdk".equals(Build.PRODUCT)) {
//            parameters.setPreviewFormat(ImageFormat.YV12);  // "generic" or "android" = android emulator
//        } else {
//            parameters.setPreviewFormat(ImageFormat.NV21);
//        }
//        mFrontCameraPreviewFormat = parameters.getPreviewFormat();
//
//        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
//        Collections.sort(previewSizes, (a, b) -> (b.height * b.width) - (a.height * a.width));
//        Camera.Size cameraPreviewSize = previewSizes.get(0);
//        if (quality.equalsIgnoreCase(AppConstants.CAMERA_IMAGE_QUALITY_HIGH)) {
//            cameraPreviewSize = previewSizes.get(0);
//            Log.d("tag", "CameraService_V4->startFrontCameraPreview()->HIGH_IMAGE_QUALITY: " + cameraPreviewSize.width + " - " + cameraPreviewSize.height);
//        } else if (quality.equalsIgnoreCase(AppConstants.CAMERA_IMAGE_QUALITY_MEDIUM)) {
//            cameraPreviewSize = previewSizes.get(previewSizes.size() / 2);
//            Log.d("tag", "CameraService_V4->startFrontCameraPreview()->MEDIUM_IMAGE_QUALITY: " + cameraPreviewSize.width + " - " + cameraPreviewSize.height);
//        } else if (quality.equalsIgnoreCase(AppConstants.CAMERA_IMAGE_QUALITY_LOW)) {
//            cameraPreviewSize = previewSizes.get(previewSizes.size() - 1);
//            Log.d("tag", "CameraService_V4->startFrontCameraPreview()->LOW_IMAGE_QUALITY: " + cameraPreviewSize.width + " - " + cameraPreviewSize.height);
//        }
//        parameters.setPreviewSize(cameraPreviewSize.width, cameraPreviewSize.height);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && !android.os.Build.MODEL.equals("GT-I9100")) {
//            parameters.setRecordingHint(true);
//        }
//
//        List<String> FocusModes = parameters.getSupportedFocusModes();
//        if (FocusModes != null && FocusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
//            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
//        }
//
//        try {
//            mFrontCamera.setParameters(parameters);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        int width = parameters.getPreviewSize().width;
//        int height = parameters.getPreviewSize().height;
//
//        int size = width * height;
//        size  = size * ImageFormat.getBitsPerPixel(parameters.getPreviewFormat()) / 8;
//        mFrontCameraBuffer = new byte[size];
//
//        mFrontCamera.addCallbackBuffer(mFrontCameraBuffer);
//        mFrontCamera.setPreviewCallbackWithBuffer((bytes, camera) -> {
////            setFrontCameraPreviewImageData(bytes, width, height, mFrontCameraPreviewFormat);
//            onFrontCameraPreviewFrameChanged(bytes, width, height, mFrontCameraPreviewFormat);
//
//            if (mFrontCamera != null) {
//                mFrontCamera.addCallbackBuffer(mFrontCameraBuffer);
//            }
//        });
//
//
//        mFrontCameraSurfaceTexture = new SurfaceTexture(1);
//        try {
//            mFrontCamera.setPreviewTexture(mFrontCameraSurfaceTexture);
//            mFrontCamera.startPreview();
//
//            mFrontCameraRunning = true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void startBackCameraPreview(String quality) {
//        if (mBackCameraRunning) {
//            return;
//        }
//
//        int backCameraId = getBackCameraId();
//        if (backCameraId < 0) {
//            Log.d("tag", "CameraService_V4->startBackCameraPreview(): BAD_BACK_CAMERA_ID");
//            return;
//        }
//
//        mBackCamera = Camera.open(backCameraId);
//        if (mBackCamera == null) {
//            Log.d("tag", "CameraService_V4->startBackCameraPreview(): BAD_CURRENT_CAMERA");
//            return;
//        }
//
//        Camera.Parameters parameters = mBackCamera.getParameters();
//
//        /* Image format NV21 causes issues in the Android emulators */
//        if (Build.FINGERPRINT.startsWith("generic")
//                || Build.FINGERPRINT.startsWith("unknown")
//                || Build.MODEL.contains("google_sdk")
//                || Build.MODEL.contains("Emulator")
//                || Build.MODEL.contains("Android SDK built for x86")
//                || Build.MANUFACTURER.contains("Genymotion")
//                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
//                || "google_sdk".equals(Build.PRODUCT)) {
//            parameters.setPreviewFormat(ImageFormat.YV12);  // "generic" or "android" = android emulator
//        } else {
//            parameters.setPreviewFormat(ImageFormat.NV21);
//        }
//        mBackCameraPreviewFormat = parameters.getPreviewFormat();
//
//        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
//        Collections.sort(previewSizes, (a, b) -> (b.height * b.width) - (a.height * a.width));
//        Camera.Size cameraPreviewSize = previewSizes.get(0);
//        if (quality.equalsIgnoreCase(AppConstants.CAMERA_IMAGE_QUALITY_HIGH)) {
//            cameraPreviewSize = previewSizes.get(0);
//            Log.d("tag", "CameraService_V4->startBackCameraPreview()->HIGH_IMAGE_QUALITY: " + cameraPreviewSize.width + " - " + cameraPreviewSize.height);
//        } else if (quality.equalsIgnoreCase(AppConstants.CAMERA_IMAGE_QUALITY_MEDIUM)) {
//            cameraPreviewSize = previewSizes.get(previewSizes.size() / 2);
//            Log.d("tag", "CameraService_V4->startBackCameraPreview()->MEDIUM_IMAGE_QUALITY: " + cameraPreviewSize.width + " - " + cameraPreviewSize.height);
//        } else if (quality.equalsIgnoreCase(AppConstants.CAMERA_IMAGE_QUALITY_LOW)) {
//            cameraPreviewSize = previewSizes.get(previewSizes.size() - 1);
//            Log.d("tag", "CameraService_V4->startBackCameraPreview()->LOW_IMAGE_QUALITY: " + cameraPreviewSize.width + " - " + cameraPreviewSize.height);
//        }
//        parameters.setPreviewSize(cameraPreviewSize.width, cameraPreviewSize.height);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && !android.os.Build.MODEL.equals("GT-I9100")) {
//            parameters.setRecordingHint(true);
//        }
//
//        List<String> FocusModes = parameters.getSupportedFocusModes();
//        if (FocusModes != null && FocusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
//            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
//        }
//
//        try {
//            mBackCamera.setParameters(parameters);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        int width = parameters.getPreviewSize().width;
//        int height = parameters.getPreviewSize().height;
//
//        int size = width * height;
//        size  = size * ImageFormat.getBitsPerPixel(parameters.getPreviewFormat()) / 8;
//        mBackCameraBuffer = new byte[size];
//
//        mBackCamera.addCallbackBuffer(mBackCameraBuffer);
//        mBackCamera.setPreviewCallbackWithBuffer((bytes, camera) -> {
////            setBackCameraPreviewImageData(bytes, width, height, mBackCameraPreviewFormat);
//            onBackCameraPreviewFrameChanged(bytes, width, height, mBackCameraPreviewFormat);
//
//            if (mBackCamera != null) {
//                mBackCamera.addCallbackBuffer(mBackCameraBuffer);
//            }
//        });
//
//
//        mBackCameraSurfaceTexture = new SurfaceTexture(1);
//        try {
//            mBackCamera.setPreviewTexture(mBackCameraSurfaceTexture);
//            mBackCamera.startPreview();
//
//            mBackCameraRunning = true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void stopFrontCameraPreview() {
//        try {
//            if (mFrontCamera != null) {
//                mFrontCamera.stopPreview();
//                mFrontCamera.setPreviewCallback(null);
//                mFrontCamera.release();
//                mFrontCamera = null;
//
//                if (mFrontCameraSurfaceTexture != null) {
//                    mFrontCameraSurfaceTexture.release();
//                }
//            }
//            clearFrontCameraPreviewImageData();
//            clearFrontCameraFrameChangeTasks();
//
//            mFrontCameraRunning = false;
//        } catch (Exception e) {
//            Log.d("tag", "CameraService_V4->stopFrontCameraPreview()->ERROR");
//            e.printStackTrace();
//        }
//    }
//
//    private void stopBackCameraPreview() {
//        try {
//            if (mBackCamera != null) {
//                mBackCamera.stopPreview();
//                mBackCamera.setPreviewCallback(null);
//                mBackCamera.release();
//                mBackCamera = null;
//
//                if (mBackCameraSurfaceTexture != null) {
//                    mBackCameraSurfaceTexture.release();
//                }
//            }
//            clearBackCameraPreviewImageData();
//            clearBackCameraFrameChangeTasks();
//
//            mBackCameraRunning = false;
//        } catch (Exception e) {
//            Log.d("tag", "CameraService_V4->stopBackCameraPreview()->ERROR");
//            e.printStackTrace();
//        }
//    }
//
//    private int getBackCameraId() {
//        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
//        int camerasCount = Camera.getNumberOfCameras();
//        for (int cameraIdx = 0; cameraIdx < camerasCount; ++cameraIdx) {
//            Camera.getCameraInfo(cameraIdx, cameraInfo);
//            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
//                return cameraIdx;
//            }
//        }
//
//        return -1;
//    }
//
//    private int getFrontCameraId() {
//        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
//        int camerasCount = Camera.getNumberOfCameras();
//        for (int cameraIdx = 0; cameraIdx < camerasCount; ++cameraIdx) {
//            Camera.getCameraInfo(cameraIdx, cameraInfo);
//            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                return cameraIdx;
//            }
//        }
//
//        return -1;
//    }
//
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
//                                                            int imageWidth,
//                                                            int imageHeight,
//                                                            int imageFormat) {
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
//
//    private synchronized void clearFrontCameraFrameChangeTasks() {
//        mFrontCameraFrameChangeTasks.clear();
//    }
//
//    private synchronized void clearBackCameraFrameChangeTasks() {
//        mBackCameraFrameChangeTasks.clear();
//    }
//
//    private synchronized void onBackCameraPreviewFrameChanged(byte[] previewImageBytes,
//                                                              int imageWidth,
//                                                              int imageHeight,
//                                                              int imageFormat) {
//        setBackCameraPreviewImageData(previewImageBytes, imageWidth, imageHeight, imageFormat);
//
//        List<String> taskIdsToRemove = new ArrayList<>();
//        for (Map.Entry<String, CameraFrameChangedTask> taskEntry : mBackCameraFrameChangeTasks.entrySet()) {
//            String taskId = taskEntry.getKey();
//            CameraFrameChangedTask task = taskEntry.getValue();
//
//            boolean needRemoveTask = task.execute(getBackCameraPreviewImageData());
//            if (needRemoveTask) {
//                taskIdsToRemove.add(taskId);
//            }
//        }
//
//        for (int i = 0; i < taskIdsToRemove.size(); ++i) {
//            mBackCameraFrameChangeTasks.remove(taskIdsToRemove.get(i));
//        }
//    }
//
//    private synchronized void onFrontCameraPreviewFrameChanged(byte[] previewImageBytes,
//                                                               int imageWidth,
//                                                               int imageHeight,
//                                                               int imageFormat) {
//        setFrontCameraPreviewImageData(previewImageBytes, imageWidth, imageHeight, imageFormat);
//
//        List<String> taskIdsToRemove = new ArrayList<>();
//        for (Map.Entry<String, CameraFrameChangedTask> taskEntry : mFrontCameraFrameChangeTasks.entrySet()) {
//            String taskId = taskEntry.getKey();
//            CameraFrameChangedTask task = taskEntry.getValue();
//
//            boolean needRemoveTask = task.execute(getFrontCameraPreviewImageData());
//            if (needRemoveTask) {
//                taskIdsToRemove.add(taskId);
//            }
//        }
//
//        for (int i = 0; i < taskIdsToRemove.size(); ++i) {
//            mFrontCameraFrameChangeTasks.remove(taskIdsToRemove.get(i));
//        }
//    }
//
//    private String yuvToBase64(byte[] yuvBytes, int imageFormat, int width, int height) {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        YuvImage yuvImage = new YuvImage(yuvBytes, imageFormat, width, height, null);
//        yuvImage.compressToJpeg(new Rect(0, 0, width, height), 50, out);
//        byte[] imageBytes = out.toByteArray();
//
//        //        Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//        //        iv.setImageBitmap(image);
//
//        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
//    }
//}

package com.vision.services.camera;


import android.content.Context;
import android.util.Log;

import com.vision.services.camera.camera_manager.CameraManager_V2;
import com.vision.services.camera.camera_manager.tasks.recognize_person_with_back_camera.RecognizePersonWithBackCameraCameraManagerTask;
import com.vision.services.camera.camera_manager.tasks.recognize_person_with_front_camera.RecognizePersonWithFrontCameraCameraManagerTask;
import com.vision.services.camera.camera_manager.tasks.take_back_camera_image.TakeBackCameraImageCameraManagerTask;
import com.vision.services.camera.camera_manager.tasks.take_front_camera_image.TakeFrontCameraImageCameraManagerTask;
import com.vision.services.camera.data.callbacks.OnImageTakeError;
import com.vision.services.camera.data.callbacks.OnImageTaken;

public class CameraService_V4 {
    public static final String NAME = "CameraService_V4";

    private static CameraService_V4 sInstance;

    private CameraManager_V2 mCameraManager;

    private CameraService_V4() {
        mCameraManager = new CameraManager_V2();
    }

    public static CameraService_V4 get() {
        if (sInstance == null) {
            sInstance = new CameraService_V4();
        }

        return sInstance;
    }

    public boolean hasBackCamera() {
        return mCameraManager.hasBackCamera();
    }

    public boolean hasFrontCamera() {
        return mCameraManager.hasFrontCamera();
    }

    public boolean canRecognizePerson() {
        return mCameraManager.canRecognizePerson();
    }

    public boolean isFrontCameraRecognizePersonRunning() {
        return mCameraManager.isFrontCameraRecognizePersonRunning();
    }

    public boolean isBackCameraRecognizePersonRunning() {
        return mCameraManager.isBackCameraRecognizePersonRunning();
    }

    public void stop(Context context) {
        mCameraManager.stopAllTasks();
    }

    public void takeBackCameraImage(String quality,
                                    OnImageTaken imageTakenCallback,
                                    OnImageTakeError errorCallback) {
        mCameraManager.executeTask(
                new TakeBackCameraImageCameraManagerTask(
                        quality,
                        imageTakenCallback,
                        errorCallback
                )
        );
    }

    public void takeFrontCameraImage(String quality,
                                     OnImageTaken imageTakenCallback,
                                     OnImageTakeError errorCallback) {
        mCameraManager.executeTask(
                new TakeFrontCameraImageCameraManagerTask(
                        quality,
                        imageTakenCallback,
                        errorCallback
                )
        );
    }

    public void startRecognizePersonWithFrontCamera(int imageRotationDeg) {
        Log.d("tag", "CameraService_V4->startRecognizePersonWithFrontCamera()");

        mCameraManager.executeTask(
                new RecognizePersonWithFrontCameraCameraManagerTask(imageRotationDeg)
        );
    }

    public void startRecognizePersonWithBackCamera(int imageRotationDeg) {
        Log.d("tag", "CameraService_V4->startRecognizePersonWithBackCamera()");

        mCameraManager.executeTask(
                new RecognizePersonWithBackCameraCameraManagerTask(imageRotationDeg)
        );
    }

    public void stopRecognizePersonWithFrontCamera() {
        mCameraManager.stopTaskOfType(CameraManager_V2.RECOGNIZE_PERSON_WITH_FRONT_CAMERA);
    }

    public void stopRecognizePersonWithBackCamera() {
        mCameraManager.stopTaskOfType(CameraManager_V2.RECOGNIZE_PERSON_WITH_BACK_CAMERA);
    }
}
