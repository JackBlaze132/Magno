# 🦅 Magno - Backend Service

The core logic and API services for the **Magno** Research Seedbeds Management System. Built with a robust architecture to ensure scalability, security, and maintainability.

## ⚙️ Backend Highlights

- **🏗️ Hexagonal Architecture:** Clear separation of domain, application, and infrastructure layers.
- **🛡️ Secure by Design:** Spring Security integration with Google OAuth2 for institutional safety.
- **📄 Document Automation:** Dynamic generation of PDF certificates and Excel reports using specialized handlers.
- **✅ Data Integrity:** Strict validation using Jakarta Persistence (JPA) and Hibernate.
- **🔄 Scheduled Tasks:** Automated management of project states and log rotations.

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA** (PostgreSQL)
- **Spring Security** (OAuth2/OIDC)
- **Gradle** (Build automation)
- **MapStruct** (Mapping)
- **Lombok** (Boilerplate reduction)

## 🚀 Getting Started

### Prerequisites

- Java 17+ installed.
- PostgreSQL instance running.

### Configuration

Create or update your `application.properties` or environment variables with the following:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/magno
spring.datasource.username=your_user
spring.datasource.password=your_password
integra.base.url=institutional_api_url
```

### Running the application

```bash
# Using the Gradle wrapper
./gradlew bootRun
```

The API will be accessible at `http://localhost:8080/api/`.

## 📂 Architecture Overview

- **`src/main/java/.../domain/`**: Pure business logic (Models, Ports, Use cases).
- **`src/main/java/.../application/`**: DTOs, Handlers, and orchestrators.
- **`src/main/java/.../infrastructure/`**: Adapters (Persistence, REST controllers, Security).

---

*Part of the Magno Ecosystem*
