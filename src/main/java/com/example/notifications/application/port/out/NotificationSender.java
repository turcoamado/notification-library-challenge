package com.example.notifications.application.port.out;

import com.example.notifications.application.dto.NotificationResult;
import com.example.notifications.domain.model.NotificationMessage;
import com.example.notifications.domain.model.Recipient;

public interface NotificationSender {
    NotificationResult send(NotificationMessage message, Recipient recipient);
    String providerName();
}
