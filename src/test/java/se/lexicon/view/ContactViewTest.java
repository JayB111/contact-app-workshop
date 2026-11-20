package se.lexicon.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lexicon.model.Contact;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContactViewTest {
    private InputStream originalIn;
    private PrintStream originalOut;
    private PrintStream originalErr;
    private ByteArrayOutputStream output;
    private ByteArrayOutputStream errorOutput;
    private ContactView view;

    @BeforeEach
    void setUp() {
        originalIn = System.in;
        originalOut = System.out;
        originalErr = System.err;
        output = new ByteArrayOutputStream();
        errorOutput = new ByteArrayOutputStream();

        System.setIn(new ByteArrayInputStream("Anna\n".getBytes(StandardCharsets.UTF_8)));
        System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(errorOutput, true, StandardCharsets.UTF_8));
        view = new ContactView();
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void getUserInputDisplaysPromptAndReturnsEnteredLine() {
        String input = view.getUserInput("Name: ");

        assertEquals("Anna", input);
        assertEquals("Name: ", output.toString(StandardCharsets.UTF_8));
    }

    @Test
    void displayMenuPrintsAllMenuOptions() {
        view.displayMenu();

        assertEquals("\n" + lines("Contact App", "1. List contacts", "2. Add contact",
                "3. Search contacts", "4. Exit"), output.toString(StandardCharsets.UTF_8));
    }

    @Test
    void displayContactsShowsMessageWhenListIsEmpty() {
        view.displayContacts(List.of());

        assertEquals(lines("No contacts found."), output.toString(StandardCharsets.UTF_8));
    }

    @Test
    void displayContactsPrintsEachContactWhenListIsNotEmpty() {
        Contact first = new Contact("Anna", "0701234567");
        Contact second = new Contact("Erik", "0729876543");

        view.displayContacts(List.of(first, second));

        assertEquals(lines(first.toString(), second.toString()), output.toString(StandardCharsets.UTF_8));
    }

    @Test
    void displayMessagePrintsMessageToStandardOutput() {
        view.displayMessage("Contact saved.");

        assertEquals(lines("Contact saved."), output.toString(StandardCharsets.UTF_8));
        assertEquals("", errorOutput.toString(StandardCharsets.UTF_8));
    }

    @Test
    void displayErrorPrintsMessageToStandardError() {
        view.displayError("Unable to save contact.");

        assertEquals("", output.toString(StandardCharsets.UTF_8));
        assertEquals(lines("Unable to save contact."), errorOutput.toString(StandardCharsets.UTF_8));
    }

    private static String lines(String... lines) {
        return String.join(System.lineSeparator(), lines) + System.lineSeparator();
    }
}
