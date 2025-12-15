package com.example.notifications.domain.model;

import com.example.notifications.domain.exception.NotificationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NotificationMessageTest {

    @Test
    public void shouldFailWhenBuildingNotificationWithoutChannel() {
        NotificationException ex = assertThrows(NotificationException.class, () ->
                new NotificationMessage.Builder()
                        .body("body")
                        .build()
        );

        assertEquals("Channel is required", ex.getMessage());
    }

    @Test
    public void shouldFailWhenBuildingWithoutBody() {
        NotificationException ex = assertThrows(NotificationException.class, () ->
                new NotificationMessage.Builder()
                        .channel(Channel.EMAIL)
                        .build()
        );

        assertEquals("Body is required", ex.getMessage());
    }
}
