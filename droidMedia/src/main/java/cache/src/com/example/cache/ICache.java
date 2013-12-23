package com.example.cache;

public interface ICache<KEY> {

    public void put(KEY key, ICacheEntry value);

    public ICacheEntry get(KEY key);

    public void invalidate(KEY key);

    public void clear();
}
