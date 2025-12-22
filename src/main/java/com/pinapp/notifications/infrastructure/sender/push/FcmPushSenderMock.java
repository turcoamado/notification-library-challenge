package com.pinapp.notifications.infrastructure.sender.push;

import com.pinapp.notifications.application.dto.NotificationResult;
import com.pinapp.notifications.domain.model.NotificationMessage;
import com.pinapp.notifications.domain.model.Recipient;
import com.pinapp.notifications.application.port.out.NotificationSender;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FcmPushSenderMock implements NotificationSender {
    private final String serverKey;

    @Override
    public NotificationResult send(NotificationMessage message, Recipient recipient) {
        System.out.println("[FcmMock] Sending push to " + recipient.to() + " body=" + message.getBody());
        if (serverKey == null || serverKey.isBlank()) {
            return NotificationResult.error(providerName(), "Missing server key");
        }
        String msgId = "fcm-" + System.currentTimeMillis();
        return NotificationResult.success(providerName(), msgId);
    }

    @Override
    public String providerName() {
        return "FcmMock";
    }
}
