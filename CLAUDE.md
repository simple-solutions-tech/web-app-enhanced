# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

### Frontend (`frontend/`)
```bash
npm start          # Dev server at localhost:3000
npm test           # Run tests (Jest + React Testing Library)
npm run build      # Production build
```

### Backend (`backend/`)
```bash
./gradlew bootRun  # Run Spring Boot app at localhost:8080
./gradlew test     # Run all JUnit 5 tests
./gradlew build    # Build the project
```
Run a single test class: `./gradlew test --tests "com.experiment.simple.SomeTest"`

### Infrastructure (`infrastructure/`)
```bash
docker-compose up -d   # Start PostgreSQL 15, MongoDB 7, backend, frontend
```

## Architecture

This is a React + Spring Boot application demonstrating layered/DDD architecture with dual databases (PostgreSQL for primary data, MongoDB for audit logs).

### Backend Layer Structure

The backend follows a strict 4-layer DDD pattern under `backend/src/main/java/com/experiment/simple/`:

| Layer | Package | Responsibility |
|---|---|---|
| Domain | `domain/` | Business entities (`Employee`, `Department`, `Address`) and audit service |
| Application | `application/dto/`, `application/mapper/` | Request DTOs; MapStruct mappers between domain and JPA entities |
| Web | `web/controller/` | REST controllers (`EmployeeController`, `HelloController`) |
| Data | `data/entity/`, `data/repository/` | JPA entities, MongoDB documents, Spring Data repositories |

**Key flow**: Web layer → Domain logic → Application mapper (MapStruct) → Data layer (JPA/MongoDB)

### Dual Database Strategy
- **PostgreSQL** (`localhost:5432`): Employees, departments, addresses via Spring Data JPA. Schema is managed manually via `backend/src/main/resources/schema.sql` (Hibernate DDL is set to `none`).
- **MongoDB** (`localhost:27017`): Audit log entries written on mutating operations (e.g., POST `/api/employee`). Uses Spring Data MongoDB with the `AuditLog` document and `AuditLogRepository`.

### Frontend–Backend Communication
- Frontend calls backend at `REACT_APP_API_URL` (defaults to `http://localhost:8080` via `frontend/.env`)
- CORS on the backend accepts `WEB_APP_FRONTEND_URL` env var
- Currently active endpoints: `GET /hello`, `GET /api/employee/{id}`, `POST /api/employee`

### Testing Database
- Backend tests use H2 in-memory database (no PostgreSQL/MongoDB needed to run tests)
