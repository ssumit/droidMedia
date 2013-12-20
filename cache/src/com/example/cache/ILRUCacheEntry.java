package com.example.cache;

public interface ILRUCacheEntry extends ICacheEntry{

    public int getUsedTime();

    public void setUsedTime();
}
