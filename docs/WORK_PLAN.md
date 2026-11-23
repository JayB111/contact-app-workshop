# Group 4 — Contact App Workshop: Reconciled Work Plan

**Status:** Implemented and verified for the agreed workshop scope.

## 1. Which document governs member numbering?

The original specification in [Team-task-description.md](Team-task-description.md) remains the historical record of **assigned responsibilities and member numbers**. Later project documents were edited after responsibilities changed and temporarily renumbered members. **We do not renumber people retroactively.** Instead, the table below records actual implementation and test contributions alongside the original assignments.

## 2. Group 4 — Assigned versus delivered responsibilities

| Original no. | Contributor | Original task | Final confirmed contributions |
|---|---|---|---|
| **1** | Jay (`@JayB111`) | `Contact` and validation tests | Model and 8 tests, [PR #5](https://github.com/JayB111/contact-app-workshop/pull/5); revised work plan, [PR #12](https://github.com/JayB111/contact-app-workshop/pull/12); repository coordination and merging. |
| **2** | Padma (`@Padmaqaauto`) | `ContactDAO` interface and checked exceptions | Dedicated DAO/exception tests [#11](https://github.com/JayB111/contact-app-workshop/pull/11); view tests [#13](https://github.com/JayB111/contact-app-workshop/pull/13); final `Main` [#16](https://github.com/JayB111/contact-app-workshop/pull/16). |
| **3** | Carl (`@Matojotam`) | File DAO and tests | File-based DAO and persistence tests [#14](https://github.com/JayB111/contact-app-workshop/pull/14); directory-error fix [#15](https://github.com/JayB111/contact-app-workshop/pull/15). |
| **4** | Veselina (`@vlinakonsa-svg`) | `ContactView` | Veselina was unavailable during the workshop. Tuku implemented the assigned view component (PR #7), and Padma added its dedicated JUnit tests (PR #13). The team redistributed this work to maintain progress. |
| **5** | Anas (`@jackaldzn-blip`) | `ContactController` and `ExceptionHandler` | Controller, error handling, and tests [#8](https://github.com/JayB111/contact-app-workshop/pull/8); acceptance tests and final storage fixes/tests [#17](https://github.com/JayB111/contact-app-workshop/pull/17); separate proposed documentation PR #9 was closed without merge. |
| **6** | Tuku (`@tukue`) | Startup, integration, tests, README | Original detailed task breakdown [#1](https://github.com/JayB111/contact-app-workshop/pull/1); DAO contract, exceptions, view, and GitHub Actions CI [#7](https://github.com/JayB111/contact-app-workshop/pull/7). The final startup implementation was contributed by Padma. |

Only repository-verifiable delivery is attributed to a specific merged PR. Code review, discussion, learning assistance, and other informal work may not appear in PR files.

## 3. Agreed functional and method contracts

| Subject | Contract |
|---|---|
| Name | Non-blank, trimmed in the `Contact` model |
| Phone number | Exactly ten digits (`^\d{10}$`) |
| Duplicates | Match names case-insensitively; reject with `DuplicateContactException` |
| Missing file | `findAll()` returns an empty contact list |
| Search | `findByName()` returns a `Contact` or `null`, ignoring case |
| Invalid stored record | `ContactStorageException` with error context |
| Invalid user input | `IllegalArgumentException`, mapped to user-readable message |
| File format | `name|phoneNumber`; separator is final `|` in the record |
| Entry point | `se.lexicon.controller.Main` creates DAO, view, controller and starts the menu |
| Menu options | 1 list, 2 add, 3 search, 4 exit |

See [ARCHITECTURE.md](ARCHITECTURE.md) for the actual class structure and contracts.

## 4. Integration and review history

- **Kickoff (Thursday, 19 November, 09:30 CET):** the six-member group agreed on task allocation, shared interfaces, and parallel development. Original task specification PR #1, model PR #5, and CI/shared-contract/view work PR #7 followed.
- **Core integration (Friday, 20 November):** tests PRs #11 and #13, controller PR #8, work-plan PR #12, file DAO PRs #14–15, and entry-point PR #16 were integrated. The first 49 JUnit tests passed, while scripted acceptance identified two file-storage edge cases.
- **Final verification (Monday, 23 November):** PR #17 was merged with storage fixes and regression tests. The final `main` build passed 52 tests, and the corrected application passed all 11 scripted acceptance scenarios.

## 5. Completion and testing checklist

- [x] Model validation and representation.
- [x] View, controller, and four-option console menu.
- [x] DAO interface and file-backed persistence.
- [x] Main entry point launches complete application.
- [x] Duplicate, input, and malformed-storage error handling.
- [x] 52 JUnit tests pass in final merged GitHub Actions run.
- [x] All 11 scripted console acceptance scenarios pass against corrected logic.
- [x] Documentation reconciled for original assignments and changed responsibilities (in this proposed documentation update).
- [ ] Independently recorded human-operated acceptance session against final merged commit (not confirmed in reviewed evidence).

**CI evidence:** [Final main run](https://github.com/JayB111/contact-app-workshop/actions/runs/45133250161), JDK 25, `mvn clean verify`; Maven targets Java 17 compatibility. An IntelliJ test-source configuration problem reported on one team member's computer is separate from the passing Maven and command-line workflows.

## 6. Development lessons

The workshop highlighted the value of shared method contracts before parallel development, CI on every PR, writing tests at the boundary between layers, keeping acceptance scenarios separate from unit tests, and preserving both *assigned* and *actual* work in the team record. For a balanced narrative and full evidence links, see [FINAL_TEAM_REPORT.md](FINAL_TEAM_REPORT.md).
