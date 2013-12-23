package com.example.cache;

import java.util.List;

public class LRUCacheEviction implements ICacheEvictionStrategy<ILRUCacheEntry> {

    @Override
    public ILRUCacheEntry getEvictedCacheEntry(List<ILRUCacheEntry> cacheEntries) {
        ILRUCacheEntry leastRecentlyUsedEntry = null;
        for (ILRUCacheEntry cacheEntry : cacheEntries) {
            if (!cacheEntry.isValid()) {
                return cacheEntry;
            }
            else {
                if (leastRecentlyUsedEntry == null) {
                    leastRecentlyUsedEntry = cacheEntry;
                }
                else {
                    if (leastRecentlyUsedEntry.getUsedTime() > cacheEntry.getUsedTime()) {
                        leastRecentlyUsedEntry = cacheEntry;
                    }
                }
            }
        }

        return leastRecentlyUsedEntry;
    }
}
