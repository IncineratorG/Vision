package com.vision.common.services.surveillance.data.request_handlers.take_back_camera_image;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_request_handler.ServiceRequestHandler;
import com.vision.common.services.firebase.FBSService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.common.services.surveillance.data.service_requests.response_payloads.SurveillanceServiceResponsePayloads;
import com.vision.common.services.surveillance.data.service_requests.response_payloads.payloads.TakeBackCameraImageResponsePayload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class TakeBackCameraImageServiceHandler implements ServiceRequestHandler {
    @Override
    public void handle(Context context, ServiceRequest request) {
        Log.d("tag", "TakeBackCameraImageServiceHandler->handle(): " + request.stringify());

        SurveillanceService surveillanceService = SurveillanceService.get();

        String currentGroupName = surveillanceService.currentGroupName();
        String currentGroupPassword = surveillanceService.currentGroupPassword();
        String currentDeviceName = surveillanceService.currentDeviceName();

        String requestSenderDeviceName = request.senderDeviceName();

        // ===
        // =====
        test(context);
        // =====
        TakeBackCameraImageResponsePayload responsePayload =
                SurveillanceServiceResponsePayloads.takeBackCameraImageResponsePayload("image-data");

        ServiceResponse response = new ServiceResponse(request.id(), responsePayload.jsonObject());

        surveillanceService.sendResponse(
                currentGroupName,
                currentGroupPassword,
                requestSenderDeviceName,
                response
        );
        // ===

        List<String> requestsPath = FBSPathsService.get().requestsPath(currentGroupName, currentGroupPassword, currentDeviceName);
        if (request.key() != null) {
            FBSService.get().removeValueFromList(requestsPath, request.key());
        } else {
            Log.d("tag", "TakeBackCameraImageServiceHandler->handle()->BAD_REQUEST_KEY: " + request.stringify());
        }
    }

    Camera mCamera;
    boolean safeToCapture;

    Camera.PictureCallback mCall = (data, camera) -> {
        safeToCapture = false;
        //decode the data obtained by the camera into a Bitmap

        FileOutputStream outStream = null;
        try{

            // create a File object for the parent directory
            File myDirectory = new File(Environment.getExternalStorageDirectory()+"/Test");
            // have the object build the directory structure, if needed.
            myDirectory.mkdirs();

            //SDF for getting current time for unique image name
            SimpleDateFormat curTimeFormat = new SimpleDateFormat("ddMMyyyyhhmmss");
            String curTime = curTimeFormat.format(new java.util.Date());

            // create a File object for the output file
            outStream = new FileOutputStream(myDirectory+"/user"+curTime+".jpg");
            outStream.write(data);
            outStream.close();
            mCamera.release();
            mCamera = null;

            String strImagePath = Environment.getExternalStorageDirectory()+"/"+myDirectory.getName()+"/user"+curTime+".jpg";
            setEmailWithImage(strImagePath);
            Log.d("CAMERA", "picture clicked - "+strImagePath);
        } catch (FileNotFoundException e){
            Log.d("CAMERA", e.getMessage());
        } catch (IOException e){
            Log.d("CAMERA", e.getMessage());
        }

        safeToCapture = true;    //Set a boolean to true again after saving file.

    };

    private void test(Context context) {
        Log.d("tag", "TakeBackCameraImageServiceHandler->test()");

        mCamera = getAvailableFrontCamera();
        if (mCamera == null) {
            Log.d("tag", "TakeBackCameraImageServiceHandler->test(): CAMERA_IS_NULL");
            return;
        }

        SurfaceView sv = new SurfaceView(context);
        SurfaceTexture surfaceTexture = new SurfaceTexture(10);

        // ===
        try {
            mCamera.setPreviewTexture(surfaceTexture);
            //mCamera.setPreviewDisplay(sv.getHolder());
            Camera.Parameters parameters = mCamera.getParameters();

            //set camera parameters
            mCamera.setParameters(parameters);


            //This boolean is used as app crashes while writing images to file if simultaneous calls are made to takePicture
            if(safeToCapture) {
                mCamera.startPreview();
                mCamera.takePicture(null, null, mCall);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // ===

        //Get a surface
        SurfaceHolder sHolder = sv.getHolder();
        //tells Android that this surface will have its data constantly replaced
        sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    private Camera getAvailableFrontCamera (){
        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                try {
                    cam = Camera.open(camIdx);
                } catch (RuntimeException e) {
                    Log.e("CAMERA", "Camera failed to open: " + e.getLocalizedMessage());
                }
            }
        }

        return cam;
    }

    private void setEmailWithImage(String str) {
        Log.d("tag", "setEmailWithImage(): " + str);
    }
}
