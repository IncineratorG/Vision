package com.vision.common.services.camera.data.service_errors;


import com.vision.common.data.service_error.ServiceError;

public class CameraServiceErrors {
    public static ServiceError badCameraPreviewImageData() {
        return new ServiceError("1", "BAD_CAMERA_PREVIEW_IMAGE_DATA");
    }
}
