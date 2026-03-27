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