package com.example.notifications.application.dto;

public record NotificationResult(boolean success, String provider, String messageId, String errorMessage) {
    public static NotificationResult success(String provider, String messageId) {
        return new NotificationResult(true, provider, messageId, null);
    }
    public static NotificationResult error(String provider, String errorMessage) {
        return new NotificationResult(false, provider, null, errorMessage);
    }
}
