
---

## Backend — Spring Boot

### Features

- REST API for managing clients:
  - `GET /api/legacy/clients` — Returns a list of legacy clients.
  - `POST /api/migrate/{id}` — Migrates a client to the new system.
  - `GET /api/new/clients` — Returns a list of migrated clients.
- In-memory storage (no database required)
- Simple error handling (e.g., migrating an already migrated client)
- Logs successful migrations

### Run Backend

1. Navigate to backend folder:

```bash
cd backend
```

2. Build the project:
```bash
./mvnw clean install
```
3. Run the application:

```bash
./mvnw spring-boot:run
```

4. The API runs on http://localhost:8080 by default.