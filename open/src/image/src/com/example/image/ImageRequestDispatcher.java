package com.example.image;

import com.example.utils.Dispatcher;

public class ImageRequestDispatcher {

    private Dispatcher _dispatcher;
    private static ImageRequestDispatcher _imageRequestDispatcher = null;

    private ImageRequestDispatcher() {
        _dispatcher = new Dispatcher();
    }

    public static ImageRequestDispatcher getInstance() {
        if (_imageRequestDispatcher == null) {
            _imageRequestDispatcher = new ImageRequestDispatcher();
        }
        return _imageRequestDispatcher;
    }

    public void addRequest(ImageRequest request) {
        _dispatcher.addRequest(request);
    }

    public void cancelRequest(ImageRequest request) {
        _dispatcher.cancelRequest(request);
    }
}
