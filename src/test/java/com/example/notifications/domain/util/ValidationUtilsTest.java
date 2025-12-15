package com.example.notifications.domain.util;

import com.example.notifications.domain.exception.NotificationException;
import com.example.notifications.domain.model.Channel;
import com.example.notifications.domain.model.NotificationMessage;
import com.example.notifications.domain.model.Recipient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidationUtilsTest {

    @Test
    public void emailNotificationShouldFailWhenEmailIsInvalid() {
        NotificationMessage email = new NotificationMessage.Builder()
                .channel(Channel.EMAIL)
                .subject("Test email")
                .body("This is an email for testing purposes.")
                .build();

        NotificationException ex = assertThrows(NotificationException.class, () ->
                ValidationUtils.validate(email, new Recipient("invalidmail.com"))
        );
        assertEquals("Invalid email", ex.getMessage());
    }

    @Test
    public void emailNotificationShouldFailWhenEmailIsWithoutSubject() {
        NotificationMessage email = new NotificationMessage.Builder()
                .channel(Channel.EMAIL)
                .body("This is an email for testing purposes.")
                .build();

        NotificationException ex = assertThrows(NotificationException.class, () ->
                ValidationUtils.validate(email, new Recipient("user@example.com"))
        );
        assertEquals("Email subject required", ex.getMessage());
    }

    @Test
    public void smsNotificationShouldFailWhenPhoneIsInvalid() {
        NotificationMessage sms = new NotificationMessage.Builder()
                .channel(Channel.SMS)
                .body("This is a sms for testing purposes.")
                .build();

        NotificationException ex = assertThrows(NotificationException.class, () ->
                ValidationUtils.validate(sms, new Recipient("123asdf234"))
        );
        assertEquals("Invalid phone", ex.getMessage());
    }

    @Test
    public void pushNotificationShouldFailWhenTokenDeviceIsInvalid() {
        NotificationMessage push = new NotificationMessage.Builder()
                .channel(Channel.PUSH)
                .body("This is a push notification for testing purposes.")
                .build();

        NotificationException ex = assertThrows(NotificationException.class, () ->
                ValidationUtils.validate(push, new Recipient("1234"))
        );
        assertEquals("Invalid device token", ex.getMessage());
    }

}
