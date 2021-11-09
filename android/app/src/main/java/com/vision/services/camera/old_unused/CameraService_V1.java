package com.vision.services.camera.old_unused;


import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Base64;
import android.util.Log;

import com.vision.services.camera.data.callbacks.OnImageTakeError;
import com.vision.services.camera.data.callbacks.OnImageTaken;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CameraService_V1 {
    public static final String NAME = "CameraService";

    private static CameraService_V1 sInstance;

    private Camera mCamera;
    private SurfaceTexture mSurfaceTexture;
    private OnImageTaken mCurrentImageTakenCallback;
    private OnImageTakeError mCurrentImageTakeErrorCallback;

    Camera.PictureCallback jpegPictureCallback = (bytes, cam) -> {
        Log.d("tag", "==> IN_JPEG_PICTURE_CALLBACK: " + bytes.length);

        String base64 = Base64.encodeToString(bytes, Base64.DEFAULT);

        // ===
        if (mCurrentImageTakenCallback != null) {
            mCurrentImageTakenCallback.onImageTaken(base64);
        } else {
            Log.d("tag", "CameraService->BAD_CURRENT_IMAGE_TAKEN_CALLBACK");
        }
        // ===

//        String encodedImage = Base64.encodeToString(bytes, Base64.DEFAULT);
//        Log.d("tag", "ENCODED_IMAGE: " + encodedImage);
        dispose();
    };
    Camera.PictureCallback rawPictureCallback = (bytes, cam) -> {
        Log.d("tag", "==> IN_RAW_PICTURE_CALLBACK");
    };
    Camera.ShutterCallback shutterCallback = () -> {
        Log.d("tag", "==> IN_SHUTTER_PICTURE_CALLBACK");
    };

    private CameraService_V1() {

    }

    public static CameraService_V1 get() {
        if (sInstance == null) {
            sInstance = new CameraService_V1();
        }

        return sInstance;
    }

    public void init(Context context) {
        Log.d("tag", "CameraService->init()");

        int cameraCount = 0;
        mCamera = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                try {
                    mCamera = Camera.open(camIdx);
                    break;
                } catch (RuntimeException e) {
                    Log.e("CAMERA", "Camera failed to open: " + e.getLocalizedMessage());
                }
            }
        }

        // ===
        // =====
        Camera.Parameters parameters = mCamera.getParameters();

        // ===
        List<Camera.Size> previewSizes = mCamera.getParameters().getSupportedPreviewSizes();
        Collections.sort(previewSizes, (a, b) -> (b.height * b.width) - (a.height * a.width));

        for (int i = 0; i < previewSizes.size(); ++i) {
            Camera.Size size = previewSizes.get(i);

            Log.d("tag", "PREVIEW_SIZE: " + size.width + " - " + size.height);
        }
        Camera.Size cameraPreviewSize = previewSizes.get(previewSizes.size() - 1);
        // ===

        // ===
        List<Camera.Size> pictureSizes = mCamera.getParameters().getSupportedPreviewSizes();
        Collections.sort(pictureSizes, (a, b) -> (b.height * b.width) - (a.height * a.width));

        for (int i = 0; i < pictureSizes.size(); ++i) {
            Camera.Size size = pictureSizes.get(i);

            Log.d("tag", "PICTURE_SIZE: " + size.width + " - " + size.height);
        }
        Camera.Size cameraPictureSizes = pictureSizes.get(pictureSizes.size() - 1);
        // ===

        // ===
        List<String> cameraFocusModes = mCamera.getParameters().getSupportedFocusModes();
        for (int i = 0; i < cameraFocusModes.size(); ++i) {
            String focusMode = cameraFocusModes.get(i);

            Log.d("tag", "FOCUS_MODE: " + focusMode);
        }
        // ===

        // ===
        Log.d("tag", "SELECTED_PICTURE_SIZE: " + cameraPictureSizes.width + " - " + cameraPictureSizes.height);
        // ===

        parameters.setPreviewSize(cameraPreviewSize.width, cameraPreviewSize.height);
        parameters.setPictureSize(cameraPictureSizes.width, cameraPictureSizes.height);
//        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

        try {
            mCamera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // =====
        // ===

        mSurfaceTexture = new SurfaceTexture(1);
        try {
//            mCamera.setDisplayOrientation(90);

            mCamera.setPreviewTexture(mSurfaceTexture);
            mCamera.setPreviewTexture(mSurfaceTexture);
            mCamera.startPreview();
            Log.d("tag", "===> CameraService->1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dispose() {
        Log.d("tag", "CameraService->dispose()");

        try {
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;

                mSurfaceTexture.release();
            }
        } catch (Exception e) {
            Log.d("tag", "CameraService->dispose()->ERROR");
            e.printStackTrace();
        }
    }

    public void takeBackCameraImage(Context context,
                                    OnImageTaken imageTakenCallback,
                                    OnImageTakeError errorCallback) {
        Log.d("tag", "CameraService->takeBackCameraImage()");

        mCurrentImageTakenCallback = imageTakenCallback;
        mCurrentImageTakeErrorCallback = errorCallback;

        init(context);
        mCamera.takePicture(null, null, jpegPictureCallback);
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Log.d("tag", "---> WILL_DISPOSE_PICTURE <---");
//
//                dispose(context);
//            }
//        }, 1000);

//        mCamera.startPreview();
//        mCamera.takePicture(null, null, jpegPictureCallback);

//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Log.d("tag", "---> WILL_TAKE_PICTURE <---");
//
//                mCamera.takePicture(shutterCallback, rawPictureCallback, jpegPictureCallback);
//            }
//        }, 2000);

//        mCamera.startPreview();
//        mCamera.takePicture(shutterCallback, rawPictureCallback, jpegPictureCallback);
    }
}


//package com.vision.services.camera;
//
//
//import android.content.Context;
//import android.graphics.SurfaceTexture;
//import android.hardware.Camera;
//import android.media.ExifInterface;
//import android.util.Base64;
//import android.util.Log;
//import android.view.Surface;
//
//import com.vision.services.camera.data.callbacks.OnImageTakeError;
//import com.vision.services.camera.data.callbacks.OnImageTaken;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//public class CameraService {
//    private static CameraService sInstance;
//
//    private Camera mCamera;
//    private SurfaceTexture mSurfaceTexture;
//    private OnImageTaken mCurrentImageTakenCallback;
//    private OnImageTakeError mCurrentImageTakeErrorCallback;
//
//    Camera.PictureCallback jpegPictureCallback = (bytes, cam) -> {
//        Log.d("tag", "==> IN_JPEG_PICTURE_CALLBACK: " + bytes.length);
//
//        String base64 = Base64.encodeToString(bytes, Base64.DEFAULT);
//
//        // ===
//        if (mCurrentImageTakenCallback != null) {
//            mCurrentImageTakenCallback.onImageTaken(bytes, base64);
//        } else {
//            Log.d("tag", "CameraService->BAD_CURRENT_IMAGE_TAKEN_CALLBACK");
//        }
//        // ===
//
////        String encodedImage = Base64.encodeToString(bytes, Base64.DEFAULT);
////        Log.d("tag", "ENCODED_IMAGE: " + encodedImage);
//        dispose();
//    };
//    Camera.PictureCallback rawPictureCallback = (bytes, cam) -> {
//        Log.d("tag", "==> IN_RAW_PICTURE_CALLBACK");
//    };
//    Camera.ShutterCallback shutterCallback = () -> {
//        Log.d("tag", "==> IN_SHUTTER_PICTURE_CALLBACK");
//    };
//
//    private CameraService() {
//
//    }
//
//    public static CameraService get() {
//        if (sInstance == null) {
//            sInstance = new CameraService();
//        }
//
//        return sInstance;
//    }
//
//    public void init(Context context) {
//        Log.d("tag", "CameraService->init()");
//
//        int cameraCount = 0;
//        mCamera = null;
//        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
//        cameraCount = Camera.getNumberOfCameras();
//        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
//            Camera.getCameraInfo(camIdx, cameraInfo);
//            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
//                try {
//                    mCamera = Camera.open(camIdx);
//                    break;
//                } catch (RuntimeException e) {
//                    Log.e("CAMERA", "Camera failed to open: " + e.getLocalizedMessage());
//                }
//            }
//        }
//
//        // ===
//        // =====
//        Camera.Parameters parameters = mCamera.getParameters();
//
//        // ===
//        List<Camera.Size> previewSizes = mCamera.getParameters().getSupportedPreviewSizes();
//        Collections.sort(previewSizes, (a, b) -> (b.height * b.width) - (a.height * a.width));
//
//        for (int i = 0; i < previewSizes.size(); ++i) {
//            Camera.Size size = previewSizes.get(i);
//
//            Log.d("tag", "PREVIEW_SIZE: " + size.width + " - " + size.height);
//        }
//        Camera.Size cameraPreviewSize = previewSizes.get(previewSizes.size() - 1);
//        // ===
//
//        // ===
//        List<Camera.Size> pictureSizes = mCamera.getParameters().getSupportedPreviewSizes();
//        Collections.sort(pictureSizes, (a, b) -> (b.height * b.width) - (a.height * a.width));
//
//        for (int i = 0; i < pictureSizes.size(); ++i) {
//            Camera.Size size = pictureSizes.get(i);
//
//            Log.d("tag", "PICTURE_SIZE: " + size.width + " - " + size.height);
//        }
//        Camera.Size cameraPictureSizes = pictureSizes.get(pictureSizes.size() - 1);
//        // ===
//
//        // ===
//        List<String> cameraFocusModes = mCamera.getParameters().getSupportedFocusModes();
//        for (int i = 0; i < cameraFocusModes.size(); ++i) {
//            String focusMode = cameraFocusModes.get(i);
//
//            Log.d("tag", "FOCUS_MODE: " + focusMode);
//        }
//        // ===
//
//        // ===
//        Log.d("tag", "SELECTED_PICTURE_SIZE: " + cameraPictureSizes.width + " - " + cameraPictureSizes.height);
//        // ===
//
//        parameters.setPreviewSize(cameraPreviewSize.width, cameraPreviewSize.height);
//        parameters.setPictureSize(cameraPictureSizes.width, cameraPictureSizes.height);
////        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
//
//        try {
//            mCamera.setParameters(parameters);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // =====
//        // ===
//
//        mSurfaceTexture = new SurfaceTexture(1);
//        try {
////            mCamera.setDisplayOrientation(90);
//
//            mCamera.setPreviewTexture(mSurfaceTexture);
//            mCamera.setPreviewTexture(mSurfaceTexture);
//            mCamera.startPreview();
//            Log.d("tag", "===> CameraService->1");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void dispose() {
//        Log.d("tag", "CameraService->dispose()");
//
//        try {
//            if (mCamera != null) {
//                mCamera.stopPreview();
//                mCamera.release();
//                mCamera = null;
//
//                mSurfaceTexture.release();
//            }
//        } catch (Exception e) {
//            Log.d("tag", "CameraService->dispose()->ERROR");
//            e.printStackTrace();
//        }
//    }
//
//    public void takeBackCameraImage(Context context,
//                                    OnImageTaken imageTakenCallback,
//                                    OnImageTakeError errorCallback) {
//        Log.d("tag", "CameraService->takeBackCameraImage()");
//
//        mCurrentImageTakenCallback = imageTakenCallback;
//        mCurrentImageTakeErrorCallback = errorCallback;
//
//        init(context);
//        mCamera.takePicture(null, null, jpegPictureCallback);
////        Timer timer = new Timer();
////        timer.schedule(new TimerTask() {
////            @Override
////            public void run() {
////                Log.d("tag", "---> WILL_DISPOSE_PICTURE <---");
////
////                dispose(context);
////            }
////        }, 1000);
//
////        mCamera.startPreview();
////        mCamera.takePicture(null, null, jpegPictureCallback);
//
////        Timer timer = new Timer();
////        timer.schedule(new TimerTask() {
////            @Override
////            public void run() {
////                Log.d("tag", "---> WILL_TAKE_PICTURE <---");
////
////                mCamera.takePicture(shutterCallback, rawPictureCallback, jpegPictureCallback);
////            }
////        }, 2000);
//
////        mCamera.startPreview();
////        mCamera.takePicture(shutterCallback, rawPictureCallback, jpegPictureCallback);
//    }
//
//    public void takeBackCameraImage(Context context,
//                                    String imageQuality,
//                                    OnImageTaken imageTakenCallback,
//                                    OnImageTakeError errorCallback) {
//
//    }
//}
