package se.lexicon.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DuplicateContactExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Contact already exists";

        DuplicateContactException exception =
                new DuplicateContactException(message);

        assertEquals(message, exception.getMessage());
    }
}