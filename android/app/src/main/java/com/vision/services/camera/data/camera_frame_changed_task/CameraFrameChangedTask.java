package com.vision.services.camera.data.camera_frame_changed_task;


import com.vision.services.camera.data.camera_preview_image_data.CameraPreviewImageData;

public interface CameraFrameChangedTask {
//    String getType();
    boolean execute(CameraPreviewImageData previewFrame);
}