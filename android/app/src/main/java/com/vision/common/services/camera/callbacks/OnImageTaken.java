package com.vision.common.services.camera.callbacks;


public interface OnImageTaken {
    void onImageTaken(byte[] bytes, String base64String);
}
