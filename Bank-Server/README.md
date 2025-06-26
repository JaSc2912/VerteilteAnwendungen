# Bank-Server

This is the backend server component of the distributed banking application, providing REST APIs for banking operations.

## Architecture

- **Technology Stack**: Jakarta EE 10, EJB, JPA, REST (JAX-RS)
- **Application Server**: GlassFish 7
- **Database**: Apache Derby
- **Build Tool**: Maven

## Features

### REST API Endpoints

#### Authentication (`/api/auth`)
- `POST /login` - User authentication
- `POST /validate` - Token validation
- `POST /logout` - User logout

#### Customers (`/api/customers`)
- `GET /` - Get all customers
- `GET /{id}` - Get customer by ID
- `GET /search?q={term}` - Search customers
- `POST /` - Create new customer
- `PUT /{id}` - Update customer
- `DELETE /{id}` - Delete customer (mark as inactive)
- `GET /{id}/creditworthiness` - Check customer creditworthiness

#### Accounts (`/api/accounts`)
- `GET /{iban}` - Get account by IBAN
- `GET /customer/{id}` - Get accounts by customer
- `POST /` - Create new account
- `PUT /{iban}` - Update account
- `GET /{iban}/balance` - Get account balance
- `GET /{iban}/transactions` - Get account transactions
- `POST /transfer` - Transfer money between accounts
- `POST /{iban}/deposit` - Deposit money to account
- `POST /{iban}/withdraw` - Withdraw money from account

#### Credits (`/api/credits`)
- `GET /` - Get all credit applications
- `GET /{id}` - Get credit application by ID
- `GET /pending` - Get pending applications
- `GET /customer/{id}` - Get customer credit applications
- `POST /` - Create new credit application
- `PUT /{id}/approve` - Approve application
- `PUT /{id}/reject` - Reject application

## Prerequisites

1. **Java 17** or higher
2. **Apache Maven 3.6+**
3. **GlassFish 7.0.24**
4. **Apache Derby Database**

## Database Setup

1. **Start Derby Network Server**:
   ```bash
   cd %DERBY_HOME%
   java -jar lib/derbyrun.jar server start
   ```

2. **Create Database** (done automatically by JPA):
   - Database: `bankdb`
   - Host: `localhost:1527`
   - User: `bank`
   - Password: `bankpass`

## Build and Deployment

1. **Build the project**:
   ```bash
   mvn clean package
   ```

2. **Deploy to GlassFish**:
   - Copy `target/Bank-Server.war` to GlassFish autodeploy directory
   - Or deploy via Admin Console at `http://localhost:4848`

3. **Configure DataSource in GlassFish**:
   ```bash
   asadmin create-jdbc-connection-pool --datasourceclassname org.apache.derby.jdbc.ClientDataSource --restype javax.sql.DataSource --property User=bank:Password=bankpass:ServerName=localhost:PortNumber=1527:DatabaseName=bankdb BankPool
   asadmin create-jdbc-resource --connectionpoolid BankPool java:app/jdbc/BankDB
   ```

## API Testing

### Test Authentication
```bash
curl -X POST http://localhost:8080/Bank-Server/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"benutzername":"admin","passwort":"adminpass"}'
```

### Test Customer Operations
```bash
# Get all customers
curl -X GET http://localhost:8080/Bank-Server/api/customers \
  -H "Authorization: Bearer admin"

# Search customers
curl -X GET "http://localhost:8080/Bank-Server/api/customers/search?q=Mueller" \
  -H "Authorization: Bearer admin"

# Create customer
curl -X POST http://localhost:8080/Bank-Server/api/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer admin" \
  -d '{"name":"Max Mustermann","email":"max@example.com","adresse":"Musterstraße 1","telefonnummer":"0123456789"}'
```

### Test Account Operations
```bash
# Get account balance
curl -X GET http://localhost:8080/Bank-Server/api/accounts/DE89370400440532013000/balance \
  -H "Authorization: Bearer admin"

# Transfer money
curl -X POST http://localhost:8080/Bank-Server/api/accounts/transfer \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer admin" \
  -d '{"fromIban":"DE89370400440532013000","toIban":"DE89370400440532013001","amount":100.00,"verwendungszweck":"Test transfer"}'

# Deposit money
curl -X POST http://localhost:8080/Bank-Server/api/accounts/DE89370400440532013000/deposit \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer admin" \
  -d '{"amount":500.00}'
```

### Test Credit Operations
```bash
# Get pending credit applications
curl -X GET http://localhost:8080/Bank-Server/api/credits/pending \
  -H "Authorization: Bearer admin"

# Create credit application
curl -X POST http://localhost:8080/Bank-Server/api/credits \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer admin" \
  -d '{"kreditsumme":50000.00,"laufzeit":60,"verwendungszweck":"Hausfinanzierung","antragssteller":{"kundennummer":1}}'

# Approve credit application
curl -X PUT http://localhost:8080/Bank-Server/api/credits/1/approve \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer admin" \
  -d '{"bearbeiter":"admin"}'
```

## Development

### Project Structure
```
src/main/java/de/hsnr/bank/
├── entities/           # JPA Entities
├── services/           # EJB Business Logic
│   ├── interfaces/     # Service Interfaces
│   └── impl/          # Service Implementations
└── rest/              # REST Resources
    ├── resources/     # REST Endpoints
    └── filters/       # HTTP Filters

src/main/resources/
└── META-INF/
    ├── persistence.xml # JPA Configuration
    └── load.sql       # Initial Data

src/main/webapp/
└── WEB-INF/
    ├── web.xml        # Web Configuration
    └── beans.xml      # CDI Configuration
```

### Adding New Endpoints

1. Create service interface in `services/interfaces/`
2. Implement service in `services/impl/` with `@Stateless`
3. Create REST resource in `rest/resources/`
4. Test with curl or Postman

## Security

- Simple authentication using username/password
- Token-based authorization (username as token)
- CORS enabled for client communication
- Input validation using Bean Validation

## Database Schema

The application automatically creates the following tables:
- `BENUTZER` - System users
- `KUNDE` - Bank customers  
- `BANKKONTO` - Bank accounts
- `TRANSAKTION` - Transactions
- `KREDITANTRAG` - Credit applications

## Troubleshooting

### Common Issues

1. **Database Connection Error**:
   - Ensure Derby server is running
   - Check DataSource configuration in GlassFish

2. **Deployment Error**:
   - Check GlassFish logs: `domains/domain1/logs/server.log`
   - Verify Java 17 compatibility

3. **CORS Issues**:
   - Check CORSFilter configuration
   - Verify client origin URLs

### Logs
- GlassFish logs: `%GLASSFISH_HOME%/domains/domain1/logs/server.log`
- Application logs: Check console output

## Client Integration

This server is designed to work with the Bank-Client JSF application:
- Client URL: `http://localhost:8080/Bank-Client`
- Server URL: `http://localhost:8080/Bank-Server`
- API Base: `http://localhost:8080/Bank-Server/api`
