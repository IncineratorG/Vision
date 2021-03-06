package com.vision.services.camera.data.camera_frame_detections.item;


public class CameraFrameDetectionItem {
    private int mClassId = -1;
    private String mClassName = "";
    private double mConfidence = -1.0;

    public CameraFrameDetectionItem(int classId,
                                    String className,
                                    double confidence) {
        mClassId = classId;
        mClassName = className;
        mConfidence = confidence;
    }

    public boolean isEmpty() {
        return mClassId < 0;
    }

    public int classId() {
        return mClassId;
    }

    public String className() {
        return mClassName;
    }

    public double confidence() {
        return mConfidence;
    }
}
