import java.util.ArrayList;
import java.util.List;

public class Dispatcher {

    private List<IRequest> _pendingRequests;
    private List<IRequest> _currentRequests;
    private ISchedulingPolicy _schedulingPolicy;
    private int MAX_CONCURRENT_REQUEST;
    private IRequest.Listener _listener;

    public Dispatcher() {
        _pendingRequests = new ArrayList<IRequest>();
        _currentRequests = new ArrayList<IRequest>();
        _listener = getListener();
        _schedulingPolicy = null;
        MAX_CONCURRENT_REQUEST = 50; //todo read from properties file
    }

    public void setRetryPolicy(ISchedulingPolicy retryPolicy) {
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

    public IRequest.Listener getListener() {
        return new IRequest.Listener() {
            @Override
            public void onFinish() {
                firePendingRequests();
            }
        };
    }

    private void firePendingRequests() {
        while (_currentRequests.size() < MAX_CONCURRENT_REQUEST && _pendingRequests.size() > 0) {
            IRequest request = _schedulingPolicy.getNextRequest(_pendingRequests);
            fireRequest(request);
        }
    }
}
