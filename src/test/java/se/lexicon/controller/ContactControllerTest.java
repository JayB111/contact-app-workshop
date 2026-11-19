package se.lexicon.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import org.junit.jupiter.api.Test;
import se.lexicon.data.ContactDAO;
import se.lexicon.exception.ContactStorageException;
import se.lexicon.exception.DuplicateContactException;
import se.lexicon.model.Contact;
import se.lexicon.view.ContactView;

class ContactControllerTest {
    @Test
    void listsContactsThenExits() {
        FakeDAO dao = new FakeDAO();
        dao.contacts.add(new Contact("Alice", "1234567890"));
        FakeView view = new FakeView("1", "4");

        new ContactController(dao, view).run();

        assertEquals(2, view.menuCount);
        assertEquals(1, view.displayedContacts.size());
        assertEquals(1, view.displayedContacts.get(0).size());
        assertTrue(view.messages.contains("Goodbye!"));
        assertTrue(view.errors.isEmpty());
    }

    @Test
    void addsValidContact() {
        FakeDAO dao = new FakeDAO();
        FakeView view = new FakeView("2", "Alice", "1234567890", "0");

        new ContactController(dao, view).run();

        assertEquals(1, dao.contacts.size());
        assertEquals("Alice", dao.contacts.get(0).getName());
        assertTrue(view.messages.contains("Contact added successfully."));
    }

    @Test
    void searchesForContactByTrimmedName() {
        FakeDAO dao = new FakeDAO();
        dao.contacts.add(new Contact("Alice", "1234567890"));
        FakeView view = new FakeView("3", " Alice ", "4");

        new ContactController(dao, view).run();

        assertEquals("Alice", dao.lastSearch);
        assertEquals(1, view.displayedContacts.size());
    }

    @Test
    void reportsMissingContact() {
        FakeView view = new FakeView("3", "Unknown", "4");

        new ContactController(new FakeDAO(), view).run();

        assertTrue(view.messages.contains("No contact found with that name."));
    }

    @Test
    void invalidPhoneDoesNotEndApplication() {
        FakeDAO dao = new FakeDAO();
        FakeView view = new FakeView("2", "Alice", "invalid", "4");

        new ContactController(dao, view).run();

        assertTrue(dao.contacts.isEmpty());
        assertTrue(view.errors.stream().anyMatch(message -> message.startsWith("Invalid input")));
        assertTrue(view.messages.contains("Goodbye!"));
    }

    @Test
    void duplicateErrorIsShownAndMenuContinues() {
        FakeDAO dao = new FakeDAO();
        dao.saveFailure = new DuplicateContactException("Already exists");
        FakeView view = new FakeView("2", "Alice", "1234567890", "4");

        new ContactController(dao, view).run();

        assertTrue(view.errors.contains("Duplicate contact: Already exists"));
        assertTrue(view.messages.contains("Goodbye!"));
    }

    @Test
    void storageErrorIsShownAndMenuContinues() {
        FakeDAO dao = new FakeDAO();
        dao.readFailure = new ContactStorageException("File unavailable");
        FakeView view = new FakeView("1", "4");

        new ContactController(dao, view).run();

        assertTrue(view.errors.contains("Contact storage error: File unavailable"));
        assertTrue(view.messages.contains("Goodbye!"));
    }

    @Test
    void unknownMenuChoiceIsReportedWithoutStopping() {
        FakeView view = new FakeView("9", "4");

        new ContactController(new FakeDAO(), view).run();

        assertTrue(view.errors.contains("Unknown option. Please choose a menu item."));
        assertEquals(2, view.menuCount);
    }

    @Test
    void emptySearchNameIsRejectedWithoutCallingDao() {
        FakeDAO dao = new FakeDAO();
        FakeView view = new FakeView("3", "  ", "4");

        new ContactController(dao, view).run();

        assertEquals(null, dao.lastSearch);
        assertTrue(view.errors.stream().anyMatch(message -> message.startsWith("Invalid input")));
    }

    @Test
    void unexpectedDaoErrorUsesGenericMessageAndMenuContinues() {
        FakeDAO dao = new FakeDAO();
        dao.unexpectedReadFailure = new IllegalStateException("Sensitive info");
        FakeView view = new FakeView("1", "4");

        new ContactController(dao, view).run();

        assertTrue(view.errors.contains("An unexpected error occurred. Please try again."));
        assertFalse(view.errors.stream().anyMatch(message -> message.contains("Sensitive info")));
    }

    @Test
    void closedInputExitsWithoutLooping() {
        FakeView view = new FakeView();

        new ContactController(new FakeDAO(), view).run();

        assertEquals(1, view.menuCount);
        assertTrue(view.messages.contains("Input closed. Goodbye!"));
    }

    @Test
    void requiresBothDependencies() {
        assertThrows(NullPointerException.class, () -> new ContactController(null, new FakeView()));
        assertThrows(NullPointerException.class, () -> new ContactController(new FakeDAO(), null));
    }

    private static class FakeDAO implements ContactDAO {
        final List<Contact> contacts = new ArrayList<>();
        ContactStorageException readFailure;
        DuplicateContactException saveFailure;
        RuntimeException unexpectedReadFailure;
        String lastSearch;

        @Override
        public List<Contact> findAll() throws ContactStorageException {
            if (readFailure != null) throw readFailure;
            if (unexpectedReadFailure != null) throw unexpectedReadFailure;
            return new ArrayList<>(contacts);
        }

        @Override
        public void save(Contact contact) throws ContactStorageException, DuplicateContactException {
            if (saveFailure != null) throw saveFailure;
            contacts.add(contact);
        }

        @Override
        public Contact findByName(String name) throws ContactStorageException {
            lastSearch = name;
            return contacts.stream()
                    .filter(contact -> contact.getName().equalsIgnoreCase(name))
                    .findFirst().orElse(null);
        }
    }

    private static class FakeView extends ContactView {
        final Queue<String> input;
        final List<String> messages = new ArrayList<>();
        final List<String> errors = new ArrayList<>();
        final List<List<Contact>> displayedContacts = new ArrayList<>();
        int menuCount;

        FakeView(String... input) {
            this.input = new ArrayDeque<>(Arrays.asList(input));
        }

        @Override
        public String getUserInput(String prompt) {
            return input.remove();
        }

        @Override
        public void displayMenu() {
            menuCount++;
        }

        @Override
        public void displayContacts(List<Contact> contacts) {
            displayedContacts.add(new ArrayList<>(contacts));
        }

        @Override
        public void displayMessage(String message) {
            messages.add(message);
        }

        @Override
        public void displayError(String message) {
            errors.add(message);
        }
    }
}
