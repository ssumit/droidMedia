package com.example.image;

import android.graphics.Bitmap;
import com.example.cache.ICacheEntry;

public class ImageDiskCacheEntry implements ICacheEntry {
    private Bitmap _bitmap;

    public ImageDiskCacheEntry(Bitmap bitmap) {
        _bitmap = bitmap;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public int getSize() {
        if (_bitmap == null) {
            return 0;
        }
        else {
            return _bitmap.getHeight() * _bitmap.getWidth();
        }
    }

    public Bitmap getBitmap() {
        return _bitmap;
    }
}
