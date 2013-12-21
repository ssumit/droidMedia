package com.example.image;

import android.widget.ImageView;
import com.example.utils.IRequest;

public class ImageRequest implements IRequest {

    private String _url;
    private ImageView _imageView;
    private int _drawableId = -1;
    private Listener _listener;
    private ImageRequestManager _requestManager;

    public ImageRequest() {
        _requestManager = ImageRequestManager.getInstance();
    }

    public ImageRequest fetch(String url) {
        _url = url;
        return this;
    }

    public ImageRequest showInto(ImageView imageView) {
        _imageView = imageView;
        return this;
    }

    public ImageRequest setPlaceHolder(int drawableId) {
        _drawableId = drawableId;
        return this;
    }

    public void execute() {
        setPlaceHolderIntoImageView();
        if (_url != null) {
            _requestManager.addRequest(this);
        }
    }

    private void setPlaceHolderIntoImageView() {
        if (_imageView != null && _drawableId != -1) {
            _imageView.setImageResource(_drawableId);
        }
    }

    @Override
    public void cancel() {
        _requestManager.cancelRequest(this);
    }

    @Override
    public void setListener(Listener listener) {
        _listener = listener;
    }

    @Override
    public Listener getListener() {
        return _listener;
    }
}
