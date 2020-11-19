package com.attach.springboot.attach.common.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "attach.file")
public class FileProperties {
    private String defaultCollName;
    private final Map<String, String> collectionNames = new HashMap<>();

    public String getDefaultCollName() {
        return defaultCollName;
    }

    public void setDefaultCollName(String defaultCollName) {
        this.defaultCollName = defaultCollName;
    }

    public Map<String, String> getCollectionNames() {
        return collectionNames;
    }
}
