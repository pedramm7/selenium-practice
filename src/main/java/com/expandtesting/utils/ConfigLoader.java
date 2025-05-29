package com.expandtesting.utils;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigLoader {
    private static JsonNode config;

    static {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Adjust file path to match your actual location
            File configFile = new File("config/testdata.json");
            if (!configFile.exists()) {
                throw new RuntimeException("Configuration file not found: " + configFile.getAbsolutePath());
            }
            config = mapper.readTree(configFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }

    public static JsonNode getConfig() {
        return config;
    }
}
