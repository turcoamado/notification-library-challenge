package com.example.notifications.application.service;

import com.example.notifications.application.dto.NotificationResult;
import com.example.notifications.domain.exception.ValidationException;
import com.example.notifications.domain.model.NotificationMessage;
import com.example.notifications.domain.model.Recipient;
import com.example.notifications.application.port.out.NotificationSender;
import com.example.notifications.infrastructure.sender.SenderFactory;
import com.example.notifications.domain.model.Channel;
import com.example.notifications.infrastructure.sender.email.SendGridEmailSenderMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotificationServiceTest {
    private static SenderFactory factory;
    private static NotificationSender mockSender;
    private static NotificationMessage email;

    @BeforeEach
    public void setup() {
        factory = new SenderFactory();
        mockSender = mock(NotificationSender.class);

        email = new NotificationMessage.Builder()
                .channel(Channel.EMAIL)
                .subject("Test email")
                .body("This is an email for testing purposes.")
                .build();
    }

    @Test
    public void sendEmailNotificationSuccessfully() {
        when(mockSender.send(any(), any())).thenReturn(NotificationResult.success("Mock", "id-1"));
        when(mockSender.providerName()).thenReturn("Mock");

         factory.register("email:mock", mockSender);
        NotificationService service = new NotificationService(factory);

        var result = service.send("email:mock", email, new Recipient("user@example.com"));
        assertTrue(result.success());
        verify(mockSender, times(1)).send(any(), any());
    }

    @Test
    public void shouldReturnFailureWhenSenderReturnsFailure() {
        NotificationSender sender = new SendGridEmailSenderMock("");

        factory.register("email:sendgrid", sender);
        NotificationService service = new NotificationService(factory);

        NotificationResult res = service.send(
                "email:sendgrid",
                email,
                new Recipient("user@example.com")
        );

        assertFalse(res.success());
        assertEquals("SendGridMock", res.provider());
        assertEquals("Missing api key", res.errorMessage());
    }

    @Test
    public void shouldThrowExceptionWhenEmailIsInvalid() {
        factory.register("email:mock", mockSender);
        NotificationService service = new NotificationService(factory);

        assertThrows(ValidationException.class, () ->
                service.send(
                        "email:mock",
                        email,
                        new Recipient("invalid-email")
                )
        );

        verify(mockSender, never()).send(any(), any());
    }

    @Test
    void shouldRetryAndEventuallySucceed() {
        SenderFactory factory = new SenderFactory();
        NotificationSender sender = mock(NotificationSender.class);

        when(sender.providerName()).thenReturn("MockProvider");

        // Fails 2 times, success in the 3rd
        when(sender.send(any(), any()))
                .thenThrow(new RuntimeException("temporary error"))
                .thenThrow(new RuntimeException("temporary error"))
                .thenReturn(NotificationResult.success("MockProvider", "id-123"));

        factory.register("email:mock", sender);
        NotificationService service = new NotificationService(factory);

        NotificationResult result = service.send(
                "email:mock",
                email,
                new Recipient("user@example.com")
        );

        assertTrue(result.success());
        assertEquals("MockProvider", result.provider());

        verify(sender, times(3)).send(any(), any());
    }

    @Test
    public void shouldReturnFailureWhenSenderThrowsExceptionAfterMaxRetries() {
        when(mockSender.providerName()).thenReturn("MockProvider");
        when(mockSender.send(any(), any()))
                .thenThrow(new RuntimeException("Some error when sending email notification"));

        factory.register("email:mock", mockSender);
        NotificationService service = new NotificationService(factory);

        NotificationResult result = service.send(
                "email:mock",
                email,
                new Recipient("user@example.com")
        );

        assertFalse(result.success());
        assertEquals("MockProvider", result.provider());
        assertEquals("Some error when sending email notification", result.errorMessage());

        // 1 initial try + 3 retries
        verify(mockSender, times(4)).send(any(), any());
    }
}
