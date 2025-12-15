package com.example.notifications.infrastructure.sender.email;

import com.example.notifications.application.dto.NotificationResult;
import com.example.notifications.domain.model.NotificationMessage;
import com.example.notifications.domain.model.Recipient;
import com.example.notifications.application.port.out.NotificationSender;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendGridEmailSenderMock implements NotificationSender {
    private final String apiKey;

    @Override
    public NotificationResult send(NotificationMessage message, Recipient recipient) {
        System.out.println("[SendGridMock] Sending email to " + recipient.to() + " subject=" + message.getSubject());
        if (apiKey == null || apiKey.isBlank()) {
            return NotificationResult.error(providerName(), "Missing api key");
        }
        String msgId = "sg-" + System.currentTimeMillis();
        return NotificationResult.success(providerName(), msgId);
    }

    @Override
    public String providerName() {
        return "SendGridMock";
    }
}
