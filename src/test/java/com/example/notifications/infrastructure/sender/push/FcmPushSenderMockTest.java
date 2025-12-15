package com.example.notifications.infrastructure.sender.push;

import com.example.notifications.domain.model.Channel;
import com.example.notifications.domain.model.NotificationMessage;
import com.example.notifications.domain.model.Recipient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FcmPushSenderMockTest {
    private static NotificationMessage push;
    private static final String SERVER_KEY = "server-key";
    private static final String DEVICE_TOKEN = "device-token-1234567";

    @BeforeAll
    public static void setup() {
        push = new NotificationMessage.Builder()
                .channel(Channel.PUSH)
                .body("This is a push notification for testing purposes.")
                .build();
    }

    @Test
    void shouldSendPushNotificationSuccessfully() {
        var sender = new FcmPushSenderMock(SERVER_KEY);

        var result = sender.send(push, new Recipient(DEVICE_TOKEN));

        assertTrue(result.success());
        assertNotNull(result.messageId());
        assertEquals("FcmMock", result.provider());
    }

    @Test
    public void shouldFailWhenServerKeyIsMissing() {
        var sender = new FcmPushSenderMock("");

        var result = sender.send(push, new Recipient(DEVICE_TOKEN));

        assertFalse(result.success());
        assertEquals("Missing server key", result.errorMessage());
    }
}
