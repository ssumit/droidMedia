package com.example.image;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.example.cache.ICache;
import com.example.cache.ICacheEntry;
import com.example.cache.LRUCache;
import com.example.utils.Dispatcher;
import com.example.utils.IRequest;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is responsible for caching and persistence which is specific to images.
 * So, storing bitmaps(for android) is this class responsibility.
 * Scaling and compressing them is again it's responsibility (which will be delegated.).
 */
public class ImageRequestManager {

    private Dispatcher _dispatcher;
    private ICache<String> _cache;
    private ImageDiskCache _imageDiskCache;
    private Map<ImageView, ImageRequest> _imageViewLatestRequestMap;
    private static ImageRequestManager _imageRequestManager = null;

    private ImageRequestManager() {
        _dispatcher = new Dispatcher();
        _cache = new LRUCache<String>();
        _imageDiskCache = new ImageDiskCache();
        _imageViewLatestRequestMap = new ConcurrentHashMap<ImageView, ImageRequest>();
    }

    public void setCache(ICache<String> cache) {
        _cache = cache;
    }

    public static ImageRequestManager getInstance() {
        if (_imageRequestManager == null) {
            _imageRequestManager = new ImageRequestManager();
        }
        return _imageRequestManager;
    }

    public void addRequest(ImageRequest request) {
        String url = request.getUrl();
        ImageView imageView = request.getImageView();
        if (url == null) {
            _imageViewLatestRequestMap.remove(imageView);
            return;
        }
        _imageViewLatestRequestMap.put(imageView, request);
        ICacheEntry cacheEntry = _cache.get(url);
        if (cacheEntry != null) {
            request.getListener().onFinish(url, ((ImageDiskCacheEntry)cacheEntry).getBitmap());
        }
        else {
            cacheEntry = _imageDiskCache.get(url);
            if (cacheEntry != null) {
                Bitmap bitmap = ((ImageDiskCacheEntry)cacheEntry).getBitmap();
                _cache.put(url, cacheEntry);
                request.getListener().onFinish(url, bitmap);
            }
            else {
                preProcessRequest(request);
                _dispatcher.addRequest(request);
            }
        }
    }

    private void preProcessRequest(final ImageRequest request) {
        final IRequest.Listener listener = request.getListener();
        request.setListener(new IRequest.Listener<String>() {
            @Override
            public void onFinish(String url, String filePath) {
                //todo: do scaling, compressing, caching, persistence (delegate accordingly)
                if (listener != null) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = new ImageBitmapConverter().getBitmap(filePath);
                        ImageDiskCacheEntry cacheEntry = new ImageDiskCacheEntry(bitmap);
                        _cache.put(url, cacheEntry);
                        _imageDiskCache.put(url, cacheEntry);
                    } catch (IOException e) {
                        bitmap = null;
                    }
                    ImageView imageView = request.getImageView();
                    if (_imageViewLatestRequestMap.get(imageView).equals(request))
                    {
                        _imageViewLatestRequestMap.remove(imageView);
                        listener.onFinish(url, bitmap);
                    }
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
