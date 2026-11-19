# Contact App Workshop — Detailed Tasks for 6 Team Members

This document divides the Contact App workshop implementation among six team members. Each member should coordinate shared decisions with the other members before changing interfaces or behavior.

## Member 1 — Contact model and validation

**Goal:** Implement the model class that represents one contact and prevents invalid data from entering the application.

**File:** `src/main/java/se/lexicon/model/Contact.java`

**Tasks:**
1. Create the `se.lexicon.model` package if needed.
2. Implement `Contact` with private `name` and `phoneNumber` fields.
3. Add a constructor that accepts and validates both fields.
4. Add getters and setters; setters must apply the same validation as the constructor.
5. Throw unchecked `IllegalArgumentException` for invalid values:
   - Name is `null`, empty, or whitespace-only.
   - Phone number does not match `^\\d{10}$`.
6. Add a useful `toString()` representation for displaying a contact.
7. Coordinate with Member 3 on validation when reading records from the file.

**Signatures:**
```java
public Contact(String name, String phoneNumber)

public String getName()
public void setName(String name)

public String getPhoneNumber()
public void setPhoneNumber(String phoneNumber)

@Override
public String toString()
```

**Done when:** Valid contacts can be created and updated, while invalid data throws `IllegalArgumentException`.

## Member 2 — Custom exceptions and DAO contract

**Goal:** Define the checked exceptions for persistence and the interface that specifies data operations.

**Files:**
- `src/main/java/se/lexicon/exception/ContactStorageException.java`
- `src/main/java/se/lexicon/exception/DuplicateContactException.java`
- `src/main/java/se/lexicon/data/ContactDAO.java`

**Tasks:**
1. Implement checked `ContactStorageException` for file reading, writing, and malformed stored data.
2. Provide constructors that accept a message and a message plus cause.
3. Implement checked `DuplicateContactException` for attempts to save an existing contact.
4. Define `ContactDAO` using the signatures below.
5. Agree with Member 3 on duplicate detection and behavior when a contact is not found.

**Signatures:**
```java
public class ContactStorageException extends Exception {
    public ContactStorageException(String message)
    public ContactStorageException(String message, Throwable cause)
}

public class DuplicateContactException extends Exception {
    public DuplicateContactException(String message)
}

public interface ContactDAO {
    List<Contact> findAll() throws ContactStorageException;

    void save(Contact contact)
        throws ContactStorageException, DuplicateContactException;

    Contact findByName(String name) throws ContactStorageException;
}
```

**Shared behavior:** `findAll()` returns an empty list if the file does not exist. `findByName()` returns `null` if there is no match. Duplicate contacts are determined by case-insensitive name comparison.

**Done when:** The exceptions compile and the DAO interface is ready for Member 3 to implement.

## Member 3 — File-based DAO implementation

**Goal:** Persist contacts in a text file and retrieve them through the `ContactDAO` interface.

**File:** `src/main/java/se/lexicon/data/FileContactDAOImpl.java`

**Tasks:**
1. Implement `ContactDAO` and store the configured file location in a private `Path filePath` field.
2. Accept the `Path` through the constructor; do not hard-code the location inside the DAO.
3. Implement `findAll()` using try-with-resources. Return an empty list if the file does not exist. Parse each record into a validated `Contact`.
4. Implement `findByName(String name)` using case-insensitive name matching. Return `null` if no contact matches.
5. Implement `save(Contact contact)`. Check for duplicates before writing, create the file or parent directories when needed, and preserve existing records.
6. Agree on a text-file format with the team and document it. Use a format that can safely distinguish the name and phone number.
7. Wrap file and malformed-record problems in `ContactStorageException`, preserving the cause where available.
8. Keep all console interaction out of the DAO. Do not use `Scanner` or print to `System.out`.

**Signatures:**
```java
public FileContactDAOImpl(Path filePath)

@Override
public List<Contact> findAll() throws ContactStorageException

@Override
public void save(Contact contact)
    throws ContactStorageException, DuplicateContactException

@Override
public Contact findByName(String name) throws ContactStorageException
```

**Done when:** Contacts can be saved and loaded, duplicates are rejected, and storage failures are reported through checked exceptions.

## Member 4 — Contact view

**Goal:** Implement the console interface for collecting input and displaying information.

**File:** `src/main/java/se/lexicon/view/ContactView.java`

**Tasks:**
1. Create the `se.lexicon.view` package if needed.
2. Implement a clear menu with options to list contacts, add a contact, search by name, and exit.
3. Implement a method that displays a prompt and returns the user's input.
4. Implement contact-list display, including a clear message when the list is empty.
5. Implement normal-message and error-message display methods.
6. Keep `Scanner` and `System.out` usage inside the view. Avoid file access and application workflow in this class.
7. Coordinate menu option numbers with Member 5.

**Signatures:**
```java
public ContactView()

public String getUserInput(String prompt)
public void displayMenu()
public void displayContacts(List<Contact> contacts)
public void displayMessage(String message)
public void displayError(String message)
```

**Done when:** The user can read the menu, enter responses, and see contacts, messages, and errors through the view.

## Member 5 — Controller and centralized exception handling

**Goal:** Coordinate the view and DAO, manage the application loop, and report errors to the user.

**Files:**
- `src/main/java/se/lexicon/controller/ContactController.java`
- `src/main/java/se/lexicon/exception/ExceptionHandler.java`

**Tasks:**
1. Implement `ExceptionHandler` to map exceptions to user-readable messages. It returns text and does not print.
2. Handle `IllegalArgumentException`, `DuplicateContactException`, `ContactStorageException`, and unexpected exceptions.
3. Implement `ContactController` with injected `ContactDAO` and `ContactView` dependencies.
4. Implement the application loop: display the menu, read the choice, dispatch the action, and continue until exit.
5. Implement listing, adding, and searching flows.
6. Construct a `Contact` from user input when adding; handle model validation errors without terminating the application.
7. Catch expected exceptions and report them through the view. Handle unknown menu choices gracefully.
8. Coordinate menu choices with Member 4 and DAO behavior with Member 3.

**Signatures:**
```java
public final class ExceptionHandler {
    public static String handle(Exception exception)
}

public ContactController(ContactDAO contactDAO, ContactView contactView)

public void run()
```

Suggested private methods:
```java
private void listContacts()
private void addContact()
private void searchContact()
```

**Done when:** The controller runs the menu-driven application, and errors are shown through the view without console output from the DAO or model.

## Member 6 — Main, integration, tests, and documentation

**Goal:** Assemble the application, verify that the layers work together, and prepare usage documentation.

**Files:**
- `src/main/java/se/lexicon/Main.java`
- `README.md`
- Relevant tests under `src/test/java/`

**Tasks:**
1. Implement `Main` to create the storage `Path`, `FileContactDAOImpl`, `ContactView`, and `ContactController`, then call `run()`.
2. Keep `Main` limited to setup and startup. Agree on the default file location with Member 3.
3. Coordinate tests with other members and run `mvn test` after integration.
4. Manually verify that the application starts, lists an empty or missing file, adds and lists a valid contact, searches by name, rejects invalid data and duplicates, and exits cleanly.
5. Update the README with build and run instructions, phone format, file location, and menu actions.
6. Include an MVC explanation:
   - **Model:** represents and validates contact data.
   - **View:** handles user input and output.
   - **Controller:** coordinates user actions and application operations.
   - **DAO/data layer:** reads and writes contact records.

**Signature:**
```java
public static void main(String[] args)
```

**Done when:** The application starts through `Main`, the documented workflow works end to end, and `mvn test` passes.

## Dependencies and recommended implementation order

```text
Member 1: Contact model ─────────────────────┐
Member 2: Exceptions + ContactDAO interface ─┼──> Member 3: FileContactDAOImpl
Member 4: ContactView ───────────────────────┐
Member 3: FileContactDAOImpl ────────────────┼──> Member 5: Controller + ExceptionHandler
                                             └──> Member 6: Main + integration
```

1. Members 1 and 2 agree on model and exception contracts.
2. Member 3 begins once the DAO signatures and file format are agreed.
3. Member 4 can work in parallel, but coordinates menu options with Member 5.
4. Member 5 integrates the view and DAO through their agreed interfaces.
5. Member 6 wires the components together, runs tests, and updates documentation.

## Shared decisions to confirm before implementation

- Phone numbers must contain exactly 10 digits.
- Duplicate contacts are determined by case-insensitive name comparison.
- `findByName()` returns `null` when no contact matches.
- `findAll()` returns an empty list when the storage file is absent.
- The DAO uses try-with-resources and never prints to the console.
- `ExceptionHandler.handle()` returns an error message; the controller asks the view to display it.
