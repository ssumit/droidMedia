package com.example.cache;

import java.util.List;

public interface ICacheEvictionStrategy<T> {

    public T getEvictedCacheEntry(List<T> cacheEntries);
}
