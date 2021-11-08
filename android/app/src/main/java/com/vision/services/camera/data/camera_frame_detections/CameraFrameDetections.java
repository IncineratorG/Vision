package com.vision.services.camera.data.camera_frame_detections;


import com.vision.services.camera.data.camera_frame_detections.item.CameraFrameDetectionItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CameraFrameDetections {
    private String mId;
    private long mTimestamp;
    private List<CameraFrameDetectionItem> mDetections;
    private byte[] mImageWithDetectionsBytes = null;

    public CameraFrameDetections() {
        mTimestamp = System.currentTimeMillis();
        mId = UUID.randomUUID().toString() + "_" + mTimestamp;
        mDetections = new ArrayList<>();
    }

    public CameraFrameDetections(CameraFrameDetections other) {
        mTimestamp = other.mTimestamp;
        mId = other.mId;
        mDetections = new ArrayList<>(other.mDetections);
        if (other.mImageWithDetectionsBytes != null) {
            mImageWithDetectionsBytes = Arrays.copyOf(other.mImageWithDetectionsBytes, other.mImageWithDetectionsBytes.length);
        }
    }

    public String id() {
        return mId;
    }

    public long timestamp() {
        return mTimestamp;
    }

    public void add(CameraFrameDetectionItem detection) {
        mDetections.add(detection);
    }

    public boolean empty() {
        return mDetections.size() <= 0;
    }

    public void setImageWithDetectionsBytes(byte[] imageWithDetectionsBytes) {
        if (imageWithDetectionsBytes != null) {
            mImageWithDetectionsBytes = Arrays.copyOf(imageWithDetectionsBytes, imageWithDetectionsBytes.length);
        }
    }

    public List<CameraFrameDetectionItem> detections() {
        return mDetections;
    }

    public byte[] imageWithDetectionsBytes() {
        if (mImageWithDetectionsBytes == null) {
            return null;
        }

        return Arrays.copyOf(mImageWithDetectionsBytes, mImageWithDetectionsBytes.length);
    }
}
