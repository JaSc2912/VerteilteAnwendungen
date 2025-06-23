# Bank Application

This is a web-based banking management application built with Jakarta EE 10, JSF (PrimeFaces), and EJB. It provides administrative functionalities for managing users, customers, bank accounts, and credit applications.

## 1. How to Build and Deploy

Follow these steps to build the project and deploy it to a GlassFish server.

### Prerequisites

*   Java Development Kit (JDK) 11 or newer.
*   Apache Maven.
*   A running GlassFish Server (v6.x or v7.x recommended for Jakarta EE 10 support).

### Build Steps

1.  Open a terminal or command prompt in the project's root directory (`c:\Users\scja07\Documents\Werkstudent\Uni\VerteilteAnwendungen\Bank`).
2.  Run the following Maven command to clean the project and create the `.war` file:
    ```shell
    mvn clean package
    ```
3.  After a successful build, you will find the application archive at `target/Bank-1.0-SNAPSHOT.war`.

### Deployment to GlassFish

1.  Start your GlassFish server.
2.  Open the GlassFish Admin Console in your web browser (typically `http://localhost:4848`).
3.  Navigate to **Applications** from the menu on the left.
4.  Click the **Deploy...** button.
5.  On the "Deploy Applications or Modules" page, select "Packaged File to Be Uploaded to the Server", click **Browse...** (or "Choose File"), and select the `Bank-1.0-SNAPSHOT.war` file from your project's `target` directory.
6.  Click **OK** to deploy the application.

### Accessing the Application

Once deployed, the application's login page will be available at:
[http://localhost:8080/Bank-1.0-SNAPSHOT/login.xhtml](http://localhost:8080/Bank-1.0-SNAPSHOT/login.xhtml)

## 2. Application Overview

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

-- Note: The table and column names must match what JPA generates. These are examples.
-- The USER_TYPE column is the discriminator for the BenutzerEntity inheritance.

-- Create Users (Admin, Customer Service, Credit Employee)
INSERT INTO BENUTZERENTITY (USER_TYPE, BENUTZERNAME, NAME, PASSWORT, TELEFONNUMMER, ROLLE) VALUES ('ADMIN', 'admin', 'Administrator', 'adminpass', '0123456789', 'ADMIN');
INSERT INTO BENUTZERENTITY (USER_TYPE, BENUTZERNAME, NAME, PASSWORT, TELEFONNUMMER, ROLLE) VALUES ('KUNDENSERVICE', 'service01', 'Sarah Schmidt', 'servicepass', '0987654321', 'KUNDENSERVICE');
INSERT INTO MITARBEITERKREDITVERGABEENTITY (USER_TYPE, BENUTZERNAME, NAME, PASSWORT, TELEFONNUMMER, ROLLE, MITARBEITER_ID, ABTEILUNG) VALUES ('KREDITVERGABE', 'credit01', 'Peter Meier', 'creditpass', '01122334455', 'KREDITVERGABE', 'MK-789', 'Credit Department');

-- Create Customers
INSERT INTO KUNDEENTITY (KUNDENNUMMER, NAME, ADRESSE, KUNDENSTATUS, GEBURTSDATUM, TELEFONNUMMER, EMAIL) VALUES ('K-001', 'Max Mustermann', 'Musterstraße 1, 12345 Musterstadt', 'ACTIVE', '1985-05-20', '01511234567', 'max.mustermann@example.com');
INSERT INTO KUNDEENTITY (KUNDENNUMMER, NAME, ADRESSE, KUNDENSTATUS, GEBURTSDATUM, TELEFONNUMMER, EMAIL) VALUES ('K-002', 'Erika Mustermann', 'Beispielweg 2, 54321 Beispielhausen', 'ACTIVE', '1990-11-15', '01607654321', 'erika.mustermann@example.com');

-- Create Bank Accounts and link them to Customers
INSERT INTO BANKKONTOENTITY (IBAN, KONTOART, KONTOSTAND, KONTOEROEFFNUNGSDATUM, KONTOSTATUS, BESITZER_KUNDENNUMMER) VALUES ('DE89370400440532013000', 'Girokonto', 1500.75, '2020-01-10', 'ACTIVE', 'K-001');
INSERT INTO BANKKONTOENTITY (IBAN, KONTOART, KONTOSTAND, KONTOEROEFFNUNGSDATUM, KONTOSTATUS, BESITZER_KUNDENNUMMER) VALUES ('DE02100100100123456789', 'Sparkonto', 12500.00, '2021-03-15', 'ACTIVE', 'K-001');
INSERT INTO BANKKONTOENTITY (IBAN, KONTOART, KONTOSTAND, KONTOEROEFFNUNGSDATUM, KONTOSTATUS, BESITZER_KUNDENNUMMER) VALUES ('DE27100777770200283700', 'Girokonto', -250.50, '2019-08-01', 'ACTIVE', 'K-002');

-- Create a Credit Application
INSERT INTO KREDITANTRAGENTITY (KREDITSUMME, LAUFZEIT, ZINS, STATUS, KUNDE_ID) VALUES (10000.00, '48 Monate', 3.5, 'PENDING', 'K-002');

-- Create Transactions
INSERT INTO TRANSAKTIONENTITY (TRANSAKTIONSNUMMER, BANKKONTO, EMPFAENGER, BETRAG, TRANSAKTIONSDATUM, TRANSAKTIONSSTATUS, TRANSAKTIONSART) VALUES ('TXN-001', 'DE89370400440532013000', 'Stromversorger GmbH', 85.50, '2025-06-01', 'COMPLETED', 'AUSGANG');
INSERT INTO TRANSAKTIONENTITY (TRANSAKTIONSNUMMER, BANKKONTO, EMPFAENGER, BETRAG, TRANSAKTIONSDATUM, TRANSAKTIONSSTATUS, TRANSAKTIONSART) VALUES ('TXN-002', 'DE89370400440532013000', 'Arbeitgeber AG', 2800.00, '2025-05-30', 'COMPLETED', 'EINGANG');
INSERT INTO TRANSAKTIONENTITY (TRANSAKTIONSNUMMER, BANKKONTO, EMPFAENGER, BETRAG, TRANSAKTIONSDATUM, TRANSAKTIONSSTATUS, TRANSAKTIONSART) VALUES ('TXN-003', 'DE27100777770200283700', 'Miete', 750.00, '2025-06-01', 'COMPLETED', 'AUSGANG');
`````
