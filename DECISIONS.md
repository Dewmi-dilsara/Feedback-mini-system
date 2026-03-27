# Technical Decisions

## Architecture

Used layered architecture:

- Controller Layer
- Service Layer
- Repository Layer

This improves maintainability and separation of concerns.

---

## Database Choice

MongoDB was selected because:

- Flexible schema design
- Suitable for document-based storage
- Easy integration with Spring Boot

---

## Error Handling

Used GlobalExceptionHandler to:

- Standardize API responses
- Handle validation errors
- Return consistent messages

---

## Frontend Choice

Next.js was selected because:

- Simple routing
- Fast UI setup
- Easy API integration

---

## Testing Strategy

Used:

- JUnit 5
- Mockito

Tests cover:

- Service logic
- Controller endpoints
- Error scenarios

---

## Assumptions

During development, the following assumptions were made:

- Each feedback request belongs to a single enterprise.
- Rating values are restricted between 1 and 5.
- Feedback requests expire based on an expiration timestamp.
- Admin authentication is simplified or mocked for this exercise.
- One enterprise is sufficient for demonstration purposes.
- MongoDB is used as the persistence layer as suggested in the task description.

---

## Priorities

Due to the time constraint (4–6 hours), the following priorities were followed:

1. Implement core backend APIs first.
2. Ensure correct validation and error handling.
3. Build functional frontend pages.
4. Add seed data to enable quick testing.
5. Implement unit and controller tests.
6. Improve documentation clarity.

---

## Future Improvements

With additional development time, the following improvements could be added:

- Improve frontend UI styling and responsiveness.
- Add authentication and authorization for admin users.
- Add feedback analytics or statistics dashboard.
- Implement accessibility improvements.
- Add Swagger/OpenAPI documentation.
- Add Docker containerization for easier deployment.
- Improve automated frontend testing coverage.

---

## What Was Intentionally Left Out

To stay within the time limit, the following features were intentionally simplified:

- Advanced UI styling
- Production-grade authentication
- Analytics dashboards
- Deployment setup