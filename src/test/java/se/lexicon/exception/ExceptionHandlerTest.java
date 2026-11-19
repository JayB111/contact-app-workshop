package se.lexicon.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ExceptionHandlerTest {
    @Test
    void explainsInvalidContactData() {
        assertEquals("Invalid input: Phone number must have 10 digits.",
                ExceptionHandler.handle(new IllegalArgumentException("Phone number must have 10 digits.")));
    }

    @Test
    void explainsDuplicateContacts() {
        assertEquals("Duplicate contact: Alice already exists",
                ExceptionHandler.handle(new DuplicateContactException("Alice already exists")));
    }

    @Test
    void explainsStorageProblems() {
        assertEquals("Contact storage error: Cannot read contacts",
                ExceptionHandler.handle(new ContactStorageException("Cannot read contacts")));
    }

    @Test
    void usesSafeGenericTextForUnexpectedErrors() {
        assertEquals("An unexpected error occurred. Please try again.",
                ExceptionHandler.handle(new IllegalStateException("Internal secret detail")));
    }

    @Test
    void handlesExpectedExceptionsWithNoMessage() {
        assertEquals("Invalid input.", ExceptionHandler.handle(new IllegalArgumentException()));
    }

    @Test
    void rejectsNullException() {
        assertThrows(NullPointerException.class, () -> ExceptionHandler.handle(null));
    }
}
