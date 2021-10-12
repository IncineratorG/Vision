package com.vision.modules.surveillance.module_actions_executor.handlers;


import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.Base64;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.vision.common.services.camera_motion_detection.CameraMotionDetectionService;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;

import java.io.ByteArrayOutputStream;

public class Test_TestCameraMotionDetectionHandler implements JSActionHandler {
    private Thread mThread;
    private boolean mStopThread;

    private class CameraWorker implements Runnable {
        private ReactApplicationContext mContext;

        public CameraWorker(ReactApplicationContext context) {
            mContext = context;
        }

        private WritableMap imageTakenEventPayload(String base64String) {
            WritableMap jsPayload = new WritableNativeMap();
            jsPayload.putString("base64String", base64String);
            return jsPayload;
        }

        @Override
        public void run() {
            do {
                Log.d("tag", "THREAD_RUNNING: " + System.currentTimeMillis());

                CameraMotionDetectionService cameraMotionDetectionService = CameraMotionDetectionService.get();

//                CameraMotionDetectionService.RequestImageCallback requestImageCallback = imageBytes -> {
//                    Log.d("tag", "CameraWorker->run()->requestImageCallback: " + imageBytes.length);
//
//                    String base64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//
//                    mContext
//                            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//                            .emit(
//                                    "IMAGE_TAKEN_EVENT",
//                                    imageTakenEventPayload(base64)
//                            );
//                };
//
//                if (cameraMotionDetectionService.isRunning()) {
//                    cameraMotionDetectionService.requestImage(requestImageCallback);
//
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
            } while (!mStopThread);
            Log.d("tag", "Finish processing thread");
        }
    }

    private WritableMap imageTakenEventPayload(String base64String) {
        WritableMap jsPayload = new WritableNativeMap();
        jsPayload.putString("base64String", base64String);
        return jsPayload;
    }

    public static byte[] NV21toJPEG(byte[] nv21, int width, int height) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        YuvImage yuv = new YuvImage(nv21, ImageFormat.NV21, width, height, null);
        yuv.compressToJpeg(new Rect(0, 0, width, height), 100, out);
        return out.toByteArray();
    }

    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "Test_TestCameraMotionDetectionHandler->handle()");

        CameraMotionDetectionService cameraMotionDetectionService = CameraMotionDetectionService.get();
        cameraMotionDetectionService.test();

        CameraMotionDetectionService.RequestImageCallback requestImageCallback = (imageBytes, width, height, previewFormat) -> {
            Log.d("tag", "CameraWorker->run()->requestImageCallback: " + imageBytes.length + " - " + width + " - " + height + " - " + previewFormat);

            byte[] jpegImageBytes = NV21toJPEG(imageBytes, width, height);
            String base64 = Base64.encodeToString(jpegImageBytes, Base64.DEFAULT);

            context
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(
                            "IMAGE_TAKEN_EVENT",
                            imageTakenEventPayload(base64)
                    );

//            byte[] jpegImage = NV21toJPEG(imageBytes, width, height);
//            Log.d("tag", "JPEG_IMAGE_SIZE: " + jpegImage.length);

//            String base64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//
//            context
//                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//                    .emit(
//                            "IMAGE_TAKEN_EVENT",
//                            imageTakenEventPayload(base64)
//                    );
        };

        if (cameraMotionDetectionService.isRunning()) {
            cameraMotionDetectionService.requestImage(requestImageCallback);
        }

        // ===
        // =====
//        if (mThread == null && cameraMotionDetectionService.isRunning()) {
//            Log.d("tag", "WILL_START_THREAD");
//
//            mStopThread = false;
//            mThread = new Thread(new CameraWorker(context));
//            mThread.start();
//        } else {
//            Log.d("tag", "WILL_STOP_THREAD");
//
//            try {
//                mStopThread = true;
//                if (mThread != null) {
//                    mThread.join();
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                mThread =  null;
//            }
//        }
        // =====
        // ===

//        mStopThread = false;
//        mThread = new Thread(new CameraWorker());
//        mThread.start();

//        CameraMotionDetectionService cameraMotionDetectionService = CameraMotionDetectionService.get();
//
//        CameraMotionDetectionService.RequestImageCallback requestImageCallback = imageBytes -> {
//            Log.d("tag", "Test_TestCameraMotionDetectionHandler->handle()->requestImageCallback");
//        };
//
//        if (cameraMotionDetectionService.isRunning()) {
//            cameraMotionDetectionService.requestImage(requestImageCallback);
//        }

        result.resolve(true);
    }
}
