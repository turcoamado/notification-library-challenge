package com.example.notifications.infrastructure.sender.email;
import com.example.notifications.domain.model.Channel;
import com.example.notifications.domain.model.NotificationMessage;
import com.example.notifications.domain.model.Recipient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SendGridEmailSenderMockTest {

    private static NotificationMessage email;
    private static final String API_KEY = "SG.fake-api-key";

    @BeforeAll
    public static void setup() {
        email = new NotificationMessage.Builder()
                .channel(Channel.EMAIL)
                .subject("Test email")
                .body("This is an email for testing purposes.")
                .build();
    }

    @Test
    public void shouldSendEmailSuccessfullyWhenApiKeyIsPresent() {
        var sender = new SendGridEmailSenderMock(API_KEY);

        var result = sender.send(email, new Recipient("user@example.com"));

        assertTrue(result.success());
        assertEquals("SendGridMock", result.provider());
        assertNotNull(result.messageId());
    }

    @Test
    public void shouldFailWhenApiKeyIsMissing() {
        var sender = new SendGridEmailSenderMock("");

        var result = sender.send(email, new Recipient("user@example.com"));

        assertFalse(result.success());
        assertEquals("Missing api key", result.errorMessage());
    }
}
