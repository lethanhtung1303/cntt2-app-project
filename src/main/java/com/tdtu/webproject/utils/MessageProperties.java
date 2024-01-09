package com.tdtu.webproject.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

public class MessageProperties {
    private final Properties configProp = new Properties();

    private MessageProperties() {
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("messages.properties")) {
            configProp.load(new InputStreamReader(in, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MessageProperties getInstance() {
        return MessagePropertiesHolder.INSTANCE;
    }

    public String getProperty(String key) {
        return configProp.getProperty(key);
    }

    public String getProperty(String key, String... args) {
        return MessageFormat.format(configProp.getProperty(key), (Object[]) args);
    }

    public String getProperty(String key, List<String> args) {
        return MessageFormat.format(configProp.getProperty(key), args.toArray(new Object[0]));
    }

    private static class MessagePropertiesHolder {
        private static final MessageProperties INSTANCE = new MessageProperties();
    }
}
