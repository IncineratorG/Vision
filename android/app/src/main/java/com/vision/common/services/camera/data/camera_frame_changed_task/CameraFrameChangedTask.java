package com.vision.common.services.camera.data.camera_frame_changed_task;


import com.vision.common.services.camera.data.camera_preview_image_data.CameraPreviewImageData;

public interface CameraFrameChangedTask {
    boolean execute(CameraPreviewImageData previewFrame);
}
