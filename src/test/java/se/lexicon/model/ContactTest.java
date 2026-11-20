
package se.lexicon.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContactTest {

    @Test
    void testCreateContact() {
        Contact contact = new Contact("Anna", "0701234567");

        assertEquals("Anna", contact.getName());
        assertEquals("0701234567", contact.getPhoneNumber());
    }
    @Test
    void testEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("", "0701234567");
        });
    }
    @Test
    void testInvalidPhoneNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("Anna", "12345");
        });
    }
    @Test
    void testNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact(null, "0701234567");
        });
    }
    @Test
    void testPhoneNumberWithLetters() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("Anna", "070ABC4567");
        });
    }
    @Test
    void testNameWithOnlySpaces() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("   ", "0701234567");
        });
    }
    @Test
    void testUpdateContact() {
        Contact contact = new Contact("Anna", "0701234567");
        contact.setName("Maria");
        contact.setPhoneNumber("0729876543");
        assertEquals("Maria", contact.getName());
        assertEquals("0729876543", contact.getPhoneNumber());
    }
    @Test
    void testSettersRejectInvalidValues() {
        Contact contact = new Contact("Anna", "0701234567");

        assertThrows(IllegalArgumentException.class, () -> contact.setName("   "));
        assertThrows(IllegalArgumentException.class, () -> contact.setPhoneNumber("123"));
    }
}
