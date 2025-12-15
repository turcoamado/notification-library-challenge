package com.example.notifications.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NotificationMessageTest {

    @Test
    public void shouldFailWhenBuildingNotificationWithoutChannel() {
        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                new NotificationMessage.Builder()
                        .body("body")
                        .build()
        );

        assertEquals("Channel is required", ex.getMessage());
    }

    @Test
    public void shouldFailWhenBuildingWithoutBody() {
        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                new NotificationMessage.Builder()
                        .channel(Channel.EMAIL)
                        .build()
        );

        assertEquals("Body is required", ex.getMessage());
    }
}
