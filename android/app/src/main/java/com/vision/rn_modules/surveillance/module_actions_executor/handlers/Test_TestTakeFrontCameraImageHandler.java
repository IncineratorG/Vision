package com.vision.rn_modules.surveillance.module_actions_executor.handlers;


import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.Base64;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.vision.common.constants.AppConstants;
import com.vision.services.camera.CameraService_V4;
import com.vision.rn_modules.modules_common.interfaces.js_action_handler.JSActionHandler;

import java.io.ByteArrayOutputStream;

public class Test_TestTakeFrontCameraImageHandler implements JSActionHandler {
    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "Test_TestTakeFrontCameraImageHandler->handle()");

        CameraService_V4 cameraService = CameraService_V4.get();

        cameraService.takeFrontCameraImage(
                AppConstants.CAMERA_IMAGE_QUALITY_HIGH,
                (base64String) -> {
                    result.resolve(imageTakenEventPayload(base64String));
                },
                (code, message) -> {
                    Log.d("tag", "Test_TestTakeFrontCameraImageHandler->handle()->ERROR: " + code + " - " + message);
                    result.reject(code, message);
                }
        );

//        CameraService_V4 cameraService = CameraService_V4.get();

//        CameraPreviewImageData cameraPreviewImageData = cameraService.getFrontCameraPreviewImageData();
//        if (cameraPreviewImageData == null || !cameraPreviewImageData.hasImage()) {
//            result.resolve(null);
//            return;
//        }
//
//        String base64 = yuvToBase64(
//                cameraPreviewImageData.imageBytes(),
//                cameraPreviewImageData.imageFormat(),
//                cameraPreviewImageData.width(),
//                cameraPreviewImageData.height()
//        );
//
//        result.resolve(imageTakenEventPayload(base64));
    }

    private String yuvToBase64(byte[] yuvBytes, int imageFormat, int width, int height) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        YuvImage yuvImage = new YuvImage(yuvBytes, imageFormat, width, height, null);
        yuvImage.compressToJpeg(new Rect(0, 0, width, height), 50, out);
        byte[] imageBytes = out.toByteArray();

        //        Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        //        iv.setImageBitmap(image);

        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private WritableMap imageTakenEventPayload(String base64String) {
        WritableMap jsPayload = new WritableNativeMap();
        jsPayload.putString("base64String", base64String);
        return jsPayload;
    }
}
