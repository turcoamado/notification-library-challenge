package com.example.notifications.application.port.in;

import com.example.notifications.application.dto.NotificationResult;
import com.example.notifications.domain.model.NotificationMessage;
import com.example.notifications.domain.model.Recipient;

import java.util.concurrent.CompletableFuture;

public interface SendNotificationUseCase {
    NotificationResult send(String senderKey, NotificationMessage message, Recipient recipient);
    CompletableFuture<NotificationResult> sendAsync(String senderKey, NotificationMessage message, Recipient recipient);
}
