package se.lexicon.exception;

import java.util.Objects;

/** Converts application exceptions into readable messages without performing I/O. */
public final class ExceptionHandler {
    private ExceptionHandler() {
        // Utility class.
    }

    public static String handle(Exception exception) {
        Objects.requireNonNull(exception, "exception");

        if (exception instanceof IllegalArgumentException) {
            return message("Invalid input", exception);
        }
        if (exception instanceof DuplicateContactException) {
            return message("Duplicate contact", exception);
        }
        if (exception instanceof ContactStorageException) {
            return message("Contact storage error", exception);
        }
        return "An unexpected error occurred. Please try again.";
    }

    private static String message(String prefix, Exception exception) {
        String detail = exception.getMessage();
        return detail == null || detail.isBlank() ? prefix + "." : prefix + ": " + detail;
    }
}
