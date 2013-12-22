package com.example.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageBitmapReader {

    private int _height = 100;//todo inject
    private int _width = 100;

    public Bitmap getBitmap(String filePath) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        return getScaledBitmap(inputStream);
    }

    private Bitmap getScaledBitmap(InputStream inputStream) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        readDimensionsWithoutLoading(inputStream, options);
        return scaleAndReadImage(inputStream, options);
    }

    private Bitmap scaleAndReadImage(InputStream inputStream, BitmapFactory.Options options) throws IOException {
        options.inSampleSize = getInSampleSize(options, _height, _width);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    private void readDimensionsWithoutLoading(InputStream inputStream, BitmapFactory.Options options) throws IOException {
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
    }

    private int getInSampleSize(BitmapFactory.Options options, int reqHeight, int reqWidth) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round((float) height / (float) reqHeight);
            int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

}
