package com.example.cache;

public interface ICacheEntry {

    /**
     * This will be particular to the consumer using this cache. If false, these entries should be evicted first.
     */
    public boolean isValid();

    public int getSize();

}
