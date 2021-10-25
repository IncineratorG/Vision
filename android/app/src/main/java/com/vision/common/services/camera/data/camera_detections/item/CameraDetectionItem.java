package com.vision.common.services.camera.data.camera_detections.item;


public class CameraDetectionItem {
    private int mClassId = -1;
    private String mClassName = "";
    private double mConfidence = -1.0;

    public CameraDetectionItem(int classId,
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
