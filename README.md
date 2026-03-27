# Feedback Management System

## Overview

This project implements a mini feedback system for chat session feedback.

When a chat session ends, the customer can submit feedback by rating the interaction from 1 to 5 using a unique feedback link.

The system supports:

- Admin feedback form configuration
- Public feedback submission
- Expired feedback handling
- Already responded detection
- Validation rules
- REST API integration
- Frontend feedback UI
- Unit and controller testing


## Tech Stack

Backend:
- Kotlin
- Spring Boot
- MongoDB

Frontend:
- Next.js
- TypeScript

Testing:
- JUnit 5
- Mockito


## Project Structure

feedback/
│
├── src/
│   ├── main/kotlin/com/example/feedback
│   │   ├── controllers
│   │   ├── services
│   │   ├── repositories
│   │   └── models
│
│   └── test/kotlin/com/example/feedback
│       ├── FeedbackRequestServiceTest
│       └── PublicFeedbackControllerTest
│
├── frontend/
│
├── README.md
├── DECISIONS.md
├── AI_JOURNAL.md
│
├── build.gradle.kts
├── gradlew
└── settings.gradle.kts


## Prerequisites

Make sure you have installed:

- Java 21
- Node.js (version 18 or later)
- MongoDB running locally
- Git


## How to Run Backend

From project root folder:

gradlew bootRun

Backend runs at:

http://localhost:8080

After starting backend, terminal prints:

VALID FEEDBACK ID:
EXPIRED FEEDBACK ID:
RESPONDED FEEDBACK ID:

These IDs are used to test the public feedback page.


## How to Run Frontend

Open a new terminal:

cd frontend
npm install
npm run dev

Frontend runs at:

http://localhost:3000


## Public Feedback Page Usage

Open browser:

http://localhost:3000/feedback/{feedbackId}

Replace {feedbackId} with:

VALID FEEDBACK ID  
Expected:
- Shows rating buttons (1–5)
- After clicking rating → shows "Thank you" message


EXPIRED FEEDBACK ID  
Expected:
- Shows "This feedback link has expired."


RESPONDED FEEDBACK ID  
Expected:
- Shows "Already responded"


## Admin Feedback Configuration

Admin UI:

http://localhost:3000/admin

Features:

- Load existing feedback form
- Edit form fields
- Save form configuration
- Preview feedback UI


Form fields include:

- headerText
- headerDescription
- footerText
- ratingLabels (exactly 5 items)
- thankYouText
- invalidReplyText
- expiredReplyText
- skipForChannels


## Validation Rules

The following validation rules are applied:

- headerText → required
- ratingLabels → must contain exactly 5 items
- rating → must be between 1 and 5
- feedbackId → must exist
- expired feedback → cannot be submitted
- responded feedback → cannot be submitted again


## Run Tests

Run backend tests:

gradlew test

Expected result:

BUILD SUCCESSFUL

Test coverage includes:

- Service logic tests
- Controller endpoint tests
- Validation tests


## Seed Data

Demo data is automatically created at application startup.

It includes:

- One enterprise
- One valid feedback request
- One expired feedback request
- One already responded feedback request

This allows reviewers to test quickly.


## Sample API Endpoints

Admin APIs:

GET  /api/admin/enterprises/{enterpriseId}/session-feedback-form

PUT  /api/admin/enterprises/{enterpriseId}/session-feedback-form


Public APIs:

GET  /api/public/feedback/{feedbackId}

POST /api/public/feedback/{feedbackId}/respond


## Assumptions Made

- Each feedback request belongs to one enterprise.
- Rating values are restricted between 1 and 5.
- Feedback links expire after a defined expiration time.
- MongoDB is used as the persistence database.
- Admin authentication is simplified or mocked.
- One enterprise is sufficient for demo testing.


## Possible Future Improvements

With more development time, the following improvements could be added:

- Admin authentication system
- UI styling improvements
- Feedback analytics dashboard
- Accessibility improvements
- API documentation using Swagger
- Containerized setup using Docker


## Author

D.M.Dewmi dilsara bandara