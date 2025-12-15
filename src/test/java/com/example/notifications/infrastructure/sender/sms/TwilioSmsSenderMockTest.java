package com.example.notifications.infrastructure.sender.sms;

import com.example.notifications.domain.model.Channel;
import com.example.notifications.domain.model.NotificationMessage;
import com.example.notifications.domain.model.Recipient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TwilioSmsSenderMockTest {

    private static NotificationMessage sms;
    private static final String PHONE_NUMBER = "+5491112345678";
    private static final String SID = "AC1234567890abcdef1234567890abcd";
    private static final String TOKEN = "a1b2c3d4e5f678901234567890abcdef";

    @BeforeAll
    public static void setup() {
        sms = new NotificationMessage.Builder()
                .channel(Channel.SMS)
                .body("This is a sms for testing purposes.")
                .build();
    }

    @Test
    public void shouldSendSmsSuccessfullyWhenCredentialsArePresent() {
        var sender = new TwilioSmsSenderMock(SID, TOKEN);

        var result = sender.send(sms, new Recipient(PHONE_NUMBER));

        assertTrue(result.success());
        assertNotNull(result.messageId());
    }

    @Test
    public void shouldFailWhenSidIsMissing() {
        var sender = new TwilioSmsSenderMock(null, TOKEN);

        var result = sender.send(sms, new Recipient(PHONE_NUMBER));

        assertFalse(result.success());
        assertEquals("Missing credentials", result.errorMessage());
    }

    @Test
    void shouldFailWhenTwilioCredentialsAreMissing() {
        var sender = new TwilioSmsSenderMock("", " ");

        var result = sender.send(sms, new Recipient(PHONE_NUMBER));

        assertFalse(result.success());
        assertEquals("TwilioMock", result.provider());
        assertEquals("Missing credentials", result.errorMessage());
    }
}
