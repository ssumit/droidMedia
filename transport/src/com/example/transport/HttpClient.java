package com.example.transport;

import android.net.Uri;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient implements IClient{

    private String STORAGE_PATH = "/";

    @Override
    public String download(Uri path) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(path.toString()).openConnection();
        //todo: set timeouts, read header, response code
        int responseCode = connection.getResponseCode();
        InputStream inputStream = connection.getInputStream();
        String filePath = getFilePath(path);
        OutputStream outputStream = new FileOutputStream(filePath);
        writeFromInputToOutputStream(inputStream, outputStream);
        return filePath;
    }

    private void writeFromInputToOutputStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        int read = 0;
        byte[] bytes = new byte[1024];
        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
    }

    private String getFilePath(Uri path) {
        return STORAGE_PATH + path.hashCode();
    }
}
