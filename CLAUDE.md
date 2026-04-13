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
docker compose -f infrastructure/docker-compose.yml up -d       # Start all services (Postgres, Mongo, LocalStack, backend, frontend)
docker compose -f infrastructure/docker-compose.yml build backend && docker compose -f infrastructure/docker-compose.yml up -d backend  # Rebuild + redeploy backend
docker compose -f infrastructure/docker-compose.yml down --remove-orphans  # Stop all
```

### Terraform (`infrastructure/terraform/`)
```bash
terraform init                        # Initialize providers/modules
terraform plan -var="environment=dev" # Preview changes
terraform apply -var="environment=dev" # Apply infrastructure
```

## Architecture

This is a React + Spring Boot application demonstrating layered/DDD architecture with dual databases (PostgreSQL for primary data, MongoDB for audit logs) and S3 for file storage.

### Backend Layer Structure

The backend follows a strict 5-layer DDD pattern (ports & adapters) under `backend/src/main/java/com/experiment/simple/`:

| Layer | Package | Responsibility |
|---|---|---|
| Domain | `domain/` | Business services (`Employee`, `Audit`), domain interfaces/ports (`PhotoStorage`) |
| Application | `application/dto/`, `application/mapper/` | Request/response DTOs (`AddEmployeeRequest`, `EmployeeResponse`, `DepartmentResponse`); MapStruct mappers |
| Web | `web/controller/` | REST controllers (`EmployeeController`, `HelloController`), `GlobalExceptionHandler` |
| Data | `data/entity/`, `data/repository/` | JPA entities, MongoDB documents, Spring Data repositories |
| Infrastructure | `infrastructure/` | AWS adapters (`S3PhotoStorage` implements `PhotoStorage` port), config (`S3Config`) |

**Key flow**: Web layer → Domain service → Application mapper (MapStruct) → Data layer (JPA/MongoDB)

**Ports & adapters**: Domain defines interfaces (e.g., `PhotoStorage`), infrastructure provides implementations (e.g., `S3PhotoStorage`). Domain has no dependency on AWS SDK.

### Database Strategy
- **PostgreSQL** (`localhost:5432`): Employees, departments, addresses via Spring Data JPA. Schema managed via `schema.sql` (Hibernate DDL set to `none`). Tables live in `company_schema`. Seed data in `data.sql` truncates and reseeds on every boot (`sql.init.mode=always`).
- **MongoDB** (`localhost:27017`): Audit log entries written on mutating operations. Uses Spring Data MongoDB with `AuditLog` document.

### S3 / Photo Storage
- Employee photos uploaded via `POST /api/employee/{id}/photo` (multipart, 10MB limit)
- LocalStack provides local S3 emulation in Docker Compose (port 4566)
- `S3Config` switches between LocalStack (custom endpoint + dummy creds) and real AWS (default credential chain) based on the `aws.s3.endpoint` property
- Bucket auto-created on startup via `S3PhotoStorage.ensureBucketExists()`

### API Endpoints
| Method | Path | Description |
|---|---|---|
| `GET` | `/hello` | Health check |
| `GET` | `/api/employee/{id}` | Get employee by ID |
| `POST` | `/api/employee` | Create employee (requires `firstName`, `lastName`, `salary`, `departmentId`) |
| `POST` | `/api/employee/{id}/photo` | Upload employee photo (multipart form, key: `file`) |

Swagger UI available at `/swagger-ui/index.html` (springdoc-openapi).

### Request Validation
- `AddEmployeeRequest` uses `@NotBlank`/`@NotNull` for required fields
- Controller uses `@Valid` — missing fields return 400 automatically
- `GlobalExceptionHandler` catches `IllegalArgumentException` (e.g., department not found) and returns 400

### Frontend–Backend Communication
- Frontend calls backend at `REACT_APP_API_URL` (defaults to `http://localhost:8080` via `frontend/.env`)
- CORS on the backend accepts `WEB_APP_FRONTEND_URL` env var

### Testing
- Backend tests use H2 in-memory database (no PostgreSQL/MongoDB/LocalStack needed)
- Backend: JUnit 5, `@WebMvcTest` + `@MockitoBean` for controller tests, `@ExtendWith(MockitoExtension.class)` for service tests
- Frontend: Jest + React Testing Library

### CI/CD Pipeline (`.github/workflows/`)
- **`ci.yml`**: Runs on push/PR. Tests backend + frontend in parallel. On `main`, builds Docker images and pushes to ECR with git SHA tag.
- **`cd.yml`**: Triggers on successful CI on `main`. Deploys to EKS via `helm upgrade --install`. Requires GitHub secrets for AWS role, ECR repos, EKS cluster name, namespace, and IRSA role ARN.

### Infrastructure as Code
- **Terraform** (`infrastructure/terraform/`): VPC, EKS cluster, ECR repos, S3 bucket (versioned, encrypted, public access blocked), IAM (IRSA for backend S3 access). Local state by default.
- **Helm** (`infrastructure/helm/web-app-enhanced/`): Chart with `values-dev.yaml` and `values-prod.yaml`. DB credentials via Kubernetes secrets. Ingress routes `/api` and `/swagger-ui` to backend, `/` to frontend.
- **Docker Compose** (`infrastructure/docker-compose.yml`): Local dev with Postgres 15, MongoDB 7, LocalStack 3, backend, frontend.
