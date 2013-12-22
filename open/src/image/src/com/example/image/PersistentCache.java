package com.example.image;

import com.example.cache.MemCache;

public abstract class PersistentCache<KEY, T> extends MemCache<KEY, T> {
    //db: url, file link, date accessed, is_valid
}
