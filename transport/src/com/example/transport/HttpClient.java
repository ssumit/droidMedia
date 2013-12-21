package com.example.transport;

import android.net.Uri;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    private String STORAGE_PATH = "";

    public void Download(Uri path) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(path.toString()).openConnection();
        //todo: set timeouts, read header, response code
        int responseCode = connection.getResponseCode();
        InputStream inputStream = connection.getInputStream();
        OutputStream outputStream = new FileOutputStream();
    }
}
