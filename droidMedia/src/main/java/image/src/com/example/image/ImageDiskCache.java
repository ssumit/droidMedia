package com.example.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.cache.ICache;
import com.example.cache.ICacheEntry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Caches bitmaps on disk.
 */
public class ImageDiskCache implements ICache<String> {
    private static final int COMPRESSION_RATIO = 90; //todo inject

    @Override
    public void put(String url, ICacheEntry value) {
        if (value instanceof ImageDiskCacheEntry) {
            Bitmap avatar = ((ImageDiskCacheEntry)value).getBitmap();
            if (avatar != null)
            {
                String filename = convertToFilename(url);
                File file = new File(filename);
                try
                {
                    FileOutputStream outputStream = new FileOutputStream(file);
                    avatar.compress(Bitmap.CompressFormat.JPEG, COMPRESSION_RATIO, outputStream);
                    outputStream.flush();
                    outputStream.close();
                }
                catch (IOException ignored) {}
            }
        }
    }

    @Override
    public ICacheEntry get(String url) {
        String filename = convertToFilename(url);
        File file = new File(filename);
        if (file.exists())
        {

            long size = file.length();
            byte[] avatarByteArray = new byte[(int) size];
            try
            {
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(avatarByteArray);
                fileInputStream.close();
                return new ImageDiskCacheEntry(BitmapFactory.decodeByteArray(avatarByteArray, 0, avatarByteArray.length));
            }
            catch (IOException ignored) {}
        }
        return null;
    }

    @Override
    public void invalidate(String s) {
    }

    @Override
    public void clear() {
    }

    private String convertToFilename(String url) {
        //todo: db support? more robust translation
        return String.valueOf(url.hashCode());
    }
}
