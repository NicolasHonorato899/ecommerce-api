# H2 Database Setup for Local Testing

This document covers running your E-Commerce API with H2 database for quick request testing before deploying with Docker containers.

## Quick Start

Run H2 with in-memory database:

```bash
./run-h2.sh
```

Or use Gradle directly:

```bash
./gradlew bootRun --args='--spring.profiles.active=h2'
```

The application will start on `http://localhost:8080`

## H2 Console Access

Once running, access the H2 database console at:

**URL:** `http://localhost:8080/h2-console`

**Credentials:**
- JDBC URL: `jdbc:h2:mem:ecommerce;MODE=PostgreSQL`
- Username: `sa`
- Password: (empty)

The `MODE=PostgreSQL` setting ensures H2 understands PostgreSQL-compatible SQL syntax, making it compatible with your production queries.

## Configuration

### H2 Profile (`application-h2.properties`)

The H2 profile is pre-configured with:

- **In-memory database**: Data exists only during the session (resets on restart)
- **Auto schema creation**: Hibernate creates tables on startup (`ddl-auto=create`)
- **H2 console enabled**: Access via `/h2-console`
- **Same test credentials**: Uses the same defaults as main config for consistent testing

### Key Differences from Docker

| Aspect | H2 (Local) | Docker (Production) |
|--------|-----------|-------------------|
| Database Type | In-memory | PostgreSQL |
| Data Persistence | Session-only | Persistent volume |
| Startup Time | ~2-3 seconds | ~10-15 seconds |
| Use Case | Quick testing | Full integration testing |
| Configuration | `application-h2.properties` | `application.properties` |

## Testing Workflow

### 1. Start with H2

```bash
./run-h2.sh
```

### 2. Test API Endpoints

Use cURL, Postman, or your favorite HTTP client:

```bash
# Example: Create a user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com"}'
```

### 3. Inspect Data

Visit `http://localhost:8080/h2-console` to run SQL queries directly against the in-memory database.

### 4. Switch to Docker

When ready for full integration testing:

```bash
docker-compose up
./gradlew bootRun  # Uses default PostgreSQL from application.properties
```

## Security Notes for Testing

The H2 profile includes default test credentials:

- **Spring Security Username**: `user`
- **Spring Security Password**: `12345`
- **JWT Secret**: Shared with main config for testing

**⚠️ Never use these in production.** These are strictly for local development.

## Troubleshooting

### Application Won't Start

**Error**: `java.lang.ClassNotFoundException: org.h2.Driver`

**Solution**: Ensure `build.gradle` includes H2 dependency. It should already be present:
```gradle
implementation 'com.h2database:h2'
```

Then rebuild:
```bash
./gradlew clean build
```

### H2 Console Not Accessible

**Error**: 404 on `/h2-console`

**Solution**: Verify the H2 profile is active:
```bash
./gradlew bootRun --args='--spring.profiles.active=h2'
```

Check that `application-h2.properties` has:
```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### Data Disappears After Restart

**This is expected.** H2 in-memory mode (`jdbc:h2:mem:ecommerce`) stores data only during the session. For persistent local testing, switch to file-based H2:

Update `application-h2.properties`:
```properties
spring.datasource.url=jdbc:h2:file:./data/ecommerce;MODE=PostgreSQL
```

This creates an `./data/ecommerce.mv.db` file that persists between restarts. Clean up with:
```bash
rm -rf ./data/
```

### Port 8080 Already in Use

```bash
# Find process on port 8080
lsof -i :8080

# Kill it
kill -9 <PID>
```

Or specify a different port:
```bash
./gradlew bootRun --args='--spring.profiles.active=h2 --server.port=8081'
```

## Integration with Docker

Your workflow should be:

1. **Local H2 testing** → Quick iteration on endpoints
2. **Docker containers** → Full integration testing with PostgreSQL
3. **Deployment** → Production environment

The configuration files support both seamlessly:

```bash
# Local testing
./run-h2.sh

# Docker testing
docker-compose up
./gradlew bootRun
```

No code changes needed—Spring profiles handle the database switching automatically.
