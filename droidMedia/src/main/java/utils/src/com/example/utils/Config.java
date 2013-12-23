package com.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class Config {
    private Properties _properties = new Properties();

    public void loadConfig(List<String> propertiesFiles) throws IOException {
        for (String propertiesFile : propertiesFiles) {
            InputStream inputStream = getClass().getResourceAsStream(propertiesFile);
            _properties.load(inputStream);
            inputStream.close();
        }
    }

    public String getString(String key) {
        return _properties.getProperty(key);
    }

    public boolean getBoolean(String key) {
        return Boolean.valueOf(_properties.getProperty(key));
    }

    public int getInteger(String key) {
        return Integer.valueOf(_properties.getProperty(key));
    }
}
