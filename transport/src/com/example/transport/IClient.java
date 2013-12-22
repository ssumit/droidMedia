package com.example.transport;

import android.net.Uri;

import java.io.IOException;

public interface IClient {
    public String download(Uri path) throws IOException;
}
