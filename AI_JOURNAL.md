# AI Usage Journal

## Tool Used

ChatGPT

---

## Purpose of AI Usage

AI was used as a development assistant to:

- Debug backend API logic
- Fix frontend API integration issues
- Resolve validation and exception handling problems
- Implement unit tests
- Create controller test cases
- Generate documentation templates

---

## Key Areas Where AI Helped

### Backend Development

AI assisted with:

- Implementing FeedbackRequestService logic
- Handling expired feedback cases
- Handling already responded feedback
- Fixing MongoDB ObjectId lookup logic
- Creating GlobalExceptionHandler

---

### Frontend Development

AI assisted with:

- Fetching feedback data from API
- Handling expired feedback UI
- Handling already responded UI
- Implementing rating submission logic
- Displaying success messages

---

### Testing

AI helped create:

- FeedbackRequestServiceTest
- PublicFeedbackControllerTest

These tests validated:

- Valid feedback submission
- Expired feedback behavior
- Duplicate response behavior
- Controller endpoints

---

## Debugging Support

AI was used to fix:

- 404 errors
- MongoDB lookup failures
- JSON response mismatches
- Unit test failures
- Exception handling issues

---

## Code Review Practice

All generated code was:

- Reviewed manually
- Adjusted to match project structure
- Tested locally before committing

---

## Learning Outcomes

Through AI-assisted development, I improved skills in:

- Spring Boot REST API development
- MongoDB integration
- Frontend-backend communication
- Error handling strategies
- Unit testing and controller testing
- Debugging real-world backend issues

---

## Responsible AI Usage

AI-generated content was used as:

- Guidance
- Debugging support
- Learning reference

All final code decisions were reviewed and validated manually.

---

## Representative Prompts Used

Below are examples of prompts used during development:

1. "Help me debug a 404 error when fetching feedback by feedbackId in Spring Boot."

2. "Generate a Next.js public feedback page that fetches feedback data and submits a rating."

3. "Write unit tests for FeedbackRequestService using Mockito."

4. "Fix validation errors when saving feedback form configuration."

5. "Help implement GlobalExceptionHandler for consistent API responses."

---

## AI Suggestion Rejected

One AI suggestion recommended using a more complex architecture with additional abstraction layers and DTO mapping for every model.

This approach was rejected because:

- It increased complexity unnecessarily.
- The assignment emphasized simplicity and practicality.
- A simpler layered architecture was sufficient for the scope.

Instead, a lightweight layered design was used to keep the solution maintainable and understandable.

---

## How AI Output Was Validated

AI-generated code was validated using the following methods:

- Manually reviewing generated code before applying it.
- Running backend and frontend locally to test behavior.
- Writing and running unit tests to verify logic correctness.
- Testing edge cases such as expired and already responded feedback.
- Comparing API responses with expected behavior.
- Fixing errors detected during testing rather than blindly trusting generated output.