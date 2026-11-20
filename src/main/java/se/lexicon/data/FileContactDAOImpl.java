package se.lexicon.data;

import se.lexicon.model.Contact;
import se.lexicon.exception.ContactStorageException;
import se.lexicon.exception.DuplicateContactException;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileContactDAOImpl implements ContactDAO {
    private final Path filePath;

    public FileContactDAOImpl(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Contact> findAll() throws ContactStorageException {
        List<Contact> contacts = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\|"); // ✅ Use pipe as delimiter
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Invalid record format: " + line);
                }
                String name = parts[0].trim();
                String phone = parts[1].trim();

                if (name.isEmpty() || phone.isEmpty()) {
                    throw new IllegalArgumentException("Invalid contact data: " + line);
                }

                contacts.add(new Contact(name, phone));
            }
        } catch (IOException e) {
            if (Files.notExists(filePath)) {
                return Collections.emptyList();
            } else {
                throw new ContactStorageException("Error reading file", e);
            }
        }
        return contacts;
    }

    @Override
    public Contact findByName(String name) throws ContactStorageException {
        List<Contact> contacts = findAll();
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }

    @Override
    public void save(Contact contact) throws ContactStorageException, DuplicateContactException {
        Contact existing = findByName(contact.getName());
        if (existing != null) {
            throw new DuplicateContactException("Duplicate contact: " + contact.getName());
        }

        //List<Contact> contacts = findAll();
        List<Contact> contacts = new ArrayList<>(findAll());
        contacts.add(contact);

        Path parent = filePath.getParent();
        if (parent != null && !Files.exists(parent)) {
            try {
                Files.createDirectories(parent);
            } catch (IOException e) {
                throw new ContactStorageException("Error creating directory", e);
            } catch (UnsupportedOperationException e) {
                throw new ContactStorageException("Directory creation failed", e);
            }
        }

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            for (Contact c : contacts) {
                writer.write(c.getName() + "|" + c.getPhoneNumber() + "\n");
            }
        } catch (IOException e) {
            throw new ContactStorageException("Error writing file", e);
        }
    }
}