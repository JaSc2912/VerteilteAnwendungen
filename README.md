# Bank Client-Server Anwendung

PS C:\Users\jannn\Downloads\glassfish-7.0.24\glassfish7\glassfish\bin> ./asadmin.bat start-domain

PS C:\Users\jannn> cd..
PS C:\Users> cd..
PS C:\> cd .\Apache
PS C:\Apache> CD .\db-derby-10.17.1.0-bin\
PS C:\Apache\db-derby-10.17.1.0-bin> cd .\db-derby-10.17.1.0-bin\
PS C:\Apache\db-derby-10.17.1.0-bin\db-derby-10.17.1.0-bin> cd .\lib\
PS C:\Apache\db-derby-10.17.1.0-bin\db-derby-10.17.1.0-bin\lib> java -jar .\derbyrun.jar server start





Diese Anwendung implementiert ein Banksystem mit einer Client-Server-Architektur basierend auf Jakarta EE.

## Projekte

### Bank (Original)
- Monolithische Anwendung aus einem Guss
- Vollständige Implementierung mit JSF und EJB

### BankServer (Neu)
- **Facade**: REST-Services für die Kommunikation mit dem Client
- **Core**: 
  - **Entities**: Business Objects (frei von JPA-Annotationen)
  - **Use Cases**: Geschäftslogik in Session Beans
- **DataAccess**: 
  - **Entities**: JPA-Entities für die Datenbankpersistierung
  - **DAO**: Data Access Objects für Datenbankzugriff

### BankClient (Neu)
- JSF-Anwendung mit Managed Beans
- Kommunikation mit Server über REST-Services
- Multipage-Anwendung mit rollenbasierter Navigation

## Neue Client-Server Architektur

### Benutzerrollen
- **Administrator**: Benutzerverwaltung
- **Kundenservice**: Kundendaten pflegen, Bankkonten verwalten, Kontotransaktionen, Kundenübersicht
- **Kreditbearbeiter**: Kreditanträge verwalten, Kundenauswertungen

### Implementierte Use Cases
1. **Benutzer verwalten** (Admin)
2. **Kundendaten pflegen** (Kundenservice)
3. **Kunden suchen** (Kundenservice)
4. **Bankkonten verwalten** (Kundenservice)
5. **Kontotransaktionen verwalten** (Kundenservice)
6. **Kreditanträge verwalten** (Kreditbearbeiter)
7. **Kunden auswerten** (Kreditbearbeiter)

### Zugriff auf neue Anwendung
- **Server**: http://localhost:8080/BankServer/api
- **Client**: http://localhost:8080/BankClient

### Testbenutzer
- **Admin**: admin / admin123
- **Kundenservice**: service1 / service123
- **Kreditbearbeiter**: kredit1 / kredit123

---

# Original Bank Application

This is a web-based banking management application built with Jakarta EE 10, JSF (PrimeFaces), and EJB. It provides administrative functionalities for managing users, customers, bank accounts, and credit applications.

## 1. Start Database and GlassFish

1. Ensure your Derby network database is running:
   ```powershell
   cd "%DERBY_HOME%\lib"
   java -jar derbyrun.jar server start
   ```
2. Start GlassFish domain:
   ```powershell
   cd "%GLASSFISH_HOME%\glassfish\bin"
   .\asadmin.bat start-domain domain1
   ```

## 2. Build the Application

1. Open a terminal in the project root (`Bank/`).
2. Run:
   ```shell
   mvn clean package
   ```
3. Artifact is generated at `target/Bank-1.0-SNAPSHOT.war`.

## 3. Deploy to GlassFish

1. In the Admin Console (`http://localhost:4848`), go to **Applications**.
2. Click **Deploy...**, choose "Packaged File", and select `target/Bank-1.0-SNAPSHOT.war`.
3. Click **OK**.

## 4. Access the Application

Navigate to:

```
http://localhost:8080/Bank-1.0-SNAPSHOT/login.xhtml
```

## 5. Application Overview

The application provides a user interface for common banking administrative tasks. It demonstrates a classic three-tier architecture with a presentation layer (JSF), a business logic layer (EJBs), and a data access layer (JPA).

### Key Features

*   **User Management**: Create, view, update, and delete system users with different roles (Admin, Customer Service, etc.).
*   **Customer Management**: Search for customers by name or ID, view their details, and edit their personal information.
*   **Account Management**: View and manage bank accounts associated with a customer.
*   **Transaction Viewing**: List all transactions for a specific bank account.
*   **Credit Application Processing**: Review, approve, or deny customer credit applications.
*   **Customer Evaluation**: Perform a simple creditworthiness check and view the total balance for a customer.

## 3. How to Use the Application

1.  **Login**: Navigate to the login page and use one of the sample users provided in the SQL script below (e.g., username `admin`, password `adminpass`).
2.  **Main Menu**: After a successful login, you will be directed to the main menu, which provides links to all major features.
3.  **Navigate**: Use the links on the menu to navigate to the different management pages. For example:
    *   Click **"Benutzer verwalten"** to go to the user administration page.
    *   Click **"Kunden suchen"** to find a customer and then navigate to their overview, data management, or account pages.

## 4. Sample Database Scripts (`load.sql`)

To make the application easier to test, you can use the following SQL scripts. Your `persistence.xml` is already configured to execute this file on deployment.

**Action**: Create a new file named `load.sql` inside the `src/main/resources/META-INF/` directory and paste the content below into it.

````sql
-- filepath: src/main/resources/META-INF/load.sql

-- DDL: Create tables for each entity (JPA will use these table names)
-- Note: Using DROP TABLE IF EXISTS and CREATE TABLE for Derby compatibility

-- 1. BENUTZERENTITY (SINGLE_TABLE inheritance for Admin, Kundenservice, MitarbeiterKreditvergabe)

CREATE TABLE BENUTZERENTITY (
  BENUTZERNAME      VARCHAR(50)   PRIMARY KEY,
  PASSWORT          VARCHAR(100),
  NAME              VARCHAR(100),
  TELEFONNUMMER     VARCHAR(20),
  ROLLE             VARCHAR(50),
  USER_TYPE         VARCHAR(50),   -- discriminator: ADMIN, KUNDENSERVICE, KREDITVERGABE
  MITARBEITER_ID    VARCHAR(50),   -- only for KREDITVERGABE
  ABTEILUNG         VARCHAR(100)   -- only for KREDITVERGABE
);

-- 2. KUNDEENTITY

CREATE TABLE KUNDEENTITY (
  KUNDENNUMMER      VARCHAR(20)   PRIMARY KEY,
  NAME              VARCHAR(100),
  ADRESSE           VARCHAR(255),
  KUNDENSTATUS      VARCHAR(50),
  GEBURTSDATUM      DATE,
  TELEFONNUMMER     VARCHAR(20),
  EMAIL             VARCHAR(100)
);

-- 3. BANKKONTOENTITY

CREATE TABLE BANKKONTOENTITY (
  IBAN                     VARCHAR(34)   PRIMARY KEY,
  KONTOART                 VARCHAR(50),
  KONTOSTAND               DOUBLE,
  KONTOEROEFFNUNGSDATUM    DATE,
  KONTOSTATUS              VARCHAR(50),
  BESITZER_KUNDENNUMMER    VARCHAR(20),
  CONSTRAINT FK_BANKKONTO_KUNDE FOREIGN KEY (BESITZER_KUNDENNUMMER)
    REFERENCES KUNDEENTITY(KUNDENNUMMER)
);

-- 4. KREDITANTRAGENTITY

CREATE TABLE KREDITANTRAGENTITY (
  KREDITANTRAGSNUMMER   BIGINT      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  KREDITSUMME           DOUBLE,
  LAUFZEIT              VARCHAR(50),
  ZINS                  DOUBLE,
  STATUS                VARCHAR(20),
  KUNDE_ID              VARCHAR(20),
  CONSTRAINT FK_KREDITANTRAG_KUNDE FOREIGN KEY (KUNDE_ID)
    REFERENCES KUNDEENTITY(KUNDENNUMMER)
);

-- 5. TRANSAKTIONENTITY

CREATE TABLE TRANSAKTIONENTITY (
  TRANSAKTIONSNUMMER    VARCHAR(50)   PRIMARY KEY,
  BANKKONTO_IBAN        VARCHAR(34),
  EMPFAENGER            VARCHAR(100),
  BETRAG                DOUBLE,
  TRANSAKTIONSDATUM     DATE,
  TRANSAKTIONSSTATUS    VARCHAR(20),
  TRANSAKTIONSART       VARCHAR(20),
  CONSTRAINT FK_TRANSAKTION_BANKKONTO FOREIGN KEY (BANKKONTO_IBAN)
    REFERENCES BANKKONTOENTITY(IBAN)
);


-- DML: Insert example values

-- BENUTZERENTITY
INSERT INTO BENUTZERENTITY (USER_TYPE, BENUTZERNAME, NAME, PASSWORT, TELEFONNUMMER, ROLLE)
  VALUES ('ADMIN', 'admin', 'Administrator', 'adminpass', '0123456789', 'ADMIN');
INSERT INTO BENUTZERENTITY (USER_TYPE, BENUTZERNAME, NAME, PASSWORT, TELEFONNUMMER, ROLLE)
  VALUES ('KUNDENSERVICE', 'service01', 'Sarah Schmidt', 'servicepass', '0987654321', 'KUNDENSERVICE');
INSERT INTO BENUTZERENTITY (USER_TYPE, BENUTZERNAME, NAME, PASSWORT, TELEFONNUMMER, ROLLE, MITARBEITER_ID, ABTEILUNG)
  VALUES ('KREDITVERGABE', 'credit01', 'Peter Meier', 'creditpass', '01122334455', 'KREDITVERGABE', 'MK-789', 'Credit Department');

-- KUNDEENTITY
INSERT INTO KUNDEENTITY (KUNDENNUMMER, NAME, ADRESSE, KUNDENSTATUS, GEBURTSDATUM, TELEFONNUMMER, EMAIL)
  VALUES ('K-001', 'Max Mustermann', 'Musterstraße 1, 12345 Musterstadt', 'ACTIVE',  '1985-05-20', '01511234567', 'max.mustermann@example.com');
INSERT INTO KUNDEENTITY (KUNDENNUMMER, NAME, ADRESSE, KUNDENSTATUS, GEBURTSDATUM, TELEFONNUMMER, EMAIL)
  VALUES ('K-002', 'Erika Mustermann', 'Beispielweg 2, 54321 Beispielhausen', 'ACTIVE',  '1990-11-15', '01607654321', 'erika.mustermann@example.com');

-- BANKKONTOENTITY
INSERT INTO BANKKONTOENTITY (IBAN, KONTOART, KONTOSTAND, KONTOEROEFFNUNGSDATUM, KONTOSTATUS, BESITZER_KUNDENNUMMER)
  VALUES ('DE89370400440532013000', 'Girokonto', 1500.75,  '2020-01-10', 'ACTIVE', 'K-001');
INSERT INTO BANKKONTOENTITY (IBAN, KONTOART, KONTOSTAND, KONTOEROEFFNUNGSDATUM, KONTOSTATUS, BESITZER_KUNDENNUMMER)
  VALUES ('DE02100100100123456789', 'Sparkonto', 12500.00,  '2021-03-15', 'ACTIVE', 'K-001');
INSERT INTO BANKKONTOENTITY (IBAN, KONTOART, KONTOSTAND, KONTOEROEFFNUNGSDATUM, KONTOSTATUS, BESITZER_KUNDENNUMMER)
  VALUES ('DE27100777770200283700', 'Girokonto', -250.50,  '2019-08-01', 'ACTIVE', 'K-002');

-- KREDITANTRAGENTITY (Note: KREDITANTRAGSNUMMER is auto-generated, so we don't specify it)
INSERT INTO KREDITANTRAGENTITY (KREDITSUMME, LAUFZEIT, ZINS, STATUS, KUNDE_ID)
  VALUES (10000.00, '48 Monate', 3.50, 'PENDING', 'K-002');

-- TRANSAKTIONENTITY (Note: foreign key column name is BANKKONTO_IBAN)
INSERT INTO TRANSAKTIONENTITY (TRANSAKTIONSNUMMER, BANKKONTO_IBAN, EMPFAENGER, BETRAG, TRANSAKTIONSDATUM, TRANSAKTIONSSTATUS, TRANSAKTIONSART)
  VALUES ('TXN-001', 'DE89370400440532013000', 'Stromversorger GmbH', 85.50,  '2025-06-01', 'COMPLETED', 'AUSGANG');
INSERT INTO TRANSAKTIONENTITY (TRANSAKTIONSNUMMER, BANKKONTO_IBAN, EMPFAENGER, BETRAG, TRANSAKTIONSDATUM, TRANSAKTIONSSTATUS, TRANSAKTIONSART)
  VALUES ('TXN-002', 'DE89370400440532013000', 'Arbeitgeber AG', 2800.00,  '2025-05-30', 'COMPLETED', 'EINGANG');
INSERT INTO TRANSAKTIONENTITY (TRANSAKTIONSNUMMER, BANKKONTO_IBAN, EMPFAENGER, BETRAG, TRANSAKTIONSDATUM, TRANSAKTIONSSTATUS, TRANSAKTIONSART)
  VALUES ('TXN-003', 'DE27100777770200283700', 'Miete', 750.00,  '2025-06-01', 'COMPLETED', 'AUSGANG');
````

### Derby SQL Compatibility Notes

**Important**: Derby does not support `CREATE TABLE IF NOT EXISTS` syntax. Instead, this script uses the `DROP TABLE IF EXISTS` followed by `CREATE TABLE` pattern, which is Derby-compatible and ensures clean table recreation on each deployment.

**Foreign Key Constraints**: Named constraints are used (e.g., `CONSTRAINT FK_BANKKONTO_KUNDE`) to make constraint management easier and avoid Derby's auto-generated constraint names.

**Deployment Workflow**:
1. Tables are dropped (if they exist) and recreated fresh on each deployment
2. Sample data is inserted to make testing easier
3. The `persistence.xml` is configured to execute this script via the `sql-load-script-source` property
