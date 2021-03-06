package com.vision.services.camera.helpers;


import android.content.Context;
import android.graphics.ImageFormat;
import android.util.Log;

import com.vision.services.camera.data.camera_frame_detections.CameraFrameDetections;
import com.vision.services.camera.data.camera_frame_detections.item.CameraFrameDetectionItem;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.helpers.AssetFilesHelper;

import org.opencv.android.OpenCVLoader;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OpenCVHelper {
    private static boolean mInitialized = false;
    private static String mCaffeModelFileName = "MobileNetSSD_deploy.caffemodel";
    private static String mCaffeModelProtoFileName = "MobileNetSSD_deploy.prototxt";
    private static File mCaffeModelFile = null;
    private static File mCaffeModelProtoFile = null;
    private static String[] mDetectedClassNames = new String[] {"background",
            "aeroplane", "bicycle", "bird", "boat",
            "bottle", "bus", "car", "cat", "chair", "cow", "diningtable",
            "dog", "horse", "motorbike", "person", "pottedplant", "sheep",
            "sofa", "train", "tvmonitor"};
    private static final int IN_WIDTH = 300;
    private static final int IN_HEIGHT = 300;
    private static final float WH_RATIO = (float)IN_WIDTH / IN_HEIGHT;
    private static final double IN_SCALE_FACTOR = 0.007843;
    private static final double MEAN_VAL = 127.5;
    private static final double THRESHOLD = 0.5;
    private static Net mNet;

    public static void init() {
        if (mInitialized) {
            return;
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            OpenCVLoader.initDebug();
            mInitialized = true;
        } else {
            mInitialized = false;
        }
    }

    public static boolean available() {
        return mInitialized;
    }

    public static String[] detectedClassNames() {
        return Arrays.copyOf(mDetectedClassNames, mDetectedClassNames.length);
    }

    public static int getClassForDetectableObjectType(String objectType) {
        switch (objectType) {
            case ("background"): {
                return 0;
            }

            case ("aeroplane"): {
                return 1;
            }

            case ("bicycle"): {
                return 2;
            }


            case ("bird"): {
                return 3;
            }

            case ("boat"): {
                return 4;
            }

            case ("bottle"): {
                return 5;
            }

            case ("bus"): {
                return 6;
            }

            case ("car"): {
                return 7;
            }

            case ("cat"): {
                return 8;
            }

            case ("chair"): {
                return 9;
            }

            case ("cow"): {
                return 10;
            }

            case ("diningtable"): {
                return 11;
            }

            case ("dog"): {
                return 12;
            }

            case ("horse"): {
                return 13;
            }

            case ("motorbike"): {
                return 14;
            }

            case ("person"): {
                return 15;
            }

            case ("pottedplant"): {
                return 16;
            }

            case ("sheep"): {
                return 17;
            }

            case ("sofa"): {
                return 18;
            }

            case ("train"): {
                return 19;
            }

            case ("tvmonitor"): {
                return 20;
            }

            default: {
                Log.d("tag", "OpenCVHelper->getClassForDetectableObjectType()->UNKNOWN_OBJECT_TYPE: " + objectType);
                return -1;
            }
        }
    }

    public static int getObjectOfClassInFrameCount(int classId, CameraFrameDetections frameDetections) {
        int objectsOfClassInFrame = 0;

        List<CameraFrameDetectionItem> frameDetectionItems = frameDetections.detections();
        for (int i = 0; i < frameDetectionItems.size(); ++i) {
            CameraFrameDetectionItem frameDetectionItem = frameDetectionItems.get(i);
            if (frameDetectionItem.classId() == classId) {
                ++objectsOfClassInFrame;
            }
        }

        return objectsOfClassInFrame;
    }

    public static Mat yuvBytesToRgbaMat(byte[] yuvBytes, int width, int height, int yuvImageFormat) {
        Mat yuvFrameData = new Mat(height + (height/2), width, CvType.CV_8UC1);
        yuvFrameData.put(0, 0, yuvBytes);

        Mat rgba = new Mat();
        if (yuvImageFormat == ImageFormat.NV21) {
            Imgproc.cvtColor(yuvFrameData, rgba, Imgproc.COLOR_YUV2BGRA_NV21, 4);
        } else if (yuvImageFormat == ImageFormat.YV12) {
            Imgproc.cvtColor(yuvFrameData, rgba, Imgproc.COLOR_YUV2BGRA_YV12, 4);
        } else {
            Log.d("tag", "OpenCVHelper->yuvBytesToRgbaMat()->UNKNOWN_IMAGE_FORMAT: " + yuvImageFormat);
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

        return rgba;
    }

    public static Mat rotateMat(Mat mat, int angle) {
        Mat dst = new Mat(mat.rows(), mat.cols(), mat.type());

        Point center = new Point(dst.cols() / 2, dst.rows() / 2);

        Mat rotationMatrix = Imgproc.getRotationMatrix2D(center, -angle, 1.5);

        Size size = new Size(mat.cols(), mat.cols());

        Imgproc.warpAffine(mat, dst, rotationMatrix, size);

        return dst;
    }

    public static byte[] rgbaMatToJpgBytes(Mat rgbaMat) {
        // create a temporary buffer
        MatOfByte buffer = new MatOfByte();
        // encode the frame in the buffer, according to the PNG format
        Imgcodecs.imencode(".jpg", rgbaMat, buffer);

        return buffer.toArray();
    }

    public static CameraFrameDetections detectObjectsOnImageMat(Context context, Mat rgbaMat) {
        if (context == null || rgbaMat == null || rgbaMat.empty()) {
            Log.d("tag", "OpenCVHelper->detectObjectsOnImageMat()->BAD_CONTEXT_OR_INPUT_MAT");
            return new CameraFrameDetections();
        }

//        boolean modelFileExists = false;
//        File modelFile = context.getExternalFilesDir(mCaffeModelFileName);
//        if (modelFile != null && modelFile.exists()) {
//            modelFileExists = true;
//        } else {
//            copyDnnModelAssets(context);
//            modelFile = context.getExternalFilesDir(mCaffeModelFileName);
//            if (modelFile == null || !modelFile.exists()) {
//                Log.d("tag", "OpenCVHelper->detectObjectsOnImageMat()->UNABLE_TO_LOAD_MODEL_ASSETS");
//                return new CameraFrameDetections();
//            }
//        }

//        boolean protoFileExists = false;
//        File protoFile = context.getExternalFilesDir(mCaffeModelProtoFileName);
//        if (protoFile != null && protoFile.exists()) {
//            protoFileExists = true;
//        } else {
//            copyDnnModelAssets(context);
//            protoFile = context.getExternalFilesDir(mCaffeModelProtoFileName);
//            if (protoFile == null || !protoFile.exists()) {
//                Log.d("tag", "OpenCVHelper->detectObjectsOnImageMat()->UNABLE_TO_LOAD_PROTO_ASSETS");
//                return new CameraFrameDetections();
//            }
//        }

//        if (!modelFileExists || !protoFileExists) {
//            Log.d("tag", "OpenCVHelper->detectObjectsOnImageMat()->BAD_MODEL_OR_PROTO_FILE");
//            return new CameraFrameDetections();
//        }

        if (mCaffeModelFile == null || mCaffeModelProtoFile == null) {
            copyDnnModelAssets(context);

            mCaffeModelFile = context.getExternalFilesDir(mCaffeModelFileName);
            mCaffeModelProtoFile = context.getExternalFilesDir(mCaffeModelProtoFileName);
        }

        Mat inputFrame = new Mat();
        Imgproc.cvtColor(rgbaMat, inputFrame, Imgproc.COLOR_RGBA2RGB);
//        Imgproc.cvtColor(rgbaMat, inputFrame, Imgproc.COLOR_BGRA2RGB);
//        Imgproc.COLOR_BGRA2RGB;

        Mat blob = Dnn.blobFromImage(
                inputFrame,
                IN_SCALE_FACTOR,
                new Size(IN_WIDTH, IN_HEIGHT),
                new Scalar(MEAN_VAL, MEAN_VAL, MEAN_VAL),
                false
        );

        if (mNet == null || mNet.empty()) {
            mNet = Dnn.readNetFromCaffe(mCaffeModelProtoFile.getAbsolutePath(), mCaffeModelFile.getAbsolutePath());
//            mNet = Dnn.readNetFromCaffe(protoFile.getAbsolutePath(), modelFile.getAbsolutePath());
        }

        mNet.setInput(blob);
        Mat detections = mNet.forward();

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

        CameraFrameDetections cameraDetections = new CameraFrameDetections();
        for (int i = 0; i < detections.rows(); ++i) {
            double confidence = detections.get(i, 2)[0];
            if (confidence > THRESHOLD) {
                int classId = (int)detections.get(i, 1)[0];
                int xLeftBottom = (int)(detections.get(i, 3)[0] * cols);
                int yLeftBottom = (int)(detections.get(i, 4)[0] * rows);
                int xRightTop   = (int)(detections.get(i, 5)[0] * cols);
                int yRightTop   = (int)(detections.get(i, 6)[0] * rows);
                // Draw rectangle around detected object.
                Imgproc.rectangle(
                        subFrame,
                        new Point(xLeftBottom, yLeftBottom),
                        new Point(xRightTop, yRightTop),
                        new Scalar(0, 255, 0)
                );
                String label = mDetectedClassNames[classId] + ": " + confidence;
                Log.d("tag", "DETECTION: " + label);

                int[] baseLine = new int[1];
                Size labelSize = Imgproc.getTextSize(label, Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, 1, baseLine);
                // Draw background for label.
                Imgproc.rectangle(subFrame, new Point(xLeftBottom, yLeftBottom - labelSize.height),
                        new Point(xLeftBottom + labelSize.width, yLeftBottom + baseLine[0]),
                        new Scalar(255, 255, 255), Core.FILLED);
                // Write class name and confidence.
                Imgproc.putText(subFrame, label, new Point(xLeftBottom, yLeftBottom),
                        Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 0, 0));

                CameraFrameDetectionItem detectionItem = new CameraFrameDetectionItem(
                        classId, mDetectedClassNames[classId], confidence
                );
                cameraDetections.add(detectionItem);
            }
        }

        // create a temporary buffer
        MatOfByte buffer = new MatOfByte();
        // encode the frame in the buffer, according to the PNG format
        Imgcodecs.imencode(".jpg", subFrame, buffer);

        cameraDetections.setImageWithDetectionsBytes(buffer.toArray());
        return cameraDetections;
    }

    private static void copyDnnModelAssets(Context context) {
        Set<String> assetsToCopy = new HashSet<>();
        assetsToCopy.add(mCaffeModelFileName);
        assetsToCopy.add(mCaffeModelProtoFileName);

        AssetFilesHelper.copyAssets(context, assetsToCopy);
    }
}
