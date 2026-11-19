package se.lexicon.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import se.lexicon.data.ContactDAO;
import se.lexicon.exception.ExceptionHandler;
import se.lexicon.model.Contact;
import se.lexicon.view.ContactView;

/** Coordinates contact operations; all user interaction belongs to ContactView. */
public class ContactController {
    private final ContactDAO contactDAO;
    private final ContactView contactView;

    public ContactController(ContactDAO contactDAO, ContactView contactView) {
        this.contactDAO = Objects.requireNonNull(contactDAO, "contactDAO");
        this.contactView = Objects.requireNonNull(contactView, "contactView");
    }

    /** Runs until the user selects Exit or console input closes. */
    public void run() {
        while (true) {
            String choice;
            try {
                contactView.displayMenu();
                choice = contactView.getUserInput("Choose an option: ");
            } catch (NoSuchElementException exception) {
                contactView.displayMessage("Input closed. Goodbye!");
                return;
            } catch (Exception exception) {
                contactView.displayError(ExceptionHandler.handle(exception));
                continue;
            }

            if (choice == null) {
                contactView.displayMessage("Input closed. Goodbye!");
                return;
            }

            try {
                switch (choice.trim()) {
                    case "1" -> listContacts();
                    case "2" -> addContact();
                    case "3" -> searchContact();
                    case "0", "4" -> {
                        contactView.displayMessage("Goodbye!");
                        return;
                    }
                    default -> contactView.displayError("Unknown option. Please choose a menu item.");
                }
            } catch (NoSuchElementException exception) {
                contactView.displayMessage("Input closed. Goodbye!");
                return;
            } catch (Exception exception) {
                contactView.displayError(ExceptionHandler.handle(exception));
            }
        }
    }

    private void listContacts() throws se.lexicon.exception.ContactStorageException {
        contactView.displayContacts(contactDAO.findAll());
    }

    private void addContact() throws se.lexicon.exception.ContactStorageException,
            se.lexicon.exception.DuplicateContactException {
        String name = contactView.getUserInput("Enter contact name: ");
        String phoneNumber = contactView.getUserInput("Enter phone number (10 digits): ");
        Contact contact = new Contact(name, phoneNumber);
        contactDAO.save(contact);
        contactView.displayMessage("Contact added successfully.");
    }

    private void searchContact() throws se.lexicon.exception.ContactStorageException {
        String name = contactView.getUserInput("Enter name to search: ");
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Contact name cannot be empty.");
        }

        Contact contact = contactDAO.findByName(name.trim());
        if (contact == null) {
            contactView.displayMessage("No contact found with that name.");
        } else {
            contactView.displayContacts(List.of(contact));
        }
    }
}
