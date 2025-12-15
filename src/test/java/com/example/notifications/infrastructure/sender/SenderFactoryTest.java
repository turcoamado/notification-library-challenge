package com.example.notifications.infrastructure.sender;

import com.example.notifications.application.port.out.NotificationSender;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class SenderFactoryTest {
    private static SenderFactory factory;

    @BeforeAll
    public static void setup() {
        factory = new SenderFactory();
    }

    @Test
    public void shouldResolveRegisteredSender() {
        factory = new SenderFactory();
        NotificationSender sender = mock(NotificationSender.class);

        factory.register("email:test", sender);

        NotificationSender resolved = factory.resolve("email:test");

        assertSame(sender, resolved);
    }

    @Test
    void shouldFailWhenSenderIsNotRegistered() {
        factory = new SenderFactory();

        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> factory.resolve("unknown"));

        assertEquals("No sender registered for: unknown", ex.getMessage());
    }
}
