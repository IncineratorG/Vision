package com.vision.services.camera.helpers;


import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class CameraHelper {
    public static String yuvToBase64(byte[] yuvBytes, int imageFormat, int width, int height) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        YuvImage yuvImage = new YuvImage(yuvBytes, imageFormat, width, height, null);
        yuvImage.compressToJpeg(new Rect(0, 0, width, height), 50, out);
        byte[] imageBytes = out.toByteArray();

        //        Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        //        iv.setImageBitmap(image);

        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}
