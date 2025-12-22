package com.pinapp.notifications.infrastructure.sender;

import com.pinapp.notifications.application.port.out.NotificationSender;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class SenderFactory {
    private final Map<String, NotificationSender> registry = new ConcurrentHashMap<>();

    public void register(String key, NotificationSender sender) {
        registry.put(key, sender);
    }

    public NotificationSender resolve(String key) {
        var s = registry.get(key);

        if (s == null) {
            throw new IllegalArgumentException("No sender registered for: " + key);
        }

        return s;
    }
}
