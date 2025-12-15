package com.example.notifications.application.service;

import com.example.notifications.application.dto.NotificationResult;
import com.example.notifications.application.port.in.SendNotificationUseCase;
import com.example.notifications.domain.model.NotificationMessage;
import com.example.notifications.domain.model.Recipient;
import com.example.notifications.application.port.out.NotificationSender;
import com.example.notifications.infrastructure.sender.SenderFactory;
import com.example.notifications.domain.util.ValidationUtils;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class NotificationService implements SendNotificationUseCase {
    private static final int MAX_RETRIES = 3;
    private final SenderFactory factory;

    @Override
    public NotificationResult send(String senderKey, NotificationMessage message, Recipient recipient) {
        ValidationUtils.validate(message, recipient);
        return sendWithRetry(senderKey, message, recipient);
    }

    @Override
    public CompletableFuture<NotificationResult> sendAsync(String senderKey, NotificationMessage message, Recipient recipient) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500); // Delay for testing purposes
            } catch (InterruptedException ignored) {}
            return send(senderKey, message, recipient);
        });
    }

    private NotificationResult sendWithRetry(String senderKey, NotificationMessage message, Recipient recipient) {
        int attempt = 0;
        NotificationResult result;

        do {
            attempt++;

            NotificationSender sender = factory.resolve(senderKey);

            try {
                result = sender.send(message, recipient);
            } catch (Exception e) {
                result = NotificationResult.error(sender.providerName(), e.getMessage());
            }

            if (result.success()) {
                return result;
            }

        } while (attempt <= MAX_RETRIES);

        return result;
    }

}
