-- Bank Database Initial Data for Derby
-- Compatible with Derby SQL syntax

-- Insert test users with different roles
INSERT INTO BENUTZER (BENUTZERNAME, PASSWORT, NAME, TELEFONNUMMER, ROLLE) VALUES 
('admin', 'adminpass', 'Administrator', '0211-12345', 'ADMIN'),
('service01', 'servicepass', 'Max Mustermann', '0211-23456', 'KUNDENSERVICE'),
('credit01', 'creditpass', 'Anna Schmidt', '0211-34567', 'KREDITVERGABE');

-- Insert test customers
INSERT INTO KUNDE (NAME, ADRESSE, TELEFONNUMMER, EMAIL, GEBURTSDATUM, KUNDENSTATUS) VALUES 
('Hans Mueller', 'Musterstraße 1, 40474 Düsseldorf', '0211-111111', 'hans.mueller@email.de', '1980-05-15', 'AKTIV'),
('Maria Fischer', 'Beispielweg 2, 40474 Düsseldorf', '0211-222222', 'maria.fischer@email.de', '1975-08-22', 'AKTIV'),
('Peter Wagner', 'Teststraße 3, 40474 Düsseldorf', '0211-333333', 'peter.wagner@email.de', '1990-03-10', 'AKTIV'),
('Anna Becker', 'Probestraße 4, 40474 Düsseldorf', '0211-444444', 'anna.becker@email.de', '1985-11-30', 'AKTIV'),
('Thomas Klein', 'Demostraße 5, 40474 Düsseldorf', '0211-555555', 'thomas.klein@email.de', '1970-12-05', 'GESPERRT');

-- Insert test bank accounts
INSERT INTO BANKKONTO (IBAN, KUNDENNUMMER, KONTOSTAND, KONTOSTATUS, KREDITLIMIT) VALUES 
('DE89370400440532013000', 1, 5000.00, 'AKTIV', 1000.00),
('DE89370400440532013001', 1, 1500.50, 'AKTIV', 500.00),
('DE89370400440532013002', 2, 3200.75, 'AKTIV', 2000.00),
('DE89370400440532013003', 3, 750.25, 'AKTIV', 0.00),
('DE89370400440532013004', 4, 10000.00, 'AKTIV', 5000.00),
('DE89370400440532013005', 5, 0.00, 'GESPERRT', 0.00);

-- Insert test transactions
INSERT INTO TRANSAKTION (IBAN, BETRAG, TRANSAKTIONSART, EMPFAENGER, VERWENDUNGSZWECK, TRANSAKTIONSDATUM, TRANSAKTIONSSTATUS) VALUES 
('DE89370400440532013000', -250.00, 'UEBERWEISUNG', 'Supermarkt XY', 'Einkauf', '2024-01-15 10:30:00', 'BEARBEITET'),
('DE89370400440532013000', 1000.00, 'EINZAHLUNG', 'Gehalt', 'Monatsgehalt Januar', '2024-01-01 08:00:00', 'BEARBEITET'),
('DE89370400440532013001', -50.00, 'ABHEBUNG', 'Geldautomat', 'Bargeld', '2024-01-10 14:15:00', 'BEARBEITET'),
('DE89370400440532013002', 500.00, 'UEBERWEISUNG', 'Hans Mueller', 'Rückzahlung', '2024-01-12 16:45:00', 'BEARBEITET'),
('DE89370400440532013003', -25.00, 'GEBUEHR', 'Bank', 'Kontoführungsgebühr', '2024-01-31 23:59:00', 'BEARBEITET');

-- Insert test credit applications
INSERT INTO KREDITANTRAG (KUNDENNUMMER, KREDITSUMME, LAUFZEIT, ZINSSATZ, ANTRAGSDATUM, STATUS, BEGRUENDUNG) VALUES 
(1, 25000.00, 60, 3.5, '2024-01-05', 'OFFEN', 'Autofinanzierung'),
(2, 150000.00, 240, 2.8, '2024-01-08', 'OFFEN', 'Immobilienfinanzierung'),
(3, 5000.00, 24, 4.2, '2024-01-10', 'GENEHMIGT', 'Renovierung'),
(4, 50000.00, 120, 3.2, '2024-01-03', 'ABGELEHNT', 'Unzureichende Bonität');
