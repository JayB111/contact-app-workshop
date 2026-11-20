package se.lexicon.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactStorageExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Failed to read contacts";

        ContactStorageException exception =
                new ContactStorageException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        String message = "Failed to save contacts";
        Throwable cause = new RuntimeException("File error");

        ContactStorageException exception =
                new ContactStorageException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}