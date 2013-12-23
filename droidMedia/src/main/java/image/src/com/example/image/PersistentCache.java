package com.example.image;

import android.graphics.Bitmap;
import com.example.cache.MemCache;

public class PersistentCache extends MemCache<String, Bitmap> {
    //db: url, file link, date accessed, is_valid
}
