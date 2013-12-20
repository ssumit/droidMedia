package com.example.cache;

import java.util.List;

public interface ICacheEvictionStrategy<T> {

    public ICacheEntry getEvictedCacheEntry(List<T> cacheEntries);
}
