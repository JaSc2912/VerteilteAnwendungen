# Bank-Client

This is the frontend client component of the distributed banking application, providing a web-based user interface for banking operations.

## Architecture

- **Technology Stack**: Jakarta EE 10, JSF 4.0, CDI, PrimeFaces, MicroProfile REST Client
- **Application Server**: GlassFish 7
- **Frontend Framework**: JSF (Jakarta Server Faces) with PrimeFaces
- **Build Tool**: Maven

## Features

### User Interface Pages

#### Authentication
- `index.xhtml` - Login page with user authentication
- Session management with automatic redirects

#### Dashboard
- `dashboard.xhtml` - Main overview page with quick access to all features
- Welcome screen for authenticated users

#### Customer Management
- `customers.xhtml` - Complete customer management interface
- Search, create, edit, and delete customers
- Responsive data table with pagination

#### Account Operations
- `accounts.xhtml` - Bank account management (in development)
- Account creation, transactions, and balance inquiries

#### Credit Management
- `credits.xhtml` - Credit application management (in development)
- Apply for loans, manage applications, approval workflow

## REST Client Integration

The client communicates with the Bank-Server backend via REST APIs:

### Service Interfaces
- `AuthRestClient` - Authentication operations
- `CustomerRestClient` - Customer management
- `AccountRestClient` - Account operations
- `CreditRestClient` - Credit applications

### Authentication
- Token-based authentication using MicroProfile REST Client
- Session management with automatic logout
- Authorization headers for all API calls

## Prerequisites

1. **Java 17** or higher
2. **Apache Maven 3.6+**
3. **GlassFish 7.0.24**
4. **Bank-Server** running on `http://localhost:8080/Bank-Server`

## Build and Deployment

1. **Build the project**:
   ```bash
   mvn clean package
   ```

2. **Deploy to GlassFish**:
   - Copy `target/Bank-Client.war` to GlassFish autodeploy directory
   - Or deploy via Admin Console at `http://localhost:4848`

3. **Access the application**:
   - URL: `http://localhost:8080/Bank-Client`
   - Default credentials: admin / adminpass

## Project Structure

```
src/main/java/de/hsnr/bank/client/
├── beans/                 # JSF Managed Beans
│   ├── AuthBean.java      # Authentication controller
│   └── CustomerBean.java  # Customer management controller
├── model/                 # Data models
│   ├── Kunde.java         # Customer model
│   ├── Bankkonto.java     # Account model
│   ├── Transaktion.java   # Transaction model
│   └── Kreditantrag.java  # Credit application model
└── services/              # REST Client Services
    ├── AuthRestClient.java      # Authentication API
    ├── CustomerRestClient.java  # Customer API
    ├── AccountRestClient.java   # Account API
    ├── CreditRestClient.java    # Credit API
    └── SessionService.java      # Session management

src/main/webapp/
├── templates/
│   └── layout.xhtml       # Main page template
├── resources/
│   └── css/
│       └── bank-style.css # Custom CSS styles
├── index.xhtml            # Login page
├── dashboard.xhtml        # Main dashboard
├── customers.xhtml        # Customer management
├── accounts.xhtml         # Account operations
├── credits.xhtml          # Credit management
└── WEB-INF/
    ├── web.xml           # Web application configuration
    └── beans.xml         # CDI configuration
```

## Development Features

### JSF Components
- PrimeFaces UI components for enhanced user experience
- Responsive design with modern CSS
- AJAX-enabled forms and data tables
- Dialog boxes for create/edit operations

### Session Management
- Automatic authentication checks
- Secure logout functionality
- Session timeout handling

### Error Handling
- User-friendly error messages
- Form validation
- REST API error handling

## Backend Integration

This client connects to the Bank-Server REST API:

### Authentication Endpoints
- `POST /api/auth/login` - User login
- `POST /api/auth/validate` - Token validation
- `POST /api/auth/logout` - User logout

### Customer Endpoints
- `GET /api/customers` - Get all customers
- `GET /api/customers/{id}` - Get customer by ID
- `GET /api/customers/search?q={term}` - Search customers
- `POST /api/customers` - Create customer
- `PUT /api/customers/{id}` - Update customer
- `DELETE /api/customers/{id}` - Delete customer

### Account Endpoints (planned)
- Account management and transactions
- Balance inquiries and transfers
- Transaction history

### Credit Endpoints (planned)
- Credit application management
- Approval/rejection workflow
- Interest rate calculations

## Usage

1. **Start Bank-Server** (backend must be running first)
2. **Deploy Bank-Client** to GlassFish
3. **Access application** at `http://localhost:8080/Bank-Client`
4. **Login** with demo credentials:
   - Username: `admin`
   - Password: `adminpass`
5. **Navigate** through the application using the top menu

## Development Status

### ✅ Completed Features
- Project structure and build configuration
- Authentication system with login/logout
- Session management
- Customer management (full CRUD operations)
- Responsive UI with PrimeFaces
- REST client integration
- Main navigation and dashboard

### 🚧 In Development
- Account management functionality
- Credit application system
- Advanced transaction features
- Enhanced error handling

### 📋 Planned Features
- User profile management
- Advanced search and filtering
- Reports and analytics
- Audit logging

## Troubleshooting

### Common Issues

1. **Login Failed**:
   - Ensure Bank-Server is running
   - Check network connectivity
   - Verify credentials (admin/adminpass)

2. **Page Not Loading**:
   - Check GlassFish deployment status
   - Verify WAR file deployment
   - Check server logs

3. **REST API Errors**:
   - Confirm Bank-Server is accessible
   - Check CORS configuration
   - Verify API endpoints

### Logs
- GlassFish logs: `%GLASSFISH_HOME%/domains/domain1/logs/server.log`
- Browser console for client-side errors

## Contributing

When adding new features:
1. Follow the established project structure
2. Use JSF best practices with PrimeFaces
3. Implement proper error handling
4. Add corresponding REST client calls
5. Update documentation

## License

This project is part of the distributed banking application system.
