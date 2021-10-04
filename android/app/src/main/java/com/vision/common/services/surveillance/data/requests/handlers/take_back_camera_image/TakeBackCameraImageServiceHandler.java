package com.vision.common.services.surveillance.data.requests.handlers.take_back_camera_image;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.WindowManager;

import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_request_handler.ServiceRequestHandler;
import com.vision.common.services.camera.CameraService_V2;
import com.vision.common.services.firebase.FBSService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.common.services.surveillance.data.requests.payloads.SurveillanceServiceRequestPayloads;
import com.vision.common.services.surveillance.data.requests.payloads.payloads.TakeBackCameraImageRequestPayload;
import com.vision.common.services.surveillance.data.responses.payloads.SurveillanceServiceResponsePayloads;
import com.vision.common.services.surveillance.data.responses.payloads.payloads.TakeBackCameraImageResponsePayload;

import java.util.List;

public class TakeBackCameraImageServiceHandler implements ServiceRequestHandler {
    @Override
    public void handle(Context context, ServiceRequest request) {
        Log.d("tag", "TakeBackCameraImageServiceHandler->handle(): " + request.stringify());

        TakeBackCameraImageRequestPayload requestPayload =
                SurveillanceServiceRequestPayloads.takeBackCameraImageRequestPayload(request.payload());
        Log.d("tag", "TakeBackCameraImageServiceHandler->handle()->REQUESTED_IMAGE_QUALITY: " + requestPayload.imageQuality());

        SurveillanceService surveillanceService = SurveillanceService.get();

        String currentGroupName = surveillanceService.currentGroupName();
        String currentGroupPassword = surveillanceService.currentGroupPassword();
        String currentDeviceName = surveillanceService.currentDeviceName();

        String requestSenderDeviceName = request.senderDeviceName();

        // ===
        CameraService_V2 cameraService = CameraService_V2.get();

        int backCameraId = cameraService.getBackCameraId();

        cameraService.takeCameraImage(
                backCameraId,
                requestPayload.imageQuality(),
                (bytes, base64String) -> {
                    Log.d("tag", "TakeBackCameraImageServiceHandler->OnImageTaken(): " + bytes.length + " - " + base64String.length());

                    TakeBackCameraImageResponsePayload responsePayload =
                            SurveillanceServiceResponsePayloads.takeBackCameraImageResponsePayload(base64String);

                    ServiceResponse response = new ServiceResponse(
                            ServiceResponse.TYPE_RESULT,
                            request.id(),
                            responsePayload.jsonObject()
                    );

                    surveillanceService.sendResponse(
                            currentGroupName,
                            currentGroupPassword,
                            requestSenderDeviceName,
                            response
                    );
                },
                (code, message) -> {
                    Log.d("tag", "TakeBackCameraImageServiceHandler->OnImageTakeError(): " + code + " - " + message);
                }
        );
        // ===
//        CameraService.get().takeBackCameraImage(
//                context,
//                (bytes, base64String) -> {
//                    Log.d("tag", "TakeBackCameraImageServiceHandler->OnImageTaken(): " + bytes.length + " - " + base64String.length());
//
//                    TakeBackCameraImageResponsePayload responsePayload =
//                            SurveillanceServiceResponsePayloads.takeBackCameraImageResponsePayload(base64String);
//
//                    ServiceResponse response = new ServiceResponse(
//                            ServiceResponse.TYPE_RESULT,
//                            request.id(),
//                            responsePayload.jsonObject()
//                    );
//
//                    surveillanceService.sendResponse(
//                            currentGroupName,
//                            currentGroupPassword,
//                            requestSenderDeviceName,
//                            response
//                    );
//                },
//                (code, message) -> {
//                    Log.d("tag", "TakeBackCameraImageServiceHandler->OnImageTakeError(): " + code + " - " + message);
//                }
//        );

        List<String> requestsPath = FBSPathsService.get().requestsPath(currentGroupName, currentGroupPassword, currentDeviceName);
        if (request.key() != null) {
            FBSService.get().removeValueFromList(requestsPath, request.key());
        } else {
            Log.d("tag", "TakeBackCameraImageServiceHandler->handle()->BAD_REQUEST_KEY: " + request.stringify());
        }
    }

//    protected CameraDevice.StateCallback cameraStateCallback = new CameraDevice.StateCallback() {
//        @Override
//        public void onOpened(@NonNull CameraDevice camera) {
//            Log.d("tag", "CameraDevice.StateCallback onOpened");
////            cameraDevice = camera;
////            actOnReadyCameraDevice();
//        }
//
//        @Override
//        public void onDisconnected(@NonNull CameraDevice camera) {
//            Log.w("tag", "CameraDevice.StateCallback onDisconnected");
//        }
//
//        @Override
//        public void onError(@NonNull CameraDevice camera, int error) {
//            Log.e("tag", "CameraDevice.StateCallback onError " + error);
//        }
//    };

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    private void test_2(Context context) {
//        Log.d("tag", "===> TEST_2");
//
//        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
//
//        String cameraId = null;
//        try {
//            for (String camId : cameraManager.getCameraIdList()) {
//                CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(camId);
//                int cOrientation = characteristics.get(CameraCharacteristics.LENS_FACING);
//                if (cOrientation == CameraCharacteristics.LENS_FACING_BACK) {
//                    cameraId = camId;
//                    break;
//                }
//            }
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//
//        if (cameraId == null) {
//            Log.d("tag", "CAMERA_IS_NULL");
//            return;
//        } else {
//            Log.d("tag", "CAMERA_ID: " + cameraId);
//        }
//
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            Log.d("tag", "PERMISSION_NOT_GRANTED");
//            return;
//        }
//        try {
//            cameraManager.openCamera(cameraId, cameraStateCallback, null);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//    }

    Camera camera;
    CameraPreview cameraPreview;
    WindowManager mWindowManager;
    private void test(Context context) {
        Log.d("tag", "===> TEST <===");

        int cameraCount = 0;
        camera = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                try {
                    camera = Camera.open(camIdx);
                    break;
                } catch (RuntimeException e) {
                    Log.e("CAMERA", "Camera failed to open: " + e.getLocalizedMessage());
                }
            }
        }

        if (camera == null) {
            Log.d("tag", "==> CAM_IS_NULL");
            return;
        } else {
            Log.d("tag", "==> CAM_NOT_NULL: " + camera.toString());
        }

        // ===
        Camera.PictureCallback jpegPictureCallback = (bytes, cam) -> {
            Log.d("tag", "==> IN_JPEG_PICTURE_CALLBACK");

            camera.release();
        };

        Camera.PictureCallback rawPictureCallback = (bytes, camera1) -> {
            Log.d("tag", "==> IN_RAW_PICTURE_CALLBACK");
        };

        Camera.ShutterCallback shutterCallback = () -> {
            Log.d("tag", "==> IN_SHUTTER_PICTURE_CALLBACK");
        };

        try {
            Camera.Parameters params = camera.getParameters();
            camera.setParameters(params);

            SurfaceTexture surfaceTexture = new SurfaceTexture(0);
            camera.setPreviewTexture(surfaceTexture);

            camera.startPreview();
            camera.takePicture(null, null, jpegPictureCallback);

//            Camera.Parameters p = camera.getParameters();
//            p.setPreviewSize(640, 480);
//            p.setPreviewFormat(PixelFormat.YCbCr_420_SP);
//            camera.setParameters(p);

            // ===
//            cameraPreview = new CameraPreview(context, camera);
//            cameraPreview.setLayoutParams(
//                    new ViewGroup.LayoutParams(
//                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
//                    )
//            );
//            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
//                    1, 1,
//                    Build.VERSION.SDK_INT < Build.VERSION_CODES.O ?
//                            WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY :
//                            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
//                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
//                    PixelFormat.TRANSLUCENT
//            );
//            mWindowManager.addView(cameraPreview, layoutParams);
//
//            camera.setPreviewDisplay(cameraPreview.getHolder());
            // ===

//            camera.startPreview();

//            camera.unlock();

//            camera.takePicture(shutterCallback, rawPictureCallback, jpegPictureCallback);

//            camera.startPreview();
//            camera.unlock();

//            camera.takePicture(shutterCallback, rawPictureCallback, jpegPictureCallback);



//            try {
//                camera.setPreviewDisplay(mSurfaceHolder);
//                camera.startPreview();
//            }
//            catch (IOException e) {
//                Log.d("tag", e.getMessage());
//                e.printStackTrace();
//                camera.release();
//            }


//            cameraPreview = new CameraPreview(context, camera);
//
//            camera.takePicture(null, null, jpegPictureCallback);
//            camera.startPreview();

//            camera.setPreviewDisplay();

//            camera.startPreview();
//            camera.setOneShotPreviewCallback((bytes, camera) -> Log.d("tag", "setOneShotPreviewCallback"));
//            camera.setOneShotPreviewCallback(new Camera.PreviewCallback() {
//                                                 @Override
//                                                 public void onPreviewFrame(byte[] data, Camera camera) {
//                                                     camera.takePicture(null, null, (data1, camera12) -> {
//                                                         // do something you want with your picture and stop preview
//                                                         camera12.stopPreview();
//                                                     });
//                                                 );
//            camera.startPreview();
//            camera.reconnect();
//            camera.setOneShotPreviewCallback((bytes, camera) -> Log.d("tag", "setOneShotPreviewCallback"));
//            camera.takePicture(shutterCallback, rawPictureCallback, jpegPictureCallback);

            Log.d("tag", "PICTURE_TAKEN");
        } catch (Exception e) {
            Log.d("tag", "==> EXCEPTION_TAKE_PICTURE_1: " + e.getLocalizedMessage());
            Log.d("tag", "==> EXCEPTION_TAKE_PICTURE_2: " + e.toString());
            e.printStackTrace();

            camera.release();
//            mWindowManager.removeView(cameraPreview);
        }
        // ===

        camera.release();
//        mWindowManager.removeView(cameraPreview);

        Log.d("tag", "==============================");
    }

    private CameraPreview addPreView() {
//        //create fake camera view
//        CameraPreview cameraSourceCameraPreview = new CameraPreview(this, this);
//        cameraSourceCameraPreview.setLayoutParams(new ViewGroup
//                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//
//        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        WindowManager.LayoutParams params = new WindowManager.LayoutParams(1, 1,
//                Build.VERSION.SDK_INT < Build.VERSION_CODES.O ?
//                        WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY :
//                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
//                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
//                PixelFormat.TRANSLUCENT);
//
//        mWindowManager.addView(cameraSourceCameraPreview, params);
//        return cameraSourceCameraPreview;
        return null;
    }
    // ============================
    // ============================

//    Camera mCamera;
//    boolean safeToCapture;
//
//    Camera.PictureCallback mCall = (data, camera) -> {
//        safeToCapture = false;
//        //decode the data obtained by the camera into a Bitmap
//
//        FileOutputStream outStream = null;
//        try{
//
//            // create a File object for the parent directory
//            File myDirectory = new File(Environment.getExternalStorageDirectory()+"/Test");
//            // have the object build the directory structure, if needed.
//            myDirectory.mkdirs();
//
//            //SDF for getting current time for unique image name
//            SimpleDateFormat curTimeFormat = new SimpleDateFormat("ddMMyyyyhhmmss");
//            String curTime = curTimeFormat.format(new java.util.Date());
//
//            // create a File object for the output file
//            outStream = new FileOutputStream(myDirectory+"/user"+curTime+".jpg");
//            outStream.write(data);
//            outStream.close();
//            mCamera.release();
//            mCamera = null;
//
//            String strImagePath = Environment.getExternalStorageDirectory()+"/"+myDirectory.getName()+"/user"+curTime+".jpg";
//            setEmailWithImage(strImagePath);
//            Log.d("CAMERA", "picture clicked - "+strImagePath);
//        } catch (FileNotFoundException e){
//            Log.d("CAMERA", e.getMessage());
//        } catch (IOException e){
//            Log.d("CAMERA", e.getMessage());
//        }
//
//        safeToCapture = true;    //Set a boolean to true again after saving file.
//
//    };
//
//    private void test(Context context) {
//        Log.d("tag", "TakeBackCameraImageServiceHandler->test()");
//
//        mCamera = getAvailableFrontCamera();
//        if (mCamera == null) {
//            Log.d("tag", "TakeBackCameraImageServiceHandler->test(): CAMERA_IS_NULL");
//            return;
//        }
//
//        SurfaceView sv = new SurfaceView(context);
//        SurfaceTexture surfaceTexture = new SurfaceTexture(10);
//
//        // ===
//        try {
//            mCamera.setPreviewTexture(surfaceTexture);
//            //mCamera.setPreviewDisplay(sv.getHolder());
//            Camera.Parameters parameters = mCamera.getParameters();
//
//            //set camera parameters
//            mCamera.setParameters(parameters);
//
//
//            //This boolean is used as app crashes while writing images to file if simultaneous calls are made to takePicture
//            if(safeToCapture) {
//                mCamera.startPreview();
//                mCamera.takePicture(null, null, mCall);
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        // ===
//
//        //Get a surface
//        SurfaceHolder sHolder = sv.getHolder();
//        //tells Android that this surface will have its data constantly replaced
//        sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//    }
//
//    private Camera getAvailableFrontCamera (){
//        int cameraCount = 0;
//        Camera cam = null;
//        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
//        cameraCount = Camera.getNumberOfCameras();
//        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
//            Camera.getCameraInfo(camIdx, cameraInfo);
//            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
//                try {
//                    cam = Camera.open(camIdx);
//                } catch (RuntimeException e) {
//                    Log.e("CAMERA", "Camera failed to open: " + e.getLocalizedMessage());
//                }
//            }
//        }
//
//        return cam;
//    }
//
//    private void setEmailWithImage(String str) {
//        Log.d("tag", "setEmailWithImage(): " + str);
//    }
}
