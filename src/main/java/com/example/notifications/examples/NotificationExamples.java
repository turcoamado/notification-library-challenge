package com.example.notifications.examples;

import com.example.notifications.application.port.in.SendNotificationUseCase;
import com.example.notifications.application.service.NotificationService;
import com.example.notifications.infrastructure.sender.SenderFactory;
import com.example.notifications.infrastructure.sender.email.SendGridEmailSenderMock;
import com.example.notifications.infrastructure.sender.sms.TwilioSmsSenderMock;
import com.example.notifications.infrastructure.sender.push.FcmPushSenderMock;
import com.example.notifications.domain.model.Channel;
import com.example.notifications.domain.model.NotificationMessage;
import com.example.notifications.domain.model.Recipient;

public class NotificationExamples {
    public static void main(String[] args) throws Exception {
        SenderFactory factory = new SenderFactory();
        factory.register("email:sendgrid", new SendGridEmailSenderMock("fake-api-key"));
        factory.register("sms:twilio", new TwilioSmsSenderMock("sid","token"));
        factory.register("push:fcm", new FcmPushSenderMock("fcm-key"));

        SendNotificationUseCase service = new NotificationService(factory);

        /**
         * Email notification
         */
        var email = new NotificationMessage.Builder()
            .channel(Channel.EMAIL)
            .subject("Test Notification")
            .body("New email notification")
            .build();

        var emailResult = service.send("email:sendgrid", email, new Recipient("user@example.com"));
        System.out.println("Result: " + emailResult);

        /**
         * Email async notification
         */
        var emailAsyncResult = service.sendAsync(
                "email:sendgrid",
                email,
                new Recipient("user@example.com")
        );

        emailAsyncResult.thenAccept(result ->
                System.out.println("Async result: " + result)
        ).exceptionally(ex -> {
            System.err.println("Async error: " + ex.getMessage());
            return null;
        });

        /**
         * SMS notification
         */
        var sms = new NotificationMessage.Builder()
                .channel(Channel.SMS)
                .body("New SMS notification")
                .build();

        var smsResult = service.send("sms:twilio", sms, new Recipient("+5491112345678"));
        System.out.println("Result: " + smsResult);

        /**
         * Push notification
         */
        var push = new NotificationMessage.Builder()
            .channel(Channel.PUSH)
            .body("New Push notification")
            .build();

        var pushResult = service.send("push:fcm", push, new Recipient("device-token-1234567"));
        System.out.println("Result: " + pushResult);


        // for blocking principal thread until async process is finished
        emailAsyncResult.join();
    }
}
