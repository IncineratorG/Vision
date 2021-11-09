package com.vision.services.camera.data.camera_manager.tasks.take_front_camera_image;

import com.vision.common.data.service_error.ServiceError;
import com.vision.services.camera.data.camera_manager.CameraManager;
import com.vision.services.camera.data.callbacks.OnImageTakeError;
import com.vision.services.camera.data.callbacks.OnImageTaken;
import com.vision.services.camera.data.camera_preview_frame_data.CameraPreviewFrameData;
import com.vision.services.camera.data.helpers.CameraHelper;
import com.vision.services.camera.data.service_errors.CameraServiceErrors;

public class TakeFrontCameraImageCameraManagerTask implements CameraManager.CameraManagerTask {
    private String mType;
    private long mTaskInitializationTimestamp;
    private String mImageQuality;
    private OnImageTaken mImageTakenCallback;
    private OnImageTakeError mImageTakeErrorCallback;

    public TakeFrontCameraImageCameraManagerTask(String imageQuality,
                                                OnImageTaken imageTakenCallback,
                                                OnImageTakeError imageTakeErrorCallback) {
        mType = CameraManager.TAKE_FRONT_CAMERA_IMAGE;

        mTaskInitializationTimestamp = System.currentTimeMillis();
        mImageQuality = imageQuality;
        mImageTakenCallback = imageTakenCallback;
        mImageTakeErrorCallback = imageTakeErrorCallback;
    }

    @Override
    public String type() {
        return mType;
    }

    @Override
    public boolean onCameraPreviewImageData(CameraPreviewFrameData previewImageData) {
        if (System.currentTimeMillis() < mTaskInitializationTimestamp + 1000) {
            return false;
        }

        if (previewImageData == null || !previewImageData.hasImage()) {
            ServiceError error = CameraServiceErrors.badCameraPreviewImageData();
            mImageTakeErrorCallback.onError(error.code(), error.message());
            return true;
        }

        String base64 = CameraHelper.yuvToBase64(
                previewImageData.imageBytes(),
                previewImageData.imageFormat(),
                previewImageData.width(),
                previewImageData.height()
        );

        mImageTakenCallback.onImageTaken(base64);

        return true;
    }

    public String imageQuality() {
        return mImageQuality;
    }
}
