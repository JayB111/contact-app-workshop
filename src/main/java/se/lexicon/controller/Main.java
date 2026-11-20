package se.lexicon.controller;

import se.lexicon.data.ContactDAO;
import se.lexicon.data.FileContactDAOImpl;
import se.lexicon.view.ContactView;

import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        Path storagePath = Path.of("contacts.txt");
        ContactDAO contactDAO = new FileContactDAOImpl(storagePath);
        ContactView contactView = new ContactView();
        ContactController contactController = new ContactController(contactDAO, contactView);

        contactController.run();
    }
}
