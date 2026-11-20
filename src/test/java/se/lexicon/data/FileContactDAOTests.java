package se.lexicon.data;

import se.lexicon.model.Contact;
import se.lexicon.exception.ContactStorageException;
import se.lexicon.exception.DuplicateContactException;
import org.junit.jupiter.api.Test;
import java.io.BufferedWriter;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileContactDAOTests {

    @Test
    public void testSaveAndFindAll() throws Exception {
        Path tempFile = Files.createTempFile("contact", ".txt");
        FileContactDAOImpl dao = new FileContactDAOImpl(tempFile);

        Contact contact = new Contact("John Doe", "1234567890"); // ✅ Valid 10-digit number
        dao.save(contact);

        List<Contact> contacts = dao.findAll();
        assertEquals(1, contacts.size());
        assertEquals("John Doe", contacts.get(0).getName());
        assertEquals("1234567890", contacts.get(0).getPhoneNumber());
    }

    @Test
    public void testDuplicateContact() throws Exception {
        Path tempFile = Files.createTempFile("contact", ".txt");
        FileContactDAOImpl dao = new FileContactDAOImpl(tempFile);

        Contact contact = new Contact("John Doe", "1234567890");
        dao.save(contact);

        assertThrows(DuplicateContactException.class, () -> {
            dao.save(new Contact("John Doe", "9876543210"));
        });
    }

    @Test
    public void testFindByName() throws Exception {
        Path tempFile = Files.createTempFile("contact", ".txt");
        FileContactDAOImpl dao = new FileContactDAOImpl(tempFile);

        Contact contact = new Contact("John Doe", "1234567890"); // ✅ Valid 10-digit number
        dao.save(contact);

        Contact found = dao.findByName("John Doe");
        assertNotNull(found);
        assertEquals("John Doe", found.getName());
        assertEquals("1234567890", found.getPhoneNumber());

        Contact notFound = dao.findByName("Jane Smith");
        assertNull(notFound);
    }

    @Test
    public void testInvalidFormat() throws Exception {
        Path tempFile = Files.createTempFile("contact", ".txt");
        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            writer.write("John,Doe,123\n");
        }

        FileContactDAOImpl dao = new FileContactDAOImpl(tempFile);
        assertThrows(IllegalArgumentException.class, () -> {
            dao.findAll();
        });
    }

    @Test
    public void testInvalidData() throws Exception {
        Path tempFile = Files.createTempFile("contact", ".txt");
        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            writer.write("John,,123\n");
        }

        FileContactDAOImpl dao = new FileContactDAOImpl(tempFile);
        assertThrows(IllegalArgumentException.class, () -> {
            dao.findAll();
        });
    }

    @Test
    public void testMissingFile() throws Exception {
        Path tempFile = Files.createTempFile("contact", ".txt");
        Files.delete(tempFile);

        FileContactDAOImpl dao = new FileContactDAOImpl(tempFile);
        List<Contact> contacts = dao.findAll();
        assertTrue(contacts.isEmpty());
    }

    @Test
    public void testDirectoryCreationFailure() throws Exception {
        Path tempFile = Files.createTempFile("contact", ".txt");
        Files.delete(tempFile);
        Path parent = tempFile.getParent();
        if (Files.exists(parent)) {
            Files.delete(parent);
        }

        FileContactDAOImpl dao = new FileContactDAOImpl(tempFile);
        assertThrows(ContactStorageException.class, () -> {
            dao.save(new Contact("John Doe", "1234567890"));
        });
    }
}