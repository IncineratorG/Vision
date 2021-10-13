package com.vision.common.services.camera;


import java.util.Arrays;

public class CameraPreviewImageData {
    private byte[] mPreviewImageBytes = null;
    private int mPreviewImageWidth = -1;
    private int mPreviewImageHeight = -1;
    private int mPreviewImageFormat = -1;

    public CameraPreviewImageData(byte[] imageBytes,
                                  int imageWidth,
                                  int imageHeight,
                                  int imageFormat) {
        mPreviewImageBytes = Arrays.copyOf(imageBytes, imageBytes.length);
        mPreviewImageWidth = imageWidth;
        mPreviewImageHeight = imageHeight;
        mPreviewImageFormat = imageFormat;
    }

    public CameraPreviewImageData(CameraPreviewImageData other) {
        if (other.mPreviewImageBytes != null) {
            mPreviewImageBytes = Arrays.copyOf(other.mPreviewImageBytes, other.mPreviewImageBytes.length);
        }
        mPreviewImageWidth = other.mPreviewImageWidth;
        mPreviewImageHeight = other.mPreviewImageHeight;
        mPreviewImageFormat = other.mPreviewImageFormat;
    }

    public boolean hasImage() {
        return mPreviewImageBytes != null;
    }

    public byte[] imageBytes() {
        if (mPreviewImageBytes == null) {
            return null;
        }

        return Arrays.copyOf(mPreviewImageBytes, mPreviewImageBytes.length);
    }

    public int width() {
        return mPreviewImageWidth;
    }

    public int height() {
        return mPreviewImageHeight;
    }

    public int imageFormat() {
        return mPreviewImageFormat;
    }
}
