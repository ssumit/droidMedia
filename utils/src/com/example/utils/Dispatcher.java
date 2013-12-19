package com.example.utils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Dispatcher {

    private final List<IRequest> _pendingRequests;
    private final List<IRequest> _currentRequests;
    private ISchedulingPolicy _schedulingPolicy;
    private int MAX_CONCURRENT_REQUEST;
    private IRequest.Listener _listener;

    public Dispatcher() {
        _pendingRequests = new CopyOnWriteArrayList<IRequest>();
        _currentRequests = new CopyOnWriteArrayList<IRequest>();
        _listener = getListener();
        _schedulingPolicy = getSchedulingPolicy();
        MAX_CONCURRENT_REQUEST = 50; //todo read from properties file
    }

    public void setSchedulingPolicy(ISchedulingPolicy retryPolicy) {
        _schedulingPolicy = retryPolicy;
    }

    //todo: thread safety?
    public void addRequest(IRequest request) {
        if (_currentRequests.size() < MAX_CONCURRENT_REQUEST) {
            if (!_currentRequests.contains(request)) {
                fireRequest(request);
            }
        } else {
            _pendingRequests.add(request);
        }
    }

    private void fireRequest(IRequest request) {
        _currentRequests.add(request);
        request.addListener(_listener);
        request.execute();
    }

    public boolean cancelRequest(IRequest request) {
        if (_pendingRequests.contains(request)) {
            _pendingRequests.remove(request);
            return true;
        }
        return false;
    }

    private IRequest.Listener getListener() {
        return new IRequest.Listener() {
            @Override
            public void onFinish() {
                firePendingRequests();
            }

            @Override
            public void progress(String url, long dataDownloaded, long totalSize) {
            }
        };
    }

    private void firePendingRequests() {
        while (_currentRequests.size() < MAX_CONCURRENT_REQUEST && _pendingRequests.size() > 0) {
            IRequest request = _schedulingPolicy.getNextRequest(_pendingRequests);
            fireRequest(request);
        }
    }

    private ISchedulingPolicy getSchedulingPolicy() {
        return new ISchedulingPolicy() {
            @Override
            public IRequest getNextRequest(List<IRequest> requests) {
                if (requests == null || requests.size() == 0) {
                    return null;
                }
                else {
                    return requests.get(0);
                }
            }
        };
    }
}
