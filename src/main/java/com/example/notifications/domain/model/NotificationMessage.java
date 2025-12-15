package com.example.notifications.domain.model;

import lombok.Getter;

@Getter
public final class NotificationMessage {
    private final Channel channel;
    private final String subject;
    private final String body;

    private NotificationMessage(Builder b) {
        this.channel = b.channel;
        this.subject = b.subject;
        this.body = b.body;
    }

    public static class Builder {
        private Channel channel;
        private String subject;
        private String body;

        public Builder channel(Channel channel) { this.channel = channel; return this; }
        public Builder subject(String subject) { this.subject = subject; return this; }
        public Builder body(String body) { this.body = body; return this; }

        public NotificationMessage build() {
            if (channel == null) throw new IllegalStateException("Channel is required");
            if (body == null || body.isBlank()) throw new IllegalStateException("Body is required");
            return new NotificationMessage(this);
        }
    }
}
