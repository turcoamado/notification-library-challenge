package com.pinapp.notifications.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecipientTest {

    @Test
    public void shouldFailWhenRecipientIsBlank() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> new Recipient(""));

        assertEquals("Recipient is required", ex.getMessage());
    }

    @Test
    public void shouldFailWhenRecipientIsNull() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> new Recipient(null));

        assertEquals("Recipient is required", ex.getMessage());
    }
}
