package com.example.cache;

public interface ILRUCacheEntry extends ICacheEntry{

    public long getUsedTime();

    public void setUsedTime(long timeInMillis);
}
