package com.example.utils;

import java.util.List;

public interface ISchedulingPolicy {

    public IRequest getNextRequest(List<IRequest> requests);
}
