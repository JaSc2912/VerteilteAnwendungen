<!-- Use this file to provide workspace-specific custom instructions to Copilot. For more details, visit https://code.visualstudio.com/docs/copilot/copilot-customization#_use-a-githubcopilotinstructionsmd-file -->

# Bank-Client Project Instructions

This is a JSF (Jakarta Server Faces) web application that serves as the frontend client for a distributed banking system.

## Project Context

- **Technology Stack**: Jakarta EE 10, JSF 4.0, CDI, PrimeFaces, MicroProfile REST Client
- **Purpose**: Frontend web application for banking operations
- **Backend Communication**: Communicates with Bank-Server via REST APIs
- **Application Server**: GlassFish 7

## Code Generation Guidelines

### JSF Managed Beans

- Use `@Named` and `@ViewScoped` for page-specific beans
- Use `@ApplicationScoped` for service beans
- Follow naming convention: `*Bean` for managed beans
- Implement proper error handling and user feedback

### JSF Pages (XHTML)

- Use PrimeFaces components for enhanced UI (p:dataTable, p:inputText, etc.)
- Implement responsive design with PrimeFaces Grid system
- Use proper templating with ui:composition and ui:define
- Include proper form validation and error messages

### REST Client Services

- Use MicroProfile REST Client with `@RegisterRestClient`
- Create interfaces that mirror the Bank-Server REST endpoints
- Implement proper exception handling for REST calls
- Use CDI `@Inject` for dependency injection

### Navigation

- Use JSF 2.3+ navigation features
- Implement page redirects after successful operations
- Use flash scope for success/error messages across redirects

### Security

- Implement session management for user authentication
- Use CDI producers for current user context
- Implement proper logout functionality

### Data Models

- Create POJOs that match the Bank-Server entity structure
- Use proper validation annotations
- Implement serializable interfaces for session storage

## File Organization

- Managed beans: `src/main/java/de/hsnr/bank/client/beans/`
- REST clients: `src/main/java/de/hsnr/bank/client/services/`
- Models: `src/main/java/de/hsnr/bank/client/model/`
- Pages: `src/main/webapp/`
- Templates: `src/main/webapp/templates/`
- Resources: `src/main/webapp/resources/`

## Bank-Server REST API Endpoints

The client should integrate with these Bank-Server endpoints:

- Authentication: `/api/auth/*`
- Customers: `/api/customers/*`
- Accounts: `/api/accounts/*`
- Credits: `/api/credits/*`

Base URL: `http://localhost:8080/Bank-Server`
