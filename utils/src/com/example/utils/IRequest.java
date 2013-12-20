package com.example.utils;

/**
 * It is important to implement equals and hash code
 */

public interface IRequest {

    public void execute();

    public void cancel();

    public void setListener(Listener listener);

    public Listener getListener();

    public interface Listener {
        /**
         * Called when request either successfully completes or cancelled.
         */
        public void onFinish();

        public void progress(String url, long dataDownloaded, long totalSize);
    }
}
