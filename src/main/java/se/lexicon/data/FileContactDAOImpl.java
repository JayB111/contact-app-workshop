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
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                // The phone is always ten digits, so the final pipe separates
                // the phone from a name that may itself contain pipe characters.
                int separator = line.lastIndexOf('|');
                if (separator <= 0 || separator == line.length() - 1) {
                    throw new ContactStorageException(
                            "Invalid record format at line " + lineNumber,
                            new IllegalArgumentException("Missing name or phone delimiter"));
                }

                String name = line.substring(0, separator).trim();
                String phone = line.substring(separator + 1).trim();
                try {
                    contacts.add(new Contact(name, phone));
                } catch (IllegalArgumentException e) {
                    throw new ContactStorageException("Invalid contact at line " + lineNumber, e);
                }
            }
        } catch (NoSuchFileException e) {
            return Collections.emptyList();
        } catch (IOException e) {
            throw new ContactStorageException("Error reading file", e);
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

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            for (Contact c : contacts) {
                writer.write(c.getName() + "|" + c.getPhoneNumber() + "\n");
            }
        } catch (IOException e) {
            throw new ContactStorageException("Error writing file", e);
        }
    }
}