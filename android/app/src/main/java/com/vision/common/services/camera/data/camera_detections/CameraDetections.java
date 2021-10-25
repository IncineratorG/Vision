package com.vision.common.services.camera.data.camera_detections;


import com.vision.common.services.camera.data.camera_detections.item.CameraDetectionItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CameraDetections {
    private List<CameraDetectionItem> mDetections;
    private byte[] mImageWithDetectionsBytes = null;

    public CameraDetections() {
        mDetections = new ArrayList<>();
    }

    public CameraDetections(CameraDetections other) {
        mDetections = new ArrayList<>(other.mDetections);
        if (other.mImageWithDetectionsBytes != null) {
            mImageWithDetectionsBytes = Arrays.copyOf(other.mImageWithDetectionsBytes, other.mImageWithDetectionsBytes.length);
        }
    }

    public void add(CameraDetectionItem detection) {
        mDetections.add(detection);
    }

    public void setImageWithDetectionsBytes(byte[] imageWithDetectionsBytes) {
        if (imageWithDetectionsBytes != null) {
            mImageWithDetectionsBytes = Arrays.copyOf(imageWithDetectionsBytes, imageWithDetectionsBytes.length);
        }
    }

    public List<CameraDetectionItem> detections() {
        return mDetections;
    }

    public byte[] imageWithDetectionsBytes() {
        if (mImageWithDetectionsBytes == null) {
            return null;
        }

        return Arrays.copyOf(mImageWithDetectionsBytes, mImageWithDetectionsBytes.length);
    }
}
