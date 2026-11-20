# Contact App Workshop — Team Work Plan

## 1. Team Members

| Member | GitHub |
|---|---|
| Jay — Member 1 | @JayB111 |
| Veselina — Member 2 | @vlinakonsa-svg |
| Tuku — Member 3 | @tukue |
| Anas — Member 4 | @jackaldzn-blip |
| Carl — Member 5 | @Matojotam |
| Padma — Member 6 | @Padmaqaauto |

## 2. Current Task Responsibilities

### Member 1 — Jay: Contact Model
- Create the Contact class.
- Add name and phone number fields.
- Implement validation.
- Add getters, setters and toString.
- Write unit tests for the Contact model.

### Member 2 — Veselina: User Interface
- Original responsibility: ContactView.
- Currently unable to participate due to illness.
- Her assigned work is being covered by Padma.

### Member 3 — Tuku: Main, Integration, Custom Exceptions and DAO Interface- Completed Custom Exceptions.
- Completed the ContactDAO interface.
- Took over Padma's original responsibilities.

### Member 4 — Anas: Controller and Exception Handling
- Create ContactController.
- Implement the central ExceptionHandler.
- Connect the view and data layer.
- Coordinate application operations.

### Member 5 — Carl: File Storage
- Implement FileContactDAOImpl.
- Read contacts from a file.
- Save contacts to a file.
- Handle file-related errors.
- Write tests for file storage.

### Member 6 — Padma: User Interface
- Took over Veselina's ContactView responsibilities due to her illness.
- Continue the user interface implementation.

## 3. Shared Responsibilities

All team members will contribute to:
- Reviewing the design.
- Testing and integration.
- Code reviews.
- Fixing integration issues.
- Making sure the application works as a whole.

## 4. Suggested Workflow

1. Discuss the application design together.
2. Agree on responsibilities and how the components communicate.
3. Work on separate Git branches.
4. Commit regularly.
5. Create pull requests for review.
6. Test the integrated application.

## 5. Questions to Agree On Before Coding

- What makes two contacts duplicates?
- What happens if the contact file does not exist?
- What happens if a contact cannot be found?
- What phone number format should we accept?
- Which methods should use our custom exceptions?

## 6. Goal

Our goal is to work together, divide the responsibilities fairly, and build an application where all components fit together correctly.

## 7. Additional Work and Integration

Tuku has also taken over Padma's original tasks after completing his own work. The team is coordinating the remaining integration work as needed.