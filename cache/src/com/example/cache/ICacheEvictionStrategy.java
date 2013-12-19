package com.example.cache;

import java.util.List;

public interface ICacheEvictionStrategy {

    public ICacheEntry getEvictedCacheEntry(List<ICacheEntry> cacheEntries);
}
