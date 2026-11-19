package se.lexicon.controller;

import java.nio.file.Path;
import se.lexicon.data.ContactDAO;
import se.lexicon.data.FileContactDAOImpl;
import se.lexicon.view.ContactView;

/** Starts the console-based contact application. */
public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        Path storagePath = args.length > 0
                ? Path.of(args[0])
                : Path.of("data", "contacts.txt");

        ContactDAO contactDAO = new FileContactDAOImpl(storagePath);
        ContactView contactView = new ContactView();
        new ContactController(contactDAO, contactView).run();
    }
}
