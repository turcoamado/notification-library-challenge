package com.example.notifications.domain.util;

import com.example.notifications.domain.exception.NotificationException;
import com.example.notifications.domain.model.NotificationMessage;
import com.example.notifications.domain.model.Recipient;

public class ValidationUtils {

    private static final int DEVICE_TOKEN_LENGTH = 20;
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PHONE_REGEX = "^\\+[1-9][0-9]{7,14}$";

    public static void validate(NotificationMessage message, Recipient recipient) {

        switch (message.getChannel()) {
            case EMAIL:
                if (!isValidEmail(recipient.to())) {
                    throw new NotificationException("Invalid email");
                }
                if (message.getSubject() == null || message.getSubject().isBlank()) {
                    throw new NotificationException("Email subject required");
                }
                break;
            case SMS:
                if (!isValidPhone(recipient.to())) {
                    throw new NotificationException("Invalid phone");
                }
                break;
            case PUSH:
                if (recipient.to().length() < DEVICE_TOKEN_LENGTH) {
                    throw new NotificationException("Invalid device token");
                }
                break;
            default: break;
        }
    }

    public static boolean isValidEmail(String e) {
        return e != null && e.matches(EMAIL_REGEX);
    }

    public static boolean isValidPhone(String p) {
        return p != null && p.matches(PHONE_REGEX);
    }

}
