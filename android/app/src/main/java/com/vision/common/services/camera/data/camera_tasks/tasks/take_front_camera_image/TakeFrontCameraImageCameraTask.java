package com.vision.common.services.camera.data.camera_tasks.tasks.take_front_camera_image;


import com.vision.common.data.service_error.ServiceError;
import com.vision.common.services.camera.data.callbacks.OnImageTakeError;
import com.vision.common.services.camera.data.callbacks.OnImageTaken;
import com.vision.common.services.camera.data.camera_frame_changed_task.CameraFrameChangedTask;
import com.vision.common.services.camera.data.camera_preview_image_data.CameraPreviewImageData;
import com.vision.common.services.camera.data.camera_tasks.types.CameraTaskTypes;
import com.vision.common.services.camera.data.helpers.CameraHelper;
import com.vision.common.services.camera.data.service_errors.CameraServiceErrors;

public class TakeFrontCameraImageCameraTask implements CameraFrameChangedTask {
    private String mQuality;
    private long mStartTaskTimestamp = -1;
    private OnImageTaken mImageTakenCallback;
    private OnImageTakeError mErrorCallback;

    public TakeFrontCameraImageCameraTask(String quality,
                                          long startTaskTimestamp,
                                          OnImageTaken imageTakenCallback,
                                          OnImageTakeError errorCallback) {
        mQuality = quality;
        mStartTaskTimestamp = startTaskTimestamp;
        mImageTakenCallback = imageTakenCallback;
        mErrorCallback = errorCallback;
    }

    public String quality() {
        return mQuality;
    }

    @Override
    public String getType() {
        return CameraTaskTypes.TAKE_FRONT_CAMERA_IMAGE;
    }

    @Override
    public boolean execute(CameraPreviewImageData previewFrame) {
        if (System.currentTimeMillis() < mStartTaskTimestamp + 1000) {
            return false;
        }

        if (previewFrame == null || !previewFrame.hasImage()) {
            ServiceError error = CameraServiceErrors.badCameraPreviewImageData();
            mErrorCallback.onError(error.code(), error.message());
            return true;
        }

        String base64 = CameraHelper.yuvToBase64(
                previewFrame.imageBytes(),
                previewFrame.imageFormat(),
                previewFrame.width(),
                previewFrame.height()
        );

        mImageTakenCallback.onImageTaken(base64);

        return true;
    }
}
