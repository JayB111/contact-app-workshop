# Contact App Workshop — Team Work Plan

This plan summarizes the agreed division of work and integration process. **[Team-task-description.md](Team-task-description.md) is the reference for each member's detailed tasks and method signatures.** The technical class diagram is in [ARCHITECTURE.md](ARCHITECTURE.md).

## 1. Assigned work

| Member | GitHub | Responsibility |
|---|---|---|
| 1 — Jay | [@JayB111](https://github.com/JayB111) | `Contact` model, validation, model tests |
| 2 — Padma | [@Padmaqaauto](https://github.com/Padmaqaauto) | `ContactStorageException`, `DuplicateContactException`, `ContactDAO` |
| 3 — Carl | [@Matojotam](https://github.com/Matojotam) | `FileContactDAOImpl`, storage behaviour and tests |
| 4 — Veselina | [@vlinakonsa-svg](https://github.com/vlinakonsa-svg) | `ContactView` and console interaction |
| 5 — Anas | [@jackaldzn-blip](https://github.com/jackaldzn-blip) | `ContactController`, `ExceptionHandler`, controller tests |
| 6 — Tuku | [@tukue](https://github.com/tukue) | `Main`, integration checks, test coordination, README |

The project lead coordinates collaboration and merging; that role does not change the assignments above. Everyone contributes to discussions, code reviews, testing, and integration.

## 2. Agreed design contracts

| Area | Contract |
|---|---|
| Model validation | Nonblank contact name; phone number matches `^\d{10}$` (exactly 10 digits); invalid data raises `IllegalArgumentException` |
| Duplicate detection | Compare names without regard to case |
| Missing storage file | `ContactDAO.findAll()` returns an empty list |
| Search miss | `ContactDAO.findByName(name)` returns `null` |
| Storage errors | DAO reports `ContactStorageException`; duplicates use `DuplicateContactException`; both are checked exceptions |
| File management | Implementation uses try-with-resources and keeps all console output out of the DAO |
| Error reporting | `ExceptionHandler.handle(Exception)` returns a message; controller asks the view to display it |
| Menu | `1` List contacts, `2` Add contact, `3` Search by name, `4` Exit (matches current view) |

Consult the [technical UML and API contracts](ARCHITECTURE.md) for class signatures and dependencies. A documented contract may be planned even when a class on `main` is still a placeholder.

## 3. Development and review workflow

1. Agree on shared method signatures, menu numbers, file format, and exception behaviour before changing interfaces.
2. Work in individual feature branches based on the latest `main`; do not commit directly to `main`.
3. Implement the assigned production classes and focused JUnit tests.
4. Run `mvn clean verify` locally, then open a pull request and request a teammate's review.
5. Address review comments, resolve merge conflicts, and verify that GitHub Actions reports a passing build before merging.
6. After integration, manually exercise the menu and test persistence, duplicate handling, invalid input, and error recovery.

**CI configuration:** `.github/workflows/contact-app-workshop.yml` runs Maven `clean verify` on JDK 25. `pom.xml` currently targets Java 17 compatibility. Check the actual number of JUnit tests reported; compilation alone is not a substitute for tests.

## 4. Dependency order

- Members 1 and 2 provide the model, checked exceptions, and DAO interface contracts.
- Member 3 builds persistence against those contracts; Member 4 can build the view in parallel.
- Member 5 implements the controller and error mapping against the DAO and view signatures.
- Member 6 wires the application entry point and coordinates integration.

## 5. Outstanding integration decisions

- **Main package:** the detailed task assignment specifies `src/main/java/se/lexicon/Main.java`, but the current repository includes a placeholder at `src/main/java/se/lexicon/controller/Main.java`. Confirm the final location before implementing the entry point; this documentation PR does not move source files.
- **Storage format and default path:** Member 3 and Member 6 should agree on the text-record format and file path before implementation.
- **Name normalization:** the model currently trims input names; keep that behaviour consistent with model tests, duplicate matching, and any pending model PR.
- **Integration test ownership:** coordinate end-to-end checks once the DAO, controller, and entry point are implemented. Do not claim full application readiness based solely on a passing CI compilation.

## 6. Final integration checklist

- [ ] The entry point constructs the DAO, view, and controller and launches `run()`.
- [ ] Adding a valid contact writes to the file and survives an application restart.
- [ ] Listing an absent/empty file shows no contacts without an exception.
- [ ] Name search is case-insensitive and missing contacts display a clear message.
- [ ] Duplicate names and invalid contact data are handled without crashing the menu.
- [ ] File I/O errors are reported through the centralized exception handler.
- [ ] Unit and integration tests pass in CI, and the README accurately describes how to run the program.
