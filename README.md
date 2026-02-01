# 📚 Course Platform Backend API

A **production-ready backend REST API** for a course learning platform built using **Spring Boot**, **PostgreSQL**, and **JWT-based authentication**.

This project enables users to **browse courses**, **search educational content**, **enroll in courses**, and **track learning progress at subtopic level**.  
All functionality is exposed via **REST APIs** and fully documented using **Swagger / OpenAPI**, allowing the entire system to be tested without any frontend.

> 🎯 This project was developed as part of an academic + internship-oriented backend system to demonstrate real-world backend engineering practices.

---

## 📌 Table of Contents

- [Core Features](#core-features)
- [System Architecture](#system-architecture)
- [Technology Stack](#technology-stack)
- [Authentication & Security](#authentication--security)
- [Course & Content Model](#course--content-model)
- [Enrollment System](#enrollment-system)
- [Progress Tracking System](#progress-tracking-system)
- [Search Implementation](#search-implementation)
- [Error Handling Strategy](#error-handling-strategy)
- [API Documentation (Swagger)](#api-documentation-swagger)
- [Configuration & Environment Variables](#configuration--environment-variables)
- [Seed Data](#seed-data)
- [Deployment](#deployment)
- [What Is Intentionally Not Implemented](#what-is-intentionally-not-implemented)
- [Elasticsearch (Optional / Bonus)](#elasticsearch-optional--bonus)
- [Evaluation Readiness](#evaluation-readiness)
- [Author](#author)

---

## 🚀 Core Features

### 🌍 Public (No Authentication Required)
- Fetch all courses
- Fetch course details with topics & subtopics
- Keyword-based search across:
    - Course title
    - Course description
    - Topic titles
    - Subtopic titles
    - Subtopic markdown content

---

### 🔐 Authentication
- User registration
- User login
- Stateless authentication using **JWT**
- Secure endpoints protected via Spring Security
- JWT authorization supported directly in Swagger UI

---

### 📘 Enrollment
- Enroll a user into a course
- Prevent duplicate enrollments
- Fetch all enrollments for the logged-in user
- Enrollment ownership is strictly enforced

---

### 📈 Progress Tracking
- Mark a **subtopic as completed**
- Completion allowed **only if the user is enrolled**
- Operation is **idempotent**
- Track progress per enrollment:
    - Total subtopics
    - Completed subtopics
    - Completion percentage
    - Completion timestamps

---

## 🧱 System Architecture

This project follows a **layered architecture**:


Controller → Service → Repository → Database
Separation of Concerns

Controller: Handles HTTP requests & responses

Service: Contains business logic

Repository: Handles database access

DTOs: Prevent entity over-exposure

Entities: Pure persistence models

🛠 Technology Stack
Layer	Technology
Language	Java 21
Framework	Spring Boot
ORM	Hibernate / JPA
Database	PostgreSQL
Security	Spring Security + JWT
API Docs	Swagger / OpenAPI
Build Tool	Maven
Deployment	Railway
🔐 Authentication & Security
JWT Design

Stateless authentication

Token contains:

User email as subject

Issued time

Expiration time

Token validated on every secured request

Security Flow

User logs in → JWT generated

Client sends Authorization: Bearer <token>

JWT filter validates token

User identity resolved from token

Access granted or denied

Why This Design?

Scalable

No server-side sessions

Industry-standard approach

📚 Course & Content Model
Hierarchy
Course
 └── Topic
      └── Subtopic (Markdown Content)

Key Points

Course content is read-only

Loaded via seed data

No admin or CRUD APIs (by design)

🎓 Enrollment System
Rules Enforced

A user can enroll only once in a course

Enrollment is required for:

Progress tracking

Subtopic completion

Enrollment ownership is checked on every request

Available APIs

Enroll in course

View my enrollments

📊 Progress Tracking System
Subtopic Completion

Users can mark a subtopic as completed

Completion is:

Linked to enrollment

Stored once (idempotent)

Timestamped

Progress View

For a given enrollment:

Course info

Total subtopics

Completed subtopics

Completion percentage

Completed subtopics with timestamps

This ensures accurate, per-course learning tracking.

🔍 Search Implementation
Default (Implemented)

PostgreSQL-based keyword search

Case-insensitive

Partial matching using LIKE

Searches across:

Course

Topic

Subtopic

Content

Why PostgreSQL Search?

Simple

Reliable

No external dependency

Easy deployment

❗ Error Handling Strategy

All errors follow a consistent JSON structure:

{
  "error": "Error Type",
  "message": "Human-readable message",
  "timestamp": "2025-12-21T10:30:00Z"
}

Supported Status Codes

200 OK

201 Created

400 Bad Request

401 Unauthorized

403 Forbidden

404 Not Found

409 Conflict

Handled via:

Global exception handler

Custom exception classes

📄 API Documentation (Swagger)

Swagger UI is enabled

JWT authorization supported inside Swagger

Entire system can be tested via Swagger alone

📌 Swagger URL:

/swagger-ui.html

⚙️ Configuration & Environment Variables

All sensitive and environment-specific values are externalized.

Required Environment Variables
DB_URL=jdbc:postgresql://localhost:5432/course_platform
DB_USERNAME=postgres
DB_PASSWORD=your_password

JWT_SECRET=your_secure_256bit_secret
JWT_EXPIRATION=86400

DDL_AUTO=update


📌 This makes the application production-ready and deployment-friendly.

🌱 Seed Data

Course, topic, and subtopic data is auto-loaded

Runs only if tables are empty

Ensures reviewer can test immediately

Content is consistent and deterministic

🚀 Deployment

Deployed on Railway

PostgreSQL database provisioned

Environment variables managed via Railway dashboard

Swagger UI enabled in deployed environment

❌ What Is Intentionally Not Implemented

To keep the scope focused, the following are excluded:

Admin panel

Course CRUD APIs

Topic/Subtopic CRUD APIs

User profile management

Email verification

Password reset

Role-based access control

This keeps the project aligned with core backend learning goals.

🔎 Elasticsearch (Optional / Bonus)
Current Status

Elasticsearch configuration files are present in the resources directory

This is optional and not required to run the application

PostgreSQL search is used by default

Purpose

Demonstrates extensibility

Allows future enhancement without redesign

Aligns with bonus evaluation criteria

✅ Evaluation Readiness

This project demonstrates:

Backend architecture

Authentication & authorization

Database modeling

Business logic enforcement

Error handling

Production readiness

Swagger-based testing

Designed specifically for academic review + internship evaluation.

👤 Author

Pritam Kumar Branwal
Master of Computer Applications (MCA)
Backend Developer – Java & Spring Boot