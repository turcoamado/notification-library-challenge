package com.pinapp.notifications.application.port.out;

import com.pinapp.notifications.application.dto.NotificationResult;
import com.pinapp.notifications.domain.model.NotificationMessage;
import com.pinapp.notifications.domain.model.Recipient;

public interface NotificationSender {
    NotificationResult send(NotificationMessage message, Recipient recipient);
    String providerName();
}
