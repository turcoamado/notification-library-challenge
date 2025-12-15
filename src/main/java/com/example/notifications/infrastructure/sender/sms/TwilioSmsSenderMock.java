package com.example.notifications.infrastructure.sender.sms;

import com.example.notifications.application.dto.NotificationResult;
import com.example.notifications.domain.model.NotificationMessage;
import com.example.notifications.domain.model.Recipient;
import com.example.notifications.application.port.out.NotificationSender;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TwilioSmsSenderMock implements NotificationSender {
    private final String sid;
    private final String token;

    @Override
    public NotificationResult send(NotificationMessage message, Recipient recipient) {
        System.out.println("[TwilioMock] Sending SMS to " + recipient.to() + " body=" + message.getBody());
        if (sid == null || sid.isBlank() || token == null || token.isBlank()) {
            return NotificationResult.error(providerName(), "Missing credentials");
        }
        String msgId = "tw-" + System.currentTimeMillis();
        return NotificationResult.success(providerName(), msgId);
    }

    @Override
    public String providerName() {
        return "TwilioMock";
    }
}
