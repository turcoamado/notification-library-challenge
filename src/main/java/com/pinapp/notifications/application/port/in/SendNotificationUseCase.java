package com.pinapp.notifications.application.port.in;

import com.pinapp.notifications.application.dto.NotificationResult;
import com.pinapp.notifications.domain.model.NotificationMessage;
import com.pinapp.notifications.domain.model.Recipient;

import java.util.concurrent.CompletableFuture;

public interface SendNotificationUseCase {
    NotificationResult send(String senderKey, NotificationMessage message, Recipient recipient);
    CompletableFuture<NotificationResult> sendAsync(String senderKey, NotificationMessage message, Recipient recipient);
}
