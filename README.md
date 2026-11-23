# Group 4 — Contact App Workshop

A Java console contact-management application created by **Group 4** for the Lexicon workshop. See the [final team report](docs/FINAL_TEAM_REPORT.md) for the workshop timeline and evidence.

**Project status:** Core workshop scope completed. The final `main` build passes **52 JUnit tests** on GitHub Actions; an independent 11-scenario scripted console acceptance run passes **11/11**. The IDE issue reported on one developer machine does not affect the successful Maven/CI build. A separately observed human manual test on the final commit is not recorded.

## Features

- Menu-driven **list**, **add**, and **search contacts**; clean **exit**.
- Non-blank names and exactly ten digits in phone numbers.
- Case-insensitive duplicate detection and name search.
- Text-file persistence across application restarts; missing file treated as empty.
- Centralized validation, storage, and duplicate error messages without ending the menu loop.
- MVC-inspired layering with `ContactDAO` and `FileContactDAOImpl`.

## Build and run

**Prerequisites:** JDK 17 or newer and Apache Maven. GitHub Actions verifies the application on **JDK 17**, matching the **Java 17 source and bytecode target** in `pom.xml` (PR #18). The earlier JDK 25 build also passed.

From the repository root:

```bash
mvn clean verify
java -cp target/classes se.lexicon.controller.Main
```

To compile without running the tests, use `mvn clean compile` before the `java` command.

The application writes `contacts.txt` into the **current working directory**. For example, launch the command from the repository root to keep the file there. Do not commit real personal contact data.

```text
Contact App
1. List contacts
2. Add contact
3. Search contacts
4. Exit
```

## Storage format and behavior

- Each line in `contacts.txt` is one contact in `name|phoneNumber` form.
- Because phone numbers contain exactly ten digits, the reader uses the **last `|`** as the separator; names containing `|` can be saved and read back.
- `ContactDAO.findAll()` returns an empty list when the file is absent.
- `ContactDAO.findByName()` returns `null` for no match; matching is case-insensitive.
- Duplicate names throw `DuplicateContactException`.
- File and malformed-record errors throw `ContactStorageException`; invalid new user inputs throw `IllegalArgumentException`.
- This is intentionally simple, one-record-per-line workshop storage, not a concurrent database or a full escaped-record interchange format.

## Architecture and tests

| Area | Implementation |
|---|---|
| Model | `se.lexicon.model.Contact` |
| View | `se.lexicon.view.ContactView` |
| Controller | `se.lexicon.controller.ContactController` |
| DAO | `se.lexicon.data.ContactDAO`, `FileContactDAOImpl` |
| Exceptions | `ContactStorageException`, `DuplicateContactException`, `ExceptionHandler` |
| Entry point | **`se.lexicon.controller.Main`** |
| Tests | JUnit Jupiter 5.12.2; Maven Surefire 3.5.2 |
| CI | GitHub Actions, JDK 17, `mvn clean verify` |

- [Technical architecture and UML](docs/ARCHITECTURE.md)
- [Original six-member task specification](docs/Team-task-description.md)
- [Reconciled assignment and contributions record](docs/WORK_PLAN.md)
- [Final team workshop report](docs/FINAL_TEAM_REPORT.md)
- [Final CI run: 52/52 tests passed](https://github.com/JayB111/contact-app-workshop/actions/runs/45141713579)

## Group 4 — Team and contributions

The **original member numbers** come from `docs/Team-task-description.md` and are retained for consistency:

| Original member | Contributor | Initial responsibility |
|---|---|---|
| 1 | [Jay](https://github.com/JayB111) | Contact model and tests; project coordination |
| 2 | [Padma](https://github.com/Padmaqaauto) | Exceptions and DAO contract |
| 3 | [Carl](https://github.com/Matojotam) | File-backed DAO and tests |
| 4 | [Veselina](https://github.com/vlinakonsa-svg) | Contact view |
| 5 | [Anas](https://github.com/jackaldzn-blip) | Controller and centralized exception handling |
| 6 | [Tuku](https://github.com/tukue) | Main, integration, tests, and documentation |

Responsibilities changed during development. Tuku integrated the view, shared contracts, and CI; Padma added contract and view tests and the final entry point; Anas supplied controller tests and the final storage acceptance fixes. These changes are documented with merged PR references in the [work plan](docs/WORK_PLAN.md) and [final report](docs/FINAL_TEAM_REPORT.md). This does not erase the original allocation or assume that every informal contribution is visible in GitHub.
