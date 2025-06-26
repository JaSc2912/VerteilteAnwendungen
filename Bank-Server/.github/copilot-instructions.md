<!-- Use this file to provide workspace-specific custom instructions to Copilot. For more details, visit https://code.visualstudio.com/docs/copilot/copilot-customization#_use-a-githubcopilotinstructionsmd-file -->

# Bank-Server Development Guidelines

This is a Jakarta EE 10 backend server project for a distributed banking application. Follow these guidelines when working with this codebase:

## Technology Stack

- Jakarta EE 10 (EJB, JPA, JAX-RS)
- GlassFish 7 Application Server
- Apache Derby Database
- Maven Build System

## Code Style and Patterns

### EJB Services

- Use `@Stateless` for all business logic services
- Implement service interfaces in `services/interfaces/`
- Place implementations in `services/impl/`
- Use `@PersistenceContext(unitName = "BankPU")` for EntityManager injection
- Include proper logging with `java.util.logging.Logger`

### REST Resources

- Place all REST endpoints in `rest/resources/` package
- Use `@Path`, `@GET`, `@POST`, `@PUT`, `@DELETE` annotations
- Always consume and produce JSON: `@Consumes(MediaType.APPLICATION_JSON)` and `@Produces(MediaType.APPLICATION_JSON)`
- Return appropriate HTTP status codes
- Use DTOs for request/response objects
- Handle exceptions with proper error responses

### JPA Entities

- Use Jakarta Persistence annotations
- Include `@NamedQueries` for common database operations
- Use proper validation annotations from `jakarta.validation`
- Implement `toString()` methods for debugging
- Use `BigDecimal` for monetary amounts
- Use `LocalDate`/`LocalDateTime` for dates

### Database Operations

- Use named queries where possible
- Handle `NoResultException` appropriately
- Use transactions properly with `@Transactional` when needed
- Follow Derby SQL syntax (avoid MySQL/PostgreSQL specific features)

### Security

- Validate all inputs
- Never expose passwords in API responses
- Use proper error messages without revealing system details
- Implement role-based access control

### Error Handling

- Use appropriate exception types
- Log errors with sufficient detail
- Return user-friendly error messages
- Use HTTP status codes correctly (200, 400, 401, 404, 500)

## Package Structure

```
de.hsnr.bank.
├── entities/           # JPA entities
├── services/           # Business logic
│   ├── interfaces/     # Service contracts
│   └── impl/          # EJB implementations
└── rest/              # REST API layer
    ├── resources/     # REST endpoints
    └── filters/       # HTTP filters
```

## Naming Conventions

- Entities: Singular nouns (Kunde, Bankkonto)
- Services: Interface prefix 'I', implementation suffix 'Service'
- REST Resources: Suffix 'Resource'
- Database tables: Uppercase (KUNDE, BANKKONTO)
- JSON fields: camelCase

## Testing

- Test REST endpoints with curl or Postman
- Verify database operations work correctly
- Test error scenarios and edge cases
- Ensure CORS works for client communication

## Integration Points

- This server provides REST APIs for the Bank-Client JSF application
- All communication between client and server should use REST calls
- No direct database access from client
- Authentication tokens should be included in client requests
