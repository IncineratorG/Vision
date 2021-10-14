package com.vision.common.services.camera;


import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Base64;
import android.util.Log;

import com.vision.common.constants.AppConstants;
import com.vision.common.services.camera.callbacks.OnImageTakeError;
import com.vision.common.services.camera.callbacks.OnImageTaken;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CameraService_V2 {
    public static final String NAME = "CameraService";

    public static CameraService_V2 sInstance;

    private Camera mCurrentCamera;
    private SurfaceTexture mCurrentSurfaceTexture;

    private CameraService_V2() {

    }

    public static CameraService_V2 get() {
        if (sInstance == null) {
            sInstance = new CameraService_V2();
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
        disposeCurrentCamera();
        initCurrentCamera(cameraId, quality);

        mCurrentCamera.takePicture(null, null, (bytes, camera) -> {
            Log.d("tag", "CameraService_V2->takeCameraImage()->IN_JPEG_PICTURE_CALLBACK: " + bytes.length);

            Camera.Parameters params = camera.getParameters();
            Camera.Size size = params.getPictureSize();

            Log.d("tag", "CameraService_V2->takeCameraImage()->IN_JPEG_PICTURE_CALLBACK->CURRENT_PICTURE_SIZE: " + size.width + " - " + size.height);

            String base64 = Base64.encodeToString(bytes, Base64.DEFAULT);
            imageTakenCallback.onImageTaken(base64);

            disposeCurrentCamera();
        });
    }

    private void disposeCurrentCamera() {
        Log.d("tag", "CameraService_V2->dispose()");

        try {
            if (mCurrentCamera != null) {
                mCurrentCamera.stopPreview();
                mCurrentCamera.release();
                mCurrentCamera = null;

                mCurrentSurfaceTexture.release();
            }
        } catch (Exception e) {
            Log.d("tag", "CameraService_V2->dispose()->ERROR");
            e.printStackTrace();
        }
    }

    private void initCurrentCamera(int cameraId, String quality) {
        mCurrentCamera = getCamera(cameraId);
        setCurrentCameraParameters(quality);
        initCurrentCameraPreview();
    }

    private Camera getCamera(int cameraId) {
        return Camera.open(cameraId);
    }

    private void setCurrentCameraParameters(String imageQuality) {
//        Camera.CameraInfo info = new Camera.CameraInfo();
//        Camera.getCameraInfo(id, info);
//        if (info.canDisableShutterSound) {
//            mCamera.enableShutterSound(false);
//        }

        Camera.Parameters parameters = mCurrentCamera.getParameters();

        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
        Collections.sort(previewSizes, (a, b) -> (b.height * b.width) - (a.height * a.width));
        for (int i = 0; i < previewSizes.size(); ++i) {
            Camera.Size size = previewSizes.get(i);
            Log.d("tag", "CameraService_V2->setCameraParameters()->SUPPORTED_PREVIEW_SIZE: " + size.width + " - " + size.height);
        }

        List<Camera.Size> pictureSizes = parameters.getSupportedPreviewSizes();
        Collections.sort(pictureSizes, (a, b) -> (b.height * b.width) - (a.height * a.width));
        for (int i = 0; i < pictureSizes.size(); ++i) {
            Camera.Size size = pictureSizes.get(i);;
            Log.d("tag", "CameraService_V2->setCameraParameters()->SUPPORTED_PICTURE_SIZE: " + size.width + " - " + size.height);
        }

        Camera.Size cameraPreviewSize = null;
        Camera.Size cameraPictureSize = null;
        if (imageQuality.equalsIgnoreCase(AppConstants.CAMERA_IMAGE_QUALITY_HIGH)) {
            cameraPreviewSize = previewSizes.get(0);
            cameraPictureSize = pictureSizes.get(0);
        } else if (imageQuality.equalsIgnoreCase(AppConstants.CAMERA_IMAGE_QUALITY_MEDIUM)) {
            cameraPreviewSize = previewSizes.get(previewSizes.size() / 2);
            cameraPictureSize = pictureSizes.get(pictureSizes.size() / 2);
        } else {
            cameraPreviewSize = previewSizes.get(previewSizes.size() - 1);
            cameraPictureSize = pictureSizes.get(pictureSizes.size() - 1);
        }
        Log.d("tag", "CameraService_V2->setCameraParameters()->SELECTED_PICTURE_SIZE: " + cameraPictureSize.width + " - " + cameraPictureSize.height);
        Log.d("tag", "CameraService_V2->setCameraParameters()->SELECTED_PREVIEW_SIZE: " + cameraPreviewSize.width + " - " + cameraPreviewSize.height);

        parameters.setPictureSize(cameraPictureSize.width, cameraPictureSize.height);

        try {
            mCurrentCamera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCurrentCameraPreview() {
        mCurrentSurfaceTexture = new SurfaceTexture(1);
        try {
            mCurrentCamera.setPreviewTexture(mCurrentSurfaceTexture);
            mCurrentCamera.setPreviewTexture(mCurrentSurfaceTexture);
            mCurrentCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
