package com.example.image;

import com.example.cache.ICache;
import com.example.cache.LRUCache;
import com.example.utils.Dispatcher;
import com.example.utils.IRequest;

/**
 * This class is responsible for caching and persistence which is specific to images.
 * So, storing bitmaps(for android) is this class responsibility.
 * Scaling and compressing them is again it's responsibility (which will be delegated.).
 */
public class ImageRequestManager {

    private Dispatcher _dispatcher;
    private ICache _cache;
    private static ImageRequestManager _imageRequestManager = null;

    private ImageRequestManager() {
        _dispatcher = new Dispatcher();
        _cache = new LRUCache();
    }

    public void setCache(ICache cache) {
        _cache = cache;
    }

    public static ImageRequestManager getInstance() {
        if (_imageRequestManager == null) {
            _imageRequestManager = new ImageRequestManager();
        }
        return _imageRequestManager;
    }

    public void addRequest(ImageRequest request) {
        preProcessRequest(request);
        //todo: see if the request has already been served
        _dispatcher.addRequest(request);
    }

    private void preProcessRequest(ImageRequest request) {
        final IRequest.Listener listener = request.getListener();
        request.setListener(new IRequest.Listener() {
            @Override
            public void onFinish(String url, Object file) {
                //todo: do scaling, compressing, caching, persistence (delegate accordingly)
                if (listener != null) {
                    listener.onFinish(url, file);
                }
            }

            @Override
            public void progress(String url, long dataDownloaded, long totalSize) {
                if (listener != null) {
                    listener.progress(url, dataDownloaded, totalSize);
                }
            }
        });
    }

    public void cancelRequest(ImageRequest request) {
        _dispatcher.cancelRequest(request);
    }
}
