package com.example.cache;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class MemCache<KEY, T> implements ICache<KEY> {

    private final Map<KEY, ICacheEntry> _store;
    private ICacheEvictionStrategy<T> _cacheEvictionStrategy;
    private int CACHE_ENTRY_NUMBER_LIMIT = 100; //todo : to be injected
    private int CACHE_ENTRY_SIZE_LIMIT = 100; //todo : to be injected

    public MemCache() {
        _store = new ConcurrentHashMap<KEY, ICacheEntry>();
    }

    public void setCacheEvictionStrategy(ICacheEvictionStrategy<T> cacheEvictionStrategy) {
        _cacheEvictionStrategy = cacheEvictionStrategy;
    }

    @Override
    public void put(KEY key, ICacheEntry cacheEntry) {
        if (!_store.containsKey(key) && cacheEntry.getSize() < CACHE_ENTRY_SIZE_LIMIT) {
            if (_store.size() >= CACHE_ENTRY_NUMBER_LIMIT) {
                ICacheEntry evictedEntry = _cacheEvictionStrategy.getEvictedCacheEntry(new ArrayList(_store.values()));
                KEY keyToBeDeleted = getKey(evictedEntry);
                _store.remove(keyToBeDeleted);
            }
            _store.put(key, cacheEntry);
        }
    }

    private KEY getKey(ICacheEntry evictedEntry) {
        for (KEY key : _store.keySet()) {
            if (_store.get(key).equals(evictedEntry)) {
                return key;
            }
        }
        return null;
    }

    @Override
    public ICacheEntry get(KEY key) {
        return _store.get(key);
    }

    @Override
    public void invalidate(KEY key) {
        _store.remove(key);
    }

    @Override
    public void clear() {
        _store.clear();
    }
}
