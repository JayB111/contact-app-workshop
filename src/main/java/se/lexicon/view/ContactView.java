package se.lexicon.view;

import java.util.List;
import java.util.Scanner;
import se.lexicon.model.Contact;

public class ContactView {
    private final Scanner scanner = new Scanner(System.in);

    public String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void displayMenu() {
        System.out.println("\nContact App");
        System.out.println("1. List contacts");
        System.out.println("2. Add contact");
        System.out.println("3. Search contacts");
        System.out.println("4. Exit");
    }

    public void displayContacts(List<Contact> contacts) {
        if (contacts.isEmpty()) {
            displayMessage("No contacts found.");
            return;
        }
        contacts.forEach(System.out::println);
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayError(String message) {
        System.err.println(message);
    }
}
