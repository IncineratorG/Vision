package com.vision.services.surveillance.requests.handlers.take_back_camera_image;


import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;

        Log.d("tag", "==> 1");

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("tag", "==> 2");

        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d("tag", "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("tag", "==> 3");

        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        Log.d("tag", "==> 4");

        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
            // preview surface does not exist
            Log.d("tag", "==> 5");

            return;
        }

        // stop preview before making changes
        try {
            Log.d("tag", "==> 6");
            mCamera.stopPreview();
        } catch (Exception e){
            Log.d("tag", "==> 7");
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            Log.d("tag", "==> 8");
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d("tag", "Error starting camera preview: " + e.getMessage());
        }
    }
}


//package com.vision.services.surveillance.requests.request_handlers.take_back_camera_image;
//
//
//import android.content.Context;
//import android.hardware.Camera;
//import android.util.Log;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//
//import java.io.IOException;
//
//public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
//    private SurfaceHolder mHolder;
//    private Camera mCamera;
//
//    public CameraPreview(Context context, Camera camera) {
//        super(context);
//        mCamera = camera;
//
//        // Install a SurfaceHolder.Callback so we get notified when the
//        // underlying surface is created and destroyed.
//        mHolder = getHolder();
//        mHolder.addCallback(this);
//        // deprecated setting, but required on Android versions prior to 3.0
//        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//    }
//
//    public void surfaceCreated(SurfaceHolder holder) {
//        // The Surface has been created, now tell the camera where to draw the preview.
//        try {
//            mCamera.setPreviewDisplay(holder);
//            mCamera.startPreview();
//        } catch (IOException e) {
//            Log.d("tag", "Error setting camera preview: " + e.getMessage());
//        }
//    }
//
//    public void surfaceDestroyed(SurfaceHolder holder) {
//        // empty. Take care of releasing the Camera preview in your activity.
//    }
//
//    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
//        // If your preview can change or rotate, take care of those events here.
//        // Make sure to stop the preview before resizing or reformatting it.
//
//        if (mHolder.getSurface() == null){
//            // preview surface does not exist
//            return;
//        }
//
//        // stop preview before making changes
//        try {
//            mCamera.stopPreview();
//        } catch (Exception e){
//            // ignore: tried to stop a non-existent preview
//        }
//
//        // set preview size and make any resize, rotate or
//        // reformatting changes here
//
//        // start preview with new settings
//        try {
//            mCamera.setPreviewDisplay(mHolder);
//            mCamera.startPreview();
//
//        } catch (Exception e){
//            Log.d("tag", "Error starting camera preview: " + e.getMessage());
//        }
//    }
//}
