package com.pinapp.notifications.domain.model;

public record Recipient(String to) {
    public Recipient {
        if (to == null || to.isBlank()) {
            throw new IllegalArgumentException("Recipient is required");
        }
    }
}
