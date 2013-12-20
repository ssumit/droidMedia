package com.example.utils;

import java.util.List;

public class FCFSSchedulingPolicy implements ISchedulingPolicy{
    @Override
    public IRequest getNextRequest(List<IRequest> requests) {
        if (requests == null || requests.size() == 0) {
            return null;
        }
        else {
            return requests.get(0);
        }
    }
}
