# NOTES

## How this project was built
Test-first (red, green, refactor) with a commit per step, using the prefixes from the brief: `test:`, `feat:`, `refactor:`, `confirm:`, `chore:`. The full history is on the `main` branch. Run the tests with the two commands in README.md.

Work done: `TimeSlot` (Part A), `Room`, `Booking`, `BookingService` rules R1 to R7 with boundary tests (Part B), `BookingService` refactored into one class per rule behind a `BookingRule` interface (Part C), and two new rules, D-1 (at most 3 active bookings) and D-2 (at most 14 days ahead) (Part D).

## Who did what
| Area | What the AI (Claude) did | What I did myself |
| ---- | ------------------------ | ----------------- |
| Planning | Read the brief, proposed the ordered list of red/green steps and commit messages. | Chose how to work (I type every step, the AI coaches) and followed or adjusted the plan. |
| Parts A and B | Proposed each test, each stub and the minimal code for each green step, and explained what each step tests. | Typed every test and every line of code, ran the build and tests after each step, saw each red fail before committing, and made every commit and push. |
| Part C | Proposed the order of small refactor steps and wrote the example classes (`BookingRule` and the five rule classes), following the structure in the brief. | Applied them one step at a time, ran all tests after each step and committed only when green. |
| Part D |  | Typed, ran and committed each step. |
| Set-up and git | Gave the commands. | Created the GitHub repo, set up the folders, `.gitignore` and `junit.jar`, and solved the problems listed below. |
| Understanding | Explained red, green and refactor and each step on request. | I am responsible for every line and will explain it in lab. |

## Gaps
| Rule | What was unclear | Ruling | My test for it |
| ---- | ---------------- | ------ | -------------- |
| R2 | Is a slot that starts exactly now (09:00-10:00 at 09:00) "in the future"? And a slot that started earlier but ends later? | No ruling yet. Implemented: rejected only if it starts strictly before now, so starting exactly now is allowed. | None yet. Related test: `bookingASlotInThePastIsRejected` (08:00-09:00). |
| R6 | Can a user cancel someone else's booking? | No ruling yet. Implemented: `cancel` only looks at the given user's own bookings, so cancelling another user's booking throws `IllegalArgumentException`. | None yet. Related tests: `cancellingABookingFreesTheSlot`, `cancellingABookingThatDoesNotExistIsRejected`. |
| D-1 | Does a booking that ends exactly now still count as "not yet ended"? | No ruling yet. Implemented: it counts while its end is strictly after now. | `aFourthActiveBookingIsRejected` (covers the basic rule only). |
| D-2 | "More than 14 days after the current date": compare dates or exact times? Is exactly 14 days allowed? | No ruling yet. Implemented: compare the slot's start date with today plus 14 days; exactly 14 days is allowed. | `aSlotMoreThanFourteenDaysAheadIsRejected`, `aSlotExactlyFourteenDaysAheadIsAllowed`. |

## AI corrections (at least three)
So far none of the AI's code or suggestions was wrong in a way one of my tests caught, so there is nothing real to record. I am not inventing entries. 

### 1
- What I asked:
- What it produced:
- What was wrong:
- The test that caught it:

### 2
- What I asked:
- What it produced:
- What was wrong:
- The test that caught it:

### 3
- What I asked:
- What it produced:
- What was wrong:
- The test that caught it:

## Problems I hit and fixed (my own process, not AI code errors)
- A `git push` was rejected because GitHub had added starter commits; I fixed it with `git pull --rebase`.
- One commit was labelled `feat:` but contained a new test; I changed it to `test:` with `git commit --amend` and a force-with-lease push.
- `TimeSlot.java` was first saved in `src/test/java` instead of `src/main/java`; moving it fixed the compile.
- A failed compile left old classes in `out/`, so a test run looked valid when it was not. I now delete `out/` before each run.
- VS Code showed red underlines for JUnit imports; adding `.vscode/settings.json` (not committed) fixed the editor, and the terminal commands were never affected.
- The first commits on `main` (`Initial commit`, etc.) were made on GitHub and have no prefix.
