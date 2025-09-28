# Task Management Backend

## Project Description

This project is a backend API for a simple, single-user task management application.  
The backend is built using **Spring Boot** and exposes **RESTful APIs** to manage user authentication and task operations.

The backend handles:

- **User Authentication:** Registration and login of a single user.
- **Task Management:** CRUD (Create, Read, Update, Delete) operations for tasks.
- **Data Persistence:** Storing tasks in a relational database (H2/MySQL/PostgreSQL).

The backend serves as the foundation for a frontend application that will allow a registered user to manage their tasks through a user-friendly interface.

---

## Features

- User Registration & Login
- JWT-based Authentication (optional, if implemented)
- Task CRUD Operations:
  - Create new tasks
  - Read all tasks or a single task
  - Update task details
  - Delete tasks
- Validation & Error Handling

---

## Tech Stack

- **Backend:** Java, Spring Boot, Spring Security, Spring Data JPA
- **Database:** H2 (default in-memory), configurable for MySQL/PostgreSQL
- **Build Tool:** Maven
- **API Format:** RESTful, JSON-based

---

## Getting Started

### Prerequisites

- Java 17 or later
- Maven 3.6+
- Git
- (Optional) MySQL/PostgreSQL if you want to use a persistent database

### Installation & Setup

1. **Clone the repository**

```bash
git clone https://github.com/<your-username>/task-manager-backend.git
cd task-manager-backend

2. **Build and the project**
mvn clean install
mvn spring-boot:run

```
