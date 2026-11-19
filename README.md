# Contact App Workshop

A Java console application for managing contacts in a text file, developed for the Lexicon exception-handling workshop. The design combines an MVC-inspired separation of responsibilities, a DAO interface, input validation, checked exceptions, and centralized error reporting.

The intended menu supports **list contacts**, **add contact**, **search by name**, and **exit**. Storage and application startup are integrated as the relevant team tasks are completed.

## Team and responsibilities

The numbering below follows the agreed [detailed task assignments](docs/Team-task-description.md).

| Member | GitHub | Assigned responsibility |
|---|---|---|
| 1 — Jay | [@JayB111](https://github.com/JayB111) | `Contact` model and validation |
| 2 — Padma | [@Padmaqaauto](https://github.com/Padmaqaauto) | Checked exceptions and `ContactDAO` interface |
| 3 — Carl | [@Matojotam](https://github.com/Matojotam) | `FileContactDAOImpl` persistence |
| 4 — Veselina | [@vlinakonsa-svg](https://github.com/vlinakonsa-svg) | `ContactView` console interaction |
| 5 — Anas | [@jackaldzn-blip](https://github.com/jackaldzn-blip) | `ContactController` and `ExceptionHandler` |
| 6 — Tuku | [@tukue](https://github.com/tukue) | `Main`, integration, testing coordination, and documentation |

All members participate in design decisions, reviews, testing, and integration.

## Project layout

```text
contact-app-workshop/
├── .github/workflows/       # CI configuration
├── docs/
│   ├── ARCHITECTURE.md      # technical Mermaid UML and contracts
│   ├── Team-task-description.md  # agreed task ownership
│   └── WORK_PLAN.md         # workflow and integration checklist
├── src/
│   ├── main/java/se/lexicon/
│   │   ├── controller/      # controller and current Main placeholder
│   │   ├── data/            # DAO interface and file implementation
│   │   ├── exception/       # checked exceptions and handler
│   │   ├── model/           # Contact
│   │   └── view/            # console view
│   └── test/java/           # JUnit tests as members submit them
├── pom.xml
└── README.md
```

## Build and tests

The CI workflow sets up **JDK 25** and runs Maven verification. The `pom.xml` currently targets **Java 17** source/bytecode compatibility; these are distinct settings.

```bash
mvn clean verify
```

`mvn test` runs available JUnit tests. A successful build does **not** imply that all features are implemented or that tests exist for every component. Check the CI job log for the actual test count.

A runnable entry point and file persistence are separate integration tasks; do not assume the program can be launched until they are completed.

## Agreed application contracts

- Names must not be null, empty, or whitespace-only; phone numbers must contain exactly **10 digits**.
- Duplicates are matched by **case-insensitive contact name**.
- `findAll()` returns an empty list when the storage file does not exist; `findByName()` returns `null` for no match.
- File operations use **try-with-resources** and report storage failures using `ContactStorageException`.
- The DAO and model do not print to the console; the view handles input/output, and the controller delegates errors to `ExceptionHandler`.
- The current `ContactView` menu labels are `1` List, `2` Add, `3` Search, `4` Exit.

See [Architecture and method contracts](docs/ARCHITECTURE.md) for the target class design and implementation caveats.

## Collaboration and documentation

Use one branch per task, submit small pull requests, request peer review, and merge when compatible with `main` and the CI checks. Details and open integration decisions are in the [Work Plan](docs/WORK_PLAN.md).

The [detailed task descriptions](docs/Team-task-description.md) remain the reference for ownership and method signatures. Documentation reflects the planned design and does not claim unfinished classes are implemented.
