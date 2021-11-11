package com.vision.rn_modules.surveillance.module_actions_executor.handlers;


import android.content.Context;
import android.graphics.ImageFormat;
import android.util.Base64;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.vision.rn_modules.modules_common.interfaces.js_action_handler.JSActionHandler;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.helpers.AssetFilesHelper;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Test_TestTakeCameraPreviewPictureHandler implements JSActionHandler {
    private final int IN_WIDTH = 300;
    private final int IN_HEIGHT = 300;
    private final float WH_RATIO = (float)IN_WIDTH / IN_HEIGHT;
    private final double IN_SCALE_FACTOR = 0.007843;
    private final double MEAN_VAL = 127.5;
    private final double THRESHOLD = 0.5;

    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "Test_TestTakeCameraPreviewPictureHandler->handle()");

        // ===
//        int rotation = context.getCurrentActivity().getWindowManager().getDefaultDisplay()
//                .getRotation();
//
//        int degrees = 0;
//        switch (rotation) {
//            case Surface.ROTATION_0: degrees = 0; break;
//            case Surface.ROTATION_90: degrees = 90; break;
//            case Surface.ROTATION_180: degrees = 180; break;
//            case Surface.ROTATION_270: degrees = 270; break;
//        }
//
//        Log.d("tag", rotation + " - " + degrees);
        // ===

//        CameraService_V3 cameraService = CameraService_V3.get();
//        if (cameraService.isCameraPreviewRunning()) {
//            CameraPreviewFrameData previewImageData = cameraService.getPreviewImageData();
//            if (previewImageData == null || !previewImageData.hasImage()) {
//                Log.d("tag", "Test_TestTakeCameraPreviewPictureHandler->handle(): PREVIEW_IMAGE_DATA_IS_NULL");
//                result.resolve(null);
//            }
//
//            Mat rgbaMat = cameraPreviewImageToRgbaMat(
//                    previewImageData.imageBytes(),
//                    previewImageData.width(),
//                    previewImageData.height(),
//                    previewImageData.imageFormat()
//            );
//            rgbaMat = rotateMat(rgbaMat, 270);
//
////            byte[] processedImage = processImage(context, rgbaMat);
//            byte[] jpgBytes = matToJpgBytes(rgbaMat);
//
//            String base64ImageString = bytesToBase64String(jpgBytes);
//            result.resolve(imageTakenEventPayload(base64ImageString));
//        } else {
//            Log.d("tag", "Test_TestTakeCameraPreviewPictureHandler->handle(): CAMERA_SERVICE_NOT_RUNNING");
//            result.resolve(null);
//        }

        result.resolve(null);
    }

    private WritableMap imageTakenEventPayload(String base64String) {
        WritableMap jsPayload = new WritableNativeMap();
        jsPayload.putString("base64String", base64String);
        return jsPayload;
    }

    private String bytesToBase64String(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    private Mat cameraPreviewImageToRgbaMat(byte[] imageBytes, int width, int height, int previewFormat) {
        Mat mYuvFrameData = new Mat(height + (height/2), width, CvType.CV_8UC1);
        mYuvFrameData.put(0, 0, imageBytes);

        Mat mRgba = new Mat();
        if (previewFormat == ImageFormat.NV21) {
            Imgproc.cvtColor(mYuvFrameData, mRgba, Imgproc.COLOR_YUV2BGRA_NV21, 4);
        } else if (previewFormat == ImageFormat.YV12) {
            Imgproc.cvtColor(mYuvFrameData, mRgba, Imgproc.COLOR_YUV2BGRA_YV12, 4);
        } else {
            Log.d("tag", "Test_TestCameraMotionDetectionHandler->handle()->UNKNOWN_IMAGE_FORMAT: " + previewFormat);
            return null;
        }
//        Mat mRgba = new Mat();
//        if (previewFormat == ImageFormat.NV21) {
//            Imgproc.cvtColor(mYuvFrameData, mRgba, Imgproc.COLOR_YUV2RGBA_NV21, 4);
//        } else if (previewFormat == ImageFormat.YV12) {
//            Imgproc.cvtColor(mYuvFrameData, mRgba, Imgproc.COLOR_YUV2RGB_I420, 4);
//        } else {
//            Log.d("tag", "Test_TestCameraMotionDetectionHandler->handle()->UNKNOWN_IMAGE_FORMAT: " + previewFormat);
//            return null;
//        }

        return mRgba;
    }

    private Mat rotateMat(Mat mat, int angle) {
        Mat dst = new Mat(mat.rows(), mat.cols(), mat.type());

        Point center = new Point(dst.cols() / 2, dst.rows() / 2);

        Mat rotationMatrix = Imgproc.getRotationMatrix2D(center, angle, 1.5);

        Size size = new Size(mat.cols(), mat.cols());

        Imgproc.warpAffine(mat, dst, rotationMatrix, size);

        return dst;
    }

    private byte[] processImage(Context context, Mat bgraMat) {
        String[] classNames = new String[] {"background",
                "aeroplane", "bicycle", "bird", "boat",
                "bottle", "bus", "car", "cat", "chair", "cow", "diningtable",
                "dog", "horse", "motorbike", "person", "pottedplant", "sheep",
                "sofa", "train", "tvmonitor"};

        boolean modelFileExists = false;
        File modelFile = context.getExternalFilesDir("MobileNetSSD_deploy.caffemodel");
        if (modelFile != null && modelFile.exists()) {
            modelFileExists = true;
        }

        boolean protoFileExists = false;
        File protoFile = context.getExternalFilesDir("MobileNetSSD_deploy.prototxt");
        if (protoFile != null && protoFile.exists()) {
            protoFileExists = true;
        }

        if (!modelFileExists || !protoFileExists) {
            return null;
        }

        Mat inputFrame = new Mat();
        Imgproc.cvtColor(bgraMat, inputFrame, Imgproc.COLOR_RGBA2RGB);

        Mat blob = Dnn.blobFromImage(
                inputFrame,
                IN_SCALE_FACTOR,
                new Size(IN_WIDTH, IN_HEIGHT),
                new Scalar(MEAN_VAL, MEAN_VAL, MEAN_VAL),
                false
        );

        Net net = Dnn.readNetFromCaffe(protoFile.getAbsolutePath(), modelFile.getAbsolutePath());
        net.setInput(blob);
        Mat detections = net.forward();

        int cols = inputFrame.cols();
        int rows = inputFrame.rows();

        Size cropSize;
        if ((float)cols / rows > WH_RATIO) {
            cropSize = new Size(rows * WH_RATIO, rows);
        } else {
            cropSize = new Size(cols, cols / WH_RATIO);
        }

        int y1 = (int)(rows - cropSize.height) / 2;
        int y2 = (int)(y1 + cropSize.height);
        int x1 = (int)(cols - cropSize.width) / 2;
        int x2 = (int)(x1 + cropSize.width);

        Mat subFrame = inputFrame.submat(y1, y2, x1, x2);
        cols = subFrame.cols();
        rows = subFrame.rows();
        detections = detections.reshape(1, (int)detections.total() / 7);

        for (int i = 0; i < detections.rows(); ++i) {
            double confidence = detections.get(i, 2)[0];
            if (confidence > THRESHOLD) {
                int classId = (int)detections.get(i, 1)[0];
                int xLeftBottom = (int)(detections.get(i, 3)[0] * cols);
                int yLeftBottom = (int)(detections.get(i, 4)[0] * rows);
                int xRightTop   = (int)(detections.get(i, 5)[0] * cols);
                int yRightTop   = (int)(detections.get(i, 6)[0] * rows);
                // Draw rectangle around detected object.
                Imgproc.rectangle(subFrame, new Point(xLeftBottom, yLeftBottom),
                        new Point(xRightTop, yRightTop),
                        new Scalar(0, 255, 0));
                String label = classNames[classId] + ": " + confidence;
                Log.d("tag", "DETECTION: " + label);

                int[] baseLine = new int[1];
                Size labelSize = Imgproc.getTextSize(label, Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, 1, baseLine);
                // Draw background for label.
                Imgproc.rectangle(subFrame, new Point(xLeftBottom, yLeftBottom - labelSize.height),
                        new Point(xLeftBottom + labelSize.width, yLeftBottom + baseLine[0]),
                        new Scalar(255, 255, 255), Core.FILLED);
                // Write class name and confidence.
//                Imgproc.putText (
//                        subFrame,                          // Matrix obj of the image
//                        label,          // Text to be added
//                        new Point(xLeftBottom, yLeftBottom),               // point
//                        Imgproc.FONT_HERSHEY_SIMPLEX ,      // front face
//                        1,                               // front scale
//                        new Scalar(0, 0, 0),             // Scalar object for color
//                        41                               // Thickness
//                );
                Imgproc.putText(subFrame, label, new Point(xLeftBottom, yLeftBottom),
                        Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 0, 0));
            }
        }

        // create a temporary buffer
        MatOfByte buffer = new MatOfByte();
        // encode the frame in the buffer, according to the PNG format
        Imgcodecs.imencode(".jpg", subFrame, buffer);

        return buffer.toArray();
    }

    private byte[] matToJpgBytes(Mat mat) {
        // create a temporary buffer
        MatOfByte buffer = new MatOfByte();
        // encode the frame in the buffer, according to the PNG format
        Imgcodecs.imencode(".jpg", mat, buffer);

        return buffer.toArray();
    }

    private void copyAssets(Context context) {
        Set<String> assetsToCopy = new HashSet<>();
        assetsToCopy.add("MobileNetSSD_deploy.caffemodel");
        assetsToCopy.add("MobileNetSSD_deploy.prototxt");
        assetsToCopy.add("image.jpg");

        AssetFilesHelper.copyAssets(context, assetsToCopy);
    }
}
