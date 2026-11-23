# Group 4 — Contact App Workshop: Final Team Report

**Lexicon Java Team Workshop**

**Workshop:** 19–23 November 2026 | Kickoff: Thursday at 09:30 CET

**Team:** Group 4 (six members)

**Repository:** [JayB111/contact-app-workshop](https://github.com/JayB111/contact-app-workshop)

**Final reviewed `main` commit:** `d5c662d57f027abf7d39a5547e2685d7b3adaae2`

## 1. Executive summary

The team developed a Java console-based contact management application as a collaborative programming workshop. Its purpose was not only to deliver a functioning program, but also to practice distributing responsibilities, agreeing on interfaces, using feature branches and pull requests, integrating independently developed components, reviewing code, and validating a shared application.

The completed application allows users to list, add, and search contacts and exit through a menu. Contact records are validated and persisted in a text file. The design separates the data model, console view, controller, data-access layer, and exception handling. Work progressed from shared task planning to implementation, integration, testing, and acceptance fixes.

The final merged version passed **52 JUnit tests with zero failures or errors** in GitHub Actions using Java 25, with Java 17 compilation compatibility. An additional **11-scenario scripted console acceptance suite passed 11/11** against the corrected application logic, covering file persistence across restarts, duplicate detection, invalid input, and storage-error handling. A separate interactive, human-operated acceptance session on the final commit was not independently documented; this should not be confused with a failed application test.

**Overall assessment:** The agreed core functionality is implemented and the final merged build is verified. The workshop met its principal technical and collaborative objectives, with a small number of documentation and developer-environment lessons identified during the final review.

## 2. Objectives and agreed scope

The team aimed to build a maintainable contact-management console application with the following capabilities:

- Display a menu for listing contacts, adding contacts, searching by name, and exiting.
- Validate non-blank names and phone numbers containing exactly ten digits.
- Save and load contacts from `contacts.txt`, retaining data across application restarts.
- Reject duplicate names using case-insensitive comparison and handle missing search results appropriately.
- Use a `ContactDAO` interface, a file-based DAO implementation, and checked storage/duplicate exceptions.
- Separate user interaction from business flow and data access using an MVC-inspired structure.
- Verify behavior through JUnit, Maven, GitHub Actions, review, and end-to-end acceptance scenarios.

The original task specification in [`docs/Team-task-description.md`](Team-task-description.md) is retained as the historical record of **assigned tasks**. Some work was later redistributed as the project evolved; the final attribution below reflects actual merged changes rather than silently rewriting the original agreement.

## 3. Group 4 — Team members and contributions

| Original member | Person / GitHub | Original assignment | Verified delivery or support |
|---|---|---|---|
| **1** | **Jay** — `@JayB111` | `Contact` model, validation and model tests | Implemented the model and eight model tests (PR #5); maintained the team work plan (PR #12); coordinated reviews and merges as repository/project lead. |
| **2** | **Padma** — `@Padmaqaauto` | Checked exceptions and `ContactDAO` contract | Added DAO/exception tests (PR #11), `ContactView` tests (PR #13), and the working application entry point (PR #16), taking on integration work beyond the initial assignment. |
| **3** | **Carl** — `@Matojotam` | File-backed DAO and persistence tests | Delivered `FileContactDAOImpl` and its storage tests (PR #14), then refined storage error handling (PR #15); collaborated through review and follow-up fixes. |
| **4** | **Veselina** — `@vlinakonsa-svg` | `ContactView` and console interaction | Veselina was unable to participate in the workshop due to illness. Tuku took over the view implementation (PR #7), while Padma contributed the dedicated view tests (PR #13), allowing the team to complete this component. |
| **5** | **Anas** — `@jackaldzn-blip` | `ContactController`, centralized `ExceptionHandler`, and tests | Implemented controller, error handling, and their tests (PR #8); reviewed integration; prepared documentation improvements (PR #9, ultimately closed without merge); ran end-to-end acceptance checks and corrected final storage defects with regression tests (PR #17). |
| **6** | **Tuku** — `@tukue` | Startup, integration, testing, and README | Created detailed task descriptions (PR #1); integrated the `ContactDAO` contract, custom exceptions, model compatibility updates, console view, and Java 25 CI workflow (PR #7). The final `Main` implementation was subsequently delivered by Padma. |

**Attribution note:** Original member numbers are those in the agreed detailed task description. The later `WORK_PLAN.md` and README temporarily used different numbers. This report preserves the historical assignment and separately records transferred work. Pull requests establish merged code and documentation changes; they do not capture every discussion, review, or informal contribution.

## 4. Architecture and implementation

The application follows an **MVC-inspired layered design** with a data-access object (DAO):

| Component | Responsibility |
|---|---|
| `Contact` | Holds a name and phone number, validates inputs, and provides a readable representation. |
| `ContactView` | Reads console input and displays menus, contacts, messages, and errors. |
| `ContactController` | Runs the menu loop, dispatches list/add/search operations, and recovers from expected errors. |
| `ContactDAO` | Declares the persistence contract: `findAll`, `save`, and `findByName`. |
| `FileContactDAOImpl` | Reads and writes the relative-path `contacts.txt` file using the DAO contract. |
| `ExceptionHandler` | Converts exceptions into safe, understandable messages for display by the view. |
| `ContactStorageException` / `DuplicateContactException` | Represent checked storage and duplicate-contact failures. |
| `se.lexicon.controller.Main` | Creates the storage path, DAO, view and controller, then starts the application. |

The implemented menu options are **1 List contacts, 2 Add contact, 3 Search contacts, and 4 Exit**. The name must be non-blank; the phone number must contain exactly ten digits. Duplicate detection uses case-insensitive name comparison. A missing storage file produces an empty list rather than an error.

The storage file uses one `name|phoneNumber` record per line. In the final fix, the parser identifies the **last pipe separator**, preserving a valid contact name containing `|`. Malformed file records are reported through `ContactStorageException`, rather than being presented as a user-input error. The class diagram and method-level contracts are documented in [`docs/ARCHITECTURE.md`](ARCHITECTURE.md).

The build uses **Maven**, **JUnit Jupiter 5.12.2**, and **Maven Surefire 3.5.2**. GitHub Actions runs `mvn clean verify` on **JDK 25**, while `pom.xml` targets **Java 17 source and bytecode compatibility**. The entry point is `se.lexicon.controller.Main` in the actual merged code, even though the original task brief proposed `se.lexicon.Main`.

## 5. Work sequence and milestones

| Phase | Milestones and evidence |
|---|---|
| **Thursday, 19 November — kickoff at 09:30** | Group 4 agreed on initial responsibilities and interfaces and began parallel development. The task breakdown (PR #1), model and validation (PR #5), and shared contracts, view and CI workflow (PR #7) were merged. Controller integration revealed a compatibility issue requiring adjustment. |
| **Friday, 20 November — integration and testing** | Controller and error-handling tests (PR #8), DAO and exception tests (PR #11), work plan (PR #12), view tests (PR #13), file persistence and tests (PRs #14–15), and application startup (PR #16) were integrated. The project reached 49 passing JUnit tests. Scripted acceptance uncovered two file-storage edge cases; the corrective PR #17 added three regression tests. |
| **Monday, 23 November — final verification** | PR #17 was merged, and the final `main` CI passed 52 JUnit tests with no failures. The corrected application passed all 11 scripted console acceptance scenarios. The final report and documentation reconciliation were prepared. |

## 6. Testing and acceptance evidence

**Unit and automated integration testing.** The final GitHub Actions run for merged commit `d5c662d` completed successfully with **52 tests, 0 failures, 0 errors, and 0 skipped**. The test suite covers the model, controller, exception handler, exceptions, DAO contract, console view, and file DAO. See [the final CI run](https://github.com/JayB111/contact-app-workshop/actions/runs/45133250161).

**Console acceptance testing.** The 11-scenario test starts the real Java application in separate JVM sessions, provides scripted console inputs, inspects output and error handling, and reads the generated temporary `contacts.txt` files. The final run passed **11/11 scenarios**:

1. Display the menu and exit cleanly.
2. List contacts when no storage file exists.
3. Add contacts, list them, search case-insensitively, and handle a search miss.
4. Preserve and retrieve records after restarting the application.
5. Reject a duplicate name without changing the stored data.
6. Reject invalid names and phones while continuing the menu loop.
7. Handle an unknown menu option.
8. Exit gracefully when console input ends.
9. Classify malformed stored records as storage errors.
10. Preserve a valid name containing `|` across save and reload.
11. Reject an empty search query and continue safely.

For transparency, the local acceptance verification compiled a mirror of the relevant final production classes using **OpenJDK 21 with `--release 17`**; the separately recorded CI run built and tested the merged GitHub repository using **JDK 25**. The acceptance runner was an automated console simulation, not an independently witnessed interactive manual test on Jay's IntelliJ installation.

## 7. Challenges and how the team addressed them

**Shared contracts and merge compatibility.** Parallel coding initially exposed mismatches between class interfaces and dependent code. The team standardized interfaces and exception types, used a temporary controller revert and a revised implementation, and relied on CI to validate integration. This reinforced the importance of agreeing on shared method signatures early.

**Redistribution of responsibilities.** Veselina was unable to participate during the workshop due to illness. To keep the project moving, Tuku implemented her assigned `ContactView` component (PR #7) and Padma developed its dedicated tests (PR #13). Responsibilities also shifted around shared contracts and the startup class. This flexibility supported integration while highlighting the importance of recording *original assignments* and *actual contributors* separately.

**File persistence edge cases.** The first integrated application passed 49 tests but failed two additional acceptance scenarios: corrupted stored data was classified as invalid user input, and a pipe character inside a valid name broke the file format. PR #17 corrected the parser and error classification, introduced regression coverage, and increased the suite to 52 passing tests.

**IDE versus build-system configuration.** Jay reported IntelliJ not resolving `org.junit.jupiter.api` and marking the test folder incorrectly, although Maven and GitHub Actions passed. The issue concerned local IDE project configuration; the independent CI and command-line execution provided reliable evidence of compilation and application behavior. The final status of that individual IntelliJ installation was not confirmed.

**Documentation drift.** The README still described initial setup, and member numbering differed between files. A documentation-only PR (#9) was closed without merge, while PR #12 changed the work plan. This final reconciliation uses the original member numbering, describes transferred work without erasing anyone's role, and updates the implemented package names and verification results.

## 8. Final status, scope boundaries, and lessons

The **core workshop application is functionally complete** and the latest merged build is green. The 52 JUnit tests and 11 automated console acceptance scenarios support its correctness for the agreed flows. No additional application feature was required to satisfy the workshop scope.

There are still sensible boundaries to state: an independently documented *human interactive test on the final `main` commit* was not available; an individual developer's IntelliJ setup remained unresolved in the latest discussion; and the simple line-based file format is suitable for workshop use rather than a production-grade, concurrent contact database. These are not evidence that the tested core application failed.

The main learning outcomes were practical use of **interface-first collaboration, single-responsibility architecture, exception contracts, peer review, Git branching and pull requests, CI, regression testing, and clear contributor attribution**. The two bugs discovered by acceptance testing also demonstrated why a green unit-test build is necessary but not always sufficient for complete behavioral confidence.

## 9. Conclusion

Group 4 took a console contact manager from initial task allocation through modular implementation, integration challenges, file persistence, automated verification, and final acceptance-fix merging. The project was collaborative in both its implementation and its recovery from integration problems. Contributions were not identical, but the result depended on a combination of implementation, test coverage, reviews, coordination, and follow-up fixes.

The agreed core features are present on `main`; **52 JUnit tests passed in CI**, and **all 11 scripted acceptance scenarios passed** against the corrected application logic. The team can reasonably report the workshop application as completed for its intended educational scope, while transparently distinguishing automated acceptance evidence from a separately observed manual demonstration.

## Appendix A — Traceable repository evidence

- [Repository](https://github.com/JayB111/contact-app-workshop)
- [Original detailed team assignments — PR #1](https://github.com/JayB111/contact-app-workshop/pull/1)
- [Model and model tests — PR #5](https://github.com/JayB111/contact-app-workshop/pull/5)
- [Shared contracts, view, and Java 25 CI — PR #7](https://github.com/JayB111/contact-app-workshop/pull/7)
- [Controller, exception handler, and tests — PR #8](https://github.com/JayB111/contact-app-workshop/pull/8)
- [Documentation proposal, closed without merge — PR #9](https://github.com/JayB111/contact-app-workshop/pull/9)
- [Exception and DAO contract tests — PR #11](https://github.com/JayB111/contact-app-workshop/pull/11)
- [Revised work plan — PR #12](https://github.com/JayB111/contact-app-workshop/pull/12)
- [View tests — PR #13](https://github.com/JayB111/contact-app-workshop/pull/13)
- [File DAO implementation — PR #14](https://github.com/JayB111/contact-app-workshop/pull/14)
- [File DAO refinement — PR #15](https://github.com/JayB111/contact-app-workshop/pull/15)
- [Main entry point — PR #16](https://github.com/JayB111/contact-app-workshop/pull/16)
- [Final storage acceptance fixes — PR #17](https://github.com/JayB111/contact-app-workshop/pull/17)
- [Final merged build, 52 tests passing](https://github.com/JayB111/contact-app-workshop/actions/runs/45133250161)

