package com.example.cache;

public class LRUCache<KEY> extends MemCache<KEY, ILRUCacheEntry> {

    @Override
    public void put(KEY key, ICacheEntry cacheEntry) {
        ILRUCacheEntry lruCacheEntry = getLRUEntry(cacheEntry);
        super.put(key, lruCacheEntry);
    }

    @Override
    public ICacheEntry get(KEY key) {
        ICacheEntry cacheEntry = super.get(key);
        if (cacheEntry != null && cacheEntry instanceof ILRUCacheEntry) {
            ((ILRUCacheEntry) cacheEntry).setUsedTime(System.currentTimeMillis());
        }
        return cacheEntry;
    }

    private ILRUCacheEntry getLRUEntry(final ICacheEntry cacheEntry) {
        return new ILRUCacheEntry() {
            private long _timeUsed = System.currentTimeMillis();
            private ICacheEntry _cacheEntry = cacheEntry;

            @Override
            public long getUsedTime() {
                return _timeUsed;
            }

            @Override
            public void setUsedTime(long time) {
                _timeUsed = time;
            }

            @Override
            public boolean isValid() {
                return _cacheEntry.isValid();
            }

            @Override
            public int getSize() {
                return _cacheEntry.getSize();
            }
        };
    }
}
