package com.vision.common.services.camera.data.camera_tasks.tasks;


import com.vision.common.services.camera.data.callbacks.OnImageTakeError;
import com.vision.common.services.camera.data.callbacks.OnImageTaken;
import com.vision.common.services.camera.data.camera_tasks.tasks.take_back_camera_image.TakeBackCameraImageCameraTask;
import com.vision.common.services.camera.data.camera_tasks.tasks.take_front_camera_image.TakeFrontCameraImageCameraTask;

public class CameraTasks {
    public static TakeFrontCameraImageCameraTask takeFrontCameraImageCameraTask(String quality,
                                                                                long startTaskTimestamp,
                                                                                OnImageTaken imageTakenCallback,
                                                                                OnImageTakeError errorCallback) {
        return new TakeFrontCameraImageCameraTask(quality, startTaskTimestamp, imageTakenCallback, errorCallback);
    }

    public static TakeBackCameraImageCameraTask takeBackCameraImageCameraTask(String quality,
                                                                              long startTaskTimestamp,
                                                                              OnImageTaken imageTakenCallback,
                                                                              OnImageTakeError errorCallback) {
        return new TakeBackCameraImageCameraTask(quality, startTaskTimestamp, imageTakenCallback, errorCallback);
    }
}
