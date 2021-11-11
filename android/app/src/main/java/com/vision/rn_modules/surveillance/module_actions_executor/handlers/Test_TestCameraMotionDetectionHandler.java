package com.vision.rn_modules.surveillance.module_actions_executor.handlers;


import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Environment;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.vision.rn_modules.modules_common.interfaces.js_action_handler.JSActionHandler;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.helpers.AssetFilesHelper;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test_TestCameraMotionDetectionHandler implements JSActionHandler {
    private Thread mThread;
    private boolean mStopThread;

    private Net mNet;
    private String[] mClassNames;

    private final int IN_WIDTH = 300;
    private final int IN_HEIGHT = 300;
    private final float WH_RATIO = (float)IN_WIDTH / IN_HEIGHT;
    private final double IN_SCALE_FACTOR = 0.007843;
    private final double MEAN_VAL = 127.5;
    private final double THRESHOLD = 0.5;

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
//            do {
//                Log.d("tag", "THREAD_RUNNING: " + System.currentTimeMillis());
//
//                CameraMotionDetectionService cameraMotionDetectionService = CameraMotionDetectionService.get();
//
//                CameraMotionDetectionService.RequestImageCallback requestImageCallback = (imageBytes, width, height, previewFormat) -> {
//                    Log.d("tag", "CameraWorker->run()->requestImageCallback: " + imageBytes.length);
//
//                    Mat mYuvFrameData = new Mat(height + (height/2), width, CvType.CV_8UC1);
//                    mYuvFrameData.put(0, 0, imageBytes);
//
//                    Log.d("tag", "mYuvFrameData: " + mYuvFrameData.toString() + " - " + mYuvFrameData.empty());
//
//                    Mat mRgba = new Mat();
//                    if (previewFormat == ImageFormat.NV21) {
//                        Imgproc.cvtColor(mYuvFrameData, mRgba, Imgproc.COLOR_YUV2RGBA_NV21, 4);
//                    } else if (previewFormat == ImageFormat.YV12) {
//                        Imgproc.cvtColor(mYuvFrameData, mRgba, Imgproc.COLOR_YUV2RGB_I420, 4);
//                    } else {
//                        Log.d("tag", "Test_TestCameraMotionDetectionHandler->handle()->UNKNOWN_IMAGE_FORMAT: " + previewFormat);
//                        return;
//                    }
//
//                    // create a temporary buffer
//                    MatOfByte buffer = new MatOfByte();
//                    // encode the frame in the buffer, according to the PNG format
//                    Imgcodecs.imencode(".jpg", mRgba, buffer);
//
//                    Log.d("tag", "BUFFER: " + buffer.toString());
//
//                    String base64 = Base64.encodeToString(buffer.toArray(), Base64.DEFAULT);
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
//            } while (!mStopThread);
//            Log.d("tag", "Finish processing thread");
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
        Log.d("tag", "Test_TestCameraMotionDetectionHandler->handle(): " + Environment.getExternalStorageDirectory().getAbsolutePath());
//        Log.d("tag", "Test_TestCameraMotionDetectionHandler->handle(): " + Objects.requireNonNull(context.getExternalFilesDir(null)).getAbsolutePath());

        // ===
        List<String> assetFileNames = new ArrayList<>();
        assetFileNames.add("MobileNetSSD_deploy.caffemodel");
        assetFileNames.add("MobileNetSSD_deploy.prototxt");

        AssetFilesHelper.deleteCopiedAssetFiles(context, assetFileNames);

//        Set<String> assetsToCopy = new HashSet<>();
//        assetsToCopy.add("MobileNetSSD_deploy.caffemodel");
//
//        AssetFilesHelper.copyAssets(context, assetsToCopy);
        // ===

//        File outFile = new File(context.getExternalFilesDir(null), "M_MobileNetSSD_deploy.caffemodel");
//        Log.d("tag", "Test_TestCameraMotionDetectionHandler->handle(): " + outFile.getAbsolutePath() + " - " + outFile.isFile() + " - " + outFile.isDirectory());
//
//        try (FileOutputStream fos=new FileOutputStream(outFile)) {
//            Log.d("tag", "Test_TestCameraMotionDetectionHandler->handle()->HERE");
//
//            String string = "MyString";
//
//            fos.write(string.getBytes(Charset.forName("UTF-8")));
//            fos.flush();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        AssetManager assetManager = context.getAssets();
//        try {
//            InputStream in = assetManager.open("MobileNetSSD_deploy.caffemodel");
//            Log.d("tag", "Test_TestCameraMotionDetectionHandler->handle()->HERE_HERE: " + (in == null));
//
//            in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // ===
        // =====
//        Environment.getExternalStorageDirectory();


        Set<String> assetsToCopy = new HashSet<>();
        assetsToCopy.add("MobileNetSSD_deploy.caffemodel");
        assetsToCopy.add("MobileNetSSD_deploy.prototxt");
        assetsToCopy.add("image.jpg");

        AssetFilesHelper.copyAssets(context, assetsToCopy);

        mClassNames = new String[] {"background",
                "aeroplane", "bicycle", "bird", "boat",
                "bottle", "bus", "car", "cat", "chair", "cow", "diningtable",
                "dog", "horse", "motorbike", "person", "pottedplant", "sheep",
                "sofa", "train", "tvmonitor"};

        File modelFile = context.getExternalFilesDir("MobileNetSSD_deploy.caffemodel");
        if (modelFile != null && modelFile.exists()) {
            Log.d("tag", "===> MODEL_EXISTS: " + modelFile.isFile() + " - " + modelFile.isDirectory());
        } else {
            Log.d("tag", "===> FILE_NOT_EXISTS");
        }

        File protoFile = context.getExternalFilesDir("MobileNetSSD_deploy.prototxt");
        if (protoFile != null && protoFile.exists()) {
            Log.d("tag", "===> PROTO_EXISTS: " + protoFile.isFile() + " - " + protoFile.isDirectory());
        } else {
            Log.d("tag", "===> FILE_NOT_EXISTS");
        }

        assert protoFile != null;
        assert modelFile != null;

        mNet = Dnn.readNetFromCaffe(protoFile.getAbsolutePath(), modelFile.getAbsolutePath());
        Log.d("tag", "NET_IS_EMPTY: " + mNet.empty());

        File imageFile = context.getExternalFilesDir("image.jpg");
        if (imageFile == null || !imageFile.exists()) {
            Log.d("tag", "IMAGE_FILE_NOT_EXIST");
            return;
        }
        Log.d("tag", "IMAGE_LOADED");

        Mat inputFrame = Imgcodecs.imread(imageFile.getAbsolutePath());
        Imgproc.cvtColor(inputFrame, inputFrame, Imgproc.COLOR_RGBA2RGB);

        Mat blob = Dnn.blobFromImage(
                inputFrame,
                IN_SCALE_FACTOR,
                new Size(IN_WIDTH, IN_HEIGHT),
                new Scalar(MEAN_VAL, MEAN_VAL, MEAN_VAL),
                false
        );
        Log.d("tag", "BLOB: " + blob.toString());

        mNet.setInput(blob);

        Log.d("tag", "Test_TestCameraMotionDetectionHandler->handle()->NET_IS_EMPTY: " + mNet.empty());






//        Mat detections = mNet.forward();

//        int cols = inputFrame.cols();
//        int rows = inputFrame.rows();
//
//        Size cropSize;
//        if ((float)cols / rows > WH_RATIO) {
//            cropSize = new Size(rows * WH_RATIO, rows);
//        } else {
//            cropSize = new Size(cols, cols / WH_RATIO);
//        }
//
//        int y1 = (int)(rows - cropSize.height) / 2;
//        int y2 = (int)(y1 + cropSize.height);
//        int x1 = (int)(cols - cropSize.width) / 2;
//        int x2 = (int)(x1 + cropSize.width);
//
//        Mat subFrame = inputFrame.submat(y1, y2, x1, x2);
//        cols = subFrame.cols();
//        rows = subFrame.rows();
//        detections = detections.reshape(1, (int)detections.total() / 7);
//
//        for (int i = 0; i < detections.rows(); ++i) {
//            double confidence = detections.get(i, 2)[0];
//            if (confidence > THRESHOLD) {
//                int classId = (int)detections.get(i, 1)[0];
//                int xLeftBottom = (int)(detections.get(i, 3)[0] * cols);
//                int yLeftBottom = (int)(detections.get(i, 4)[0] * rows);
//                int xRightTop   = (int)(detections.get(i, 5)[0] * cols);
//                int yRightTop   = (int)(detections.get(i, 6)[0] * rows);
//                // Draw rectangle around detected object.
//                Imgproc.rectangle(subFrame, new Point(xLeftBottom, yLeftBottom),
//                        new Point(xRightTop, yRightTop),
//                        new Scalar(0, 255, 0));
//                String label = mClassNames[classId] + ": " + confidence;
//
//                int[] baseLine = new int[1];
//                Size labelSize = Imgproc.getTextSize(label, Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, 1, baseLine);
//                // Draw background for label.
//                Imgproc.rectangle(subFrame, new Point(xLeftBottom, yLeftBottom - labelSize.height),
//                        new Point(xLeftBottom + labelSize.width, yLeftBottom + baseLine[0]),
//                        new Scalar(255, 255, 255), Core.FILLED);
//                // Write class name and confidence.
//                Imgproc.putText(subFrame, label, new Point(xLeftBottom, yLeftBottom),
//                        Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 0, 0));
//            }
//        }
//
//        Log.d("tag", "END: " + subFrame.toString());
//
//        // create a temporary buffer
//        MatOfByte buffer = new MatOfByte();
//        // encode the frame in the buffer, according to the PNG format
//        Imgcodecs.imencode(".jpg", subFrame, buffer);
//
//        Log.d("tag", "BUFFER: " + buffer.toString());
//
//        String base64 = Base64.encodeToString(buffer.toArray(), Base64.DEFAULT);
//        context
//                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//                .emit(
//                        "IMAGE_TAKEN_EVENT",
//                        imageTakenEventPayload(base64)
//                );
        // =====
        // ===

        result.resolve(true);
    }
}


//package com.vision.rn_modules.surveillance.module_actions_executor.handlers;
//
//
//import android.graphics.ImageFormat;
//import android.graphics.Rect;
//import android.graphics.YuvImage;
//import android.util.Base64;
//import android.util.Log;
//
//import com.facebook.react.bridge.Promise;
//import com.facebook.react.bridge.ReactApplicationContext;
//import com.facebook.react.bridge.ReadableMap;
//import com.facebook.react.bridge.WritableMap;
//import com.facebook.react.bridge.WritableNativeMap;
//import com.facebook.react.modules.core.DeviceEventManagerModule;
//import com.vision.rn_modules.modules_common.interfaces.js_action_handler.JSActionHandler;
//import com.vision.rn_modules.surveillance.module_actions_executor.handlers.helpers.CopyAssetsHelper;
//
//import org.opencv.core.Core;
//import org.opencv.core.Mat;
//import org.opencv.core.MatOfByte;
//import org.opencv.core.Point;
//import org.opencv.core.Scalar;
//import org.opencv.core.Size;
//import org.opencv.dnn.Dnn;
//import org.opencv.dnn.Net;
//import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.imgproc.Imgproc;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.util.HashSet;
//import java.util.Set;
//
//public class Test_TestCameraMotionDetectionHandler implements JSActionHandler {
//    private Thread mThread;
//    private boolean mStopThread;
//
//    private Net mNet;
//    private String[] mClassNames;
//
//    private final int IN_WIDTH = 300;
//    private final int IN_HEIGHT = 300;
//    private final float WH_RATIO = (float)IN_WIDTH / IN_HEIGHT;
//    private final double IN_SCALE_FACTOR = 0.007843;
//    private final double MEAN_VAL = 127.5;
//    private final double THRESHOLD = 0.5;
//
//    private class CameraWorker implements Runnable {
//        private ReactApplicationContext mContext;
//
//        public CameraWorker(ReactApplicationContext context) {
//            mContext = context;
//        }
//
//        private WritableMap imageTakenEventPayload(String base64String) {
//            WritableMap jsPayload = new WritableNativeMap();
//            jsPayload.putString("base64String", base64String);
//            return jsPayload;
//        }
//
//        @Override
//        public void run() {
////            do {
////                Log.d("tag", "THREAD_RUNNING: " + System.currentTimeMillis());
////
////                CameraMotionDetectionService cameraMotionDetectionService = CameraMotionDetectionService.get();
////
////                CameraMotionDetectionService.RequestImageCallback requestImageCallback = (imageBytes, width, height, previewFormat) -> {
////                    Log.d("tag", "CameraWorker->run()->requestImageCallback: " + imageBytes.length);
////
////                    Mat mYuvFrameData = new Mat(height + (height/2), width, CvType.CV_8UC1);
////                    mYuvFrameData.put(0, 0, imageBytes);
////
////                    Log.d("tag", "mYuvFrameData: " + mYuvFrameData.toString() + " - " + mYuvFrameData.empty());
////
////                    Mat mRgba = new Mat();
////                    if (previewFormat == ImageFormat.NV21) {
////                        Imgproc.cvtColor(mYuvFrameData, mRgba, Imgproc.COLOR_YUV2RGBA_NV21, 4);
////                    } else if (previewFormat == ImageFormat.YV12) {
////                        Imgproc.cvtColor(mYuvFrameData, mRgba, Imgproc.COLOR_YUV2RGB_I420, 4);
////                    } else {
////                        Log.d("tag", "Test_TestCameraMotionDetectionHandler->handle()->UNKNOWN_IMAGE_FORMAT: " + previewFormat);
////                        return;
////                    }
////
////                    // create a temporary buffer
////                    MatOfByte buffer = new MatOfByte();
////                    // encode the frame in the buffer, according to the PNG format
////                    Imgcodecs.imencode(".jpg", mRgba, buffer);
////
////                    Log.d("tag", "BUFFER: " + buffer.toString());
////
////                    String base64 = Base64.encodeToString(buffer.toArray(), Base64.DEFAULT);
////                    mContext
////                            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
////                            .emit(
////                                    "IMAGE_TAKEN_EVENT",
////                                    imageTakenEventPayload(base64)
////                            );
////                };
////
////                if (cameraMotionDetectionService.isRunning()) {
////                    cameraMotionDetectionService.requestImage(requestImageCallback);
////
////                    try {
////                        Thread.sleep(1000);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
////                }
////            } while (!mStopThread);
////            Log.d("tag", "Finish processing thread");
//        }
//    }
//
//    private WritableMap imageTakenEventPayload(String base64String) {
//        WritableMap jsPayload = new WritableNativeMap();
//        jsPayload.putString("base64String", base64String);
//        return jsPayload;
//    }
//
//    public static byte[] NV21toJPEG(byte[] nv21, int width, int height) {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        YuvImage yuv = new YuvImage(nv21, ImageFormat.NV21, width, height, null);
//        yuv.compressToJpeg(new Rect(0, 0, width, height), 100, out);
//        return out.toByteArray();
//    }
//
//    @Override
//    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
//        Log.d("tag", "Test_TestCameraMotionDetectionHandler->handle()");
//
//        // ===
//        // =====
//        Set<String> assetsToCopy = new HashSet<>();
//        assetsToCopy.add("MobileNetSSD_deploy.caffemodel");
//        assetsToCopy.add("MobileNetSSD_deploy.prototxt");
//        assetsToCopy.add("image.jpg");
//
//        CopyAssetsHelper.copyAssets(context, assetsToCopy);
//
//        mClassNames = new String[] {"background",
//                "aeroplane", "bicycle", "bird", "boat",
//                "bottle", "bus", "car", "cat", "chair", "cow", "diningtable",
//                "dog", "horse", "motorbike", "person", "pottedplant", "sheep",
//                "sofa", "train", "tvmonitor"};
//
//        File modelFile = context.getExternalFilesDir("MobileNetSSD_deploy.caffemodel");
//        if (modelFile != null && modelFile.exists()) {
//            Log.d("tag", "===> MODEL_EXISTS");
//        } else {
//            Log.d("tag", "===> FILE_NOT_EXISTS");
//        }
//
//        File protoFile = context.getExternalFilesDir("MobileNetSSD_deploy.prototxt");
//        if (protoFile != null && protoFile.exists()) {
//            Log.d("tag", "===> PROTO_EXISTS");
//        } else {
//            Log.d("tag", "===> FILE_NOT_EXISTS");
//        }
//
//        mNet = Dnn.readNetFromCaffe(protoFile.getAbsolutePath(), modelFile.getAbsolutePath());
//        Log.d("tag", "NET_IS_EMPTY: " + mNet.empty());
//
//        File imageFile = context.getExternalFilesDir("image.jpg");
//        if (imageFile == null || !imageFile.exists()) {
//            Log.d("tag", "IMAGE_FILE_NOT_EXIST");
//            return;
//        }
//        Log.d("tag", "IMAGE_LOADED");
//
//        Mat inputFrame = Imgcodecs.imread(imageFile.getAbsolutePath());
//        Imgproc.cvtColor(inputFrame, inputFrame, Imgproc.COLOR_RGBA2RGB);
//
//        Mat blob = Dnn.blobFromImage(
//                inputFrame,
//                IN_SCALE_FACTOR,
//                new Size(IN_WIDTH, IN_HEIGHT),
//                new Scalar(MEAN_VAL, MEAN_VAL, MEAN_VAL),
//                false
//        );
//        Log.d("tag", "BLOB: " + blob.toString());
//
//        mNet.setInput(blob);
//        Mat detections = mNet.forward();
//
//        int cols = inputFrame.cols();
//        int rows = inputFrame.rows();
//
//        Size cropSize;
//        if ((float)cols / rows > WH_RATIO) {
//            cropSize = new Size(rows * WH_RATIO, rows);
//        } else {
//            cropSize = new Size(cols, cols / WH_RATIO);
//        }
//
//        int y1 = (int)(rows - cropSize.height) / 2;
//        int y2 = (int)(y1 + cropSize.height);
//        int x1 = (int)(cols - cropSize.width) / 2;
//        int x2 = (int)(x1 + cropSize.width);
//
//        Mat subFrame = inputFrame.submat(y1, y2, x1, x2);
//        cols = subFrame.cols();
//        rows = subFrame.rows();
//        detections = detections.reshape(1, (int)detections.total() / 7);
//
//        for (int i = 0; i < detections.rows(); ++i) {
//            double confidence = detections.get(i, 2)[0];
//            if (confidence > THRESHOLD) {
//                int classId = (int)detections.get(i, 1)[0];
//                int xLeftBottom = (int)(detections.get(i, 3)[0] * cols);
//                int yLeftBottom = (int)(detections.get(i, 4)[0] * rows);
//                int xRightTop   = (int)(detections.get(i, 5)[0] * cols);
//                int yRightTop   = (int)(detections.get(i, 6)[0] * rows);
//                // Draw rectangle around detected object.
//                Imgproc.rectangle(subFrame, new Point(xLeftBottom, yLeftBottom),
//                        new Point(xRightTop, yRightTop),
//                        new Scalar(0, 255, 0));
//                String label = mClassNames[classId] + ": " + confidence;
//
//                int[] baseLine = new int[1];
//                Size labelSize = Imgproc.getTextSize(label, Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, 1, baseLine);
//                // Draw background for label.
//                Imgproc.rectangle(subFrame, new Point(xLeftBottom, yLeftBottom - labelSize.height),
//                        new Point(xLeftBottom + labelSize.width, yLeftBottom + baseLine[0]),
//                        new Scalar(255, 255, 255), Core.FILLED);
//                // Write class name and confidence.
//                Imgproc.putText(subFrame, label, new Point(xLeftBottom, yLeftBottom),
//                        Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 0, 0));
//            }
//        }
//
//        Log.d("tag", "END: " + subFrame.toString());
//
//        // create a temporary buffer
//        MatOfByte buffer = new MatOfByte();
//        // encode the frame in the buffer, according to the PNG format
//        Imgcodecs.imencode(".jpg", subFrame, buffer);
//
//        Log.d("tag", "BUFFER: " + buffer.toString());
//
//        String base64 = Base64.encodeToString(buffer.toArray(), Base64.DEFAULT);
//        context
//                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//                .emit(
//                        "IMAGE_TAKEN_EVENT",
//                        imageTakenEventPayload(base64)
//                );
//        // =====
//        // ===
//
////        CameraMotionDetectionService cameraMotionDetectionService = CameraMotionDetectionService.get();
////        cameraMotionDetectionService.test();
////
////        CameraMotionDetectionService.RequestImageCallback requestImageCallback = (imageBytes, width, height, previewFormat) -> {
////            Log.d("tag", "CameraWorker->run()->requestImageCallback: " + imageBytes.length + " - " + width + " - " + height + " - " + previewFormat);
////
////            Mat mYuvFrameData = new Mat(height + (height/2), width, CvType.CV_8UC1);
////            mYuvFrameData.put(0, 0, imageBytes);
////
////            Log.d("tag", "mYuvFrameData: " + mYuvFrameData.toString() + " - " + mYuvFrameData.empty());
////
////            Mat mRgba = new Mat();
////            if (previewFormat == ImageFormat.NV21) {
////                Imgproc.cvtColor(mYuvFrameData, mRgba, Imgproc.COLOR_YUV2RGBA_NV21, 4);
////            } else if (previewFormat == ImageFormat.YV12) {
////                Imgproc.cvtColor(mYuvFrameData, mRgba, Imgproc.COLOR_YUV2RGB_I420, 4);
////            } else {
////                Log.d("tag", "Test_TestCameraMotionDetectionHandler->handle()->UNKNOWN_IMAGE_FORMAT: " + previewFormat);
////                return;
////            }
////
////            // create a temporary buffer
////            MatOfByte buffer = new MatOfByte();
////            // encode the frame in the buffer, according to the PNG format
////            Imgcodecs.imencode(".jpg", mRgba, buffer);
////
////            Log.d("tag", "BUFFER: " + buffer.toString());
////
////            String base64 = Base64.encodeToString(buffer.toArray(), Base64.DEFAULT);
////            context
////                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
////                    .emit(
////                            "IMAGE_TAKEN_EVENT",
////                            imageTakenEventPayload(base64)
////                    );
////
//////            byte[] jpegImageBytes = NV21toJPEG(imageBytes, width, height);
//////            String base64 = Base64.encodeToString(jpegImageBytes, Base64.DEFAULT);
////
//////            Mat mYuvFrameData = new Mat();
//////            mYuvFrameData.put(0, 0, imageBytes);
//////
//////            Log.d("tag", "mYuvFrameData: " + mYuvFrameData.toString() + " - " + mYuvFrameData.empty());
////
//////            Mat mRgba = new Mat();
//////            if (previewFormat == ImageFormat.NV21) {
//////                Imgproc.cvtColor(mYuvFrameData, mRgba, Imgproc.COLOR_YUV2RGBA_NV21, 4);
//////            } else if (previewFormat == ImageFormat.YV12) {
//////                Imgproc.cvtColor(mYuvFrameData, mRgba, Imgproc.COLOR_YUV2RGB_I420, 4);
//////            } else {
//////                Log.d("tag", "Test_TestCameraMotionDetectionHandler->handle()->UNKNOWN_IMAGE_FORMAT: " + previewFormat);
//////                return;
//////            }
//////
//////
//////            // create a temporary buffer
//////            MatOfByte buffer = new MatOfByte();
//////            // encode the frame in the buffer, according to the PNG format
//////            Imgcodecs.imencode(".jpg", mRgba, buffer);
//////
//////            Log.d("tag", "BUFFER: " + buffer.toString());
//////
//////            String base64 = Base64.encodeToString(buffer.toArray(), Base64.DEFAULT);
//////            context
//////                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//////                    .emit(
//////                            "IMAGE_TAKEN_EVENT",
//////                            imageTakenEventPayload(base64)
//////                    );
////
////
//////            byte[] jpegImage = NV21toJPEG(imageBytes, width, height);
//////            Log.d("tag", "JPEG_IMAGE_SIZE: " + jpegImage.length);
////
//////            String base64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//////
//////            context
//////                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//////                    .emit(
//////                            "IMAGE_TAKEN_EVENT",
//////                            imageTakenEventPayload(base64)
//////                    );
////        };
////
////        if (cameraMotionDetectionService.isRunning()) {
////            cameraMotionDetectionService.requestImage(requestImageCallback);
//////            cameraMotionDetectionService.test();
////        }
//
//        // ===
//        // =====
////        CameraMotionDetectionService cameraMotionDetectionService = CameraMotionDetectionService.get();
////        cameraMotionDetectionService.test();
////
////        if (mThread == null && cameraMotionDetectionService.isRunning()) {
////            Log.d("tag", "WILL_START_THREAD");
////
////            mStopThread = false;
////            mThread = new Thread(new CameraWorker(context));
////            mThread.start();
////        } else {
////            Log.d("tag", "WILL_STOP_THREAD");
////
////            try {
////                mStopThread = true;
////                if (mThread != null) {
////                    mThread.join();
////                }
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            } finally {
////                mThread =  null;
////            }
////        }
//        // =====
//        // ===
//
////        mStopThread = false;
////        mThread = new Thread(new CameraWorker());
////        mThread.start();
//
////        CameraMotionDetectionService cameraMotionDetectionService = CameraMotionDetectionService.get();
////
////        CameraMotionDetectionService.RequestImageCallback requestImageCallback = imageBytes -> {
////            Log.d("tag", "Test_TestCameraMotionDetectionHandler->handle()->requestImageCallback");
////        };
////
////        if (cameraMotionDetectionService.isRunning()) {
////            cameraMotionDetectionService.requestImage(requestImageCallback);
////        }
//
//        result.resolve(true);
//    }
//}
