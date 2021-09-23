package com.vision.common.services.camera;


import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class CameraService {
    private static CameraService sInstance;

    private Camera mCamera;
    private SurfaceTexture mSurfaceTexture;

    Camera.PictureCallback jpegPictureCallback = (bytes, cam) -> {
        Log.d("tag", "==> IN_JPEG_PICTURE_CALLBACK: " + bytes.length);

//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Log.d("tag", "---> WILL_STOP_PREVIEW <---");
//
////                mCamera.stopPreview();
//            }
//        }, 2000);

//        mCamera.stopPreview();
    };
    Camera.PictureCallback rawPictureCallback = (bytes, cam) -> {
        Log.d("tag", "==> IN_RAW_PICTURE_CALLBACK");
    };
    Camera.ShutterCallback shutterCallback = () -> {
        Log.d("tag", "==> IN_SHUTTER_PICTURE_CALLBACK");
    };

    private CameraService() {

    }

    public static CameraService get() {
        if (sInstance == null) {
            sInstance = new CameraService();
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

        mSurfaceTexture = new SurfaceTexture(1);
        try {
            mCamera.setPreviewTexture(mSurfaceTexture);
            mCamera.startPreview();
            Log.d("tag", "===> CameraService->1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dispose(Context context) {
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

    public void takeBackCameraImage(Context context) {
        Log.d("tag", "CameraService->takeBackCameraImage()");

        init(context);
        mCamera.takePicture(null, null, jpegPictureCallback);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d("tag", "---> WILL_DISPOSE_PICTURE <---");

                dispose(context);
            }
        }, 1000);

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
