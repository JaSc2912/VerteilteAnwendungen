package de.hsnr.bankserver.startup;

import de.hsnr.bankserver.dataaccess.entities.BenutzerEntity;
import de.hsnr.bankserver.dataaccess.entities.KundeEntity;
import de.hsnr.bankserver.dataaccess.entities.KreditantragEntity;
import de.hsnr.bankserver.dataaccess.entities.BankkontoEntity;
import de.hsnr.bankserver.dataaccess.entities.TransaktionEntity;
import de.hsnr.bankserver.core.entities.RolleT;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Date;
import java.util.Calendar;

/**
 * Startup bean to load initial data into the database
 */
@Singleton
@Startup
public class DataLoader {

    @PersistenceContext(unitName = "BankPU")
    private EntityManager entityManager;

    @PostConstruct
    public void loadInitialData() {
        try {
            // Check if data already exists
            long benutzerCount = entityManager.createQuery("SELECT COUNT(b) FROM BenutzerEntity b", Long.class)
                    .getSingleResult();
            long kundenCount = entityManager.createQuery("SELECT COUNT(k) FROM KundeEntity k", Long.class)
                    .getSingleResult();

            if (benutzerCount == 0) {
                // Load initial test users
                BenutzerEntity admin = new BenutzerEntity();
                admin.setBenutzername("admin");
                admin.setPasswort("admin123");
                admin.setRolle(RolleT.ADMIN);
                admin.setName("Administrator");
                admin.setTelefonnummer("0123456789");

                BenutzerEntity mitarbeiter = new BenutzerEntity();
                mitarbeiter.setBenutzername("mitarbeiter");
                mitarbeiter.setPasswort("mitarbeiter123");
                mitarbeiter.setRolle(RolleT.KUNDENSERVICE);
                mitarbeiter.setName("Bank Mitarbeiter");
                mitarbeiter.setTelefonnummer("0987654321");

                BenutzerEntity kunde = new BenutzerEntity();
                kunde.setBenutzername("kunde");
                kunde.setPasswort("kunde123");
                kunde.setRolle(RolleT.KREDITBEARBEITER);
                kunde.setName("Test Kunde");
                kunde.setTelefonnummer("0555123456");

                BenutzerEntity service1 = new BenutzerEntity();
                service1.setBenutzername("service1");
                service1.setPasswort("service123");
                service1.setRolle(RolleT.KUNDENSERVICE);
                service1.setName("Service User 1");
                service1.setTelefonnummer("0211999888");

                BenutzerEntity kredit1 = new BenutzerEntity();
                kredit1.setBenutzername("kredit1");
                kredit1.setPasswort("kredit123");
                kredit1.setRolle(RolleT.KREDITBEARBEITER);
                kredit1.setName("Kredit Bearbeiter 1");
                kredit1.setTelefonnummer("0211777666");

                entityManager.persist(admin);
                entityManager.persist(mitarbeiter);
                entityManager.persist(kunde);
                entityManager.persist(service1);
                entityManager.persist(kredit1);

                System.out.println("Initial test users loaded successfully!");
            } else {
                System.out.println("Users already exist, skipping user load.");
            }

            if (kundenCount == 0) {
                // Load initial test customers
                Calendar cal = Calendar.getInstance();

                // Customer 1 - Peter
                cal.set(1985, Calendar.MARCH, 15);
                KundeEntity peter = new KundeEntity();
                peter.setKundennummer("K001");
                peter.setName("Peter Müller");
                peter.setAdresse("Musterstraße 1, 41234 Düsseldorf");
                peter.setTelefonnummer("0211123456");
                peter.setEmail("peter.mueller@email.de");
                peter.setGeburtsdatum(cal.getTime());
                peter.setKundenstatus("AKTIV");

                // Customer 2 - Anna
                cal.set(1990, Calendar.JULY, 22);
                KundeEntity anna = new KundeEntity();
                anna.setKundennummer("K002");
                anna.setName("Anna Schmidt");
                anna.setAdresse("Hauptstraße 45, 41238 Mönchengladbach");
                anna.setTelefonnummer("02161987654");
                anna.setEmail("anna.schmidt@email.de");
                anna.setGeburtsdatum(cal.getTime());
                anna.setKundenstatus("AKTIV");

                // Customer 3 - Max
                cal.set(1978, Calendar.DECEMBER, 3);
                KundeEntity max = new KundeEntity();
                max.setKundennummer("K003");
                max.setName("Max Petersen");
                max.setAdresse("Bergstraße 12, 41239 Mönchengladbach");
                max.setTelefonnummer("02161555777");
                max.setEmail("max.petersen@email.de");
                max.setGeburtsdatum(cal.getTime());
                max.setKundenstatus("AKTIV");

                entityManager.persist(peter);
                entityManager.persist(anna);
                entityManager.persist(max);

                System.out.println("Initial test customers loaded successfully!");
            } else {
                System.out.println("Customers already exist, skipping customer load.");
            }

            // Load initial bank accounts
            Long bankkontenCount = entityManager.createQuery("SELECT COUNT(b) FROM BankkontoEntity b", Long.class)
                    .getSingleResult();

            if (bankkontenCount == 0) {
                // Bank Account 1 - Peter Schmidt Girokonto
                BankkontoEntity account1 = new BankkontoEntity();
                account1.setIban("DE89370400440532013000");
                account1.setKontoinhaber("K001");
                account1.setKontoart("Girokonto");
                account1.setKontostand(2500.50);
                Calendar cal1 = Calendar.getInstance();
                cal1.set(2020, Calendar.JANUARY, 15);
                account1.setKontoeröffnungsdatum(cal1.getTime());
                account1.setKontostatus("aktiv");

                // Bank Account 2 - Peter Schmidt Sparkonto
                BankkontoEntity account2 = new BankkontoEntity();
                account2.setIban("DE89370400440532013001");
                account2.setKontoinhaber("K001");
                account2.setKontoart("Sparkonto");
                account2.setKontostand(15000.00);
                account2.setKontoeröffnungsdatum(cal1.getTime());
                account2.setKontostatus("aktiv");

                // Bank Account 3 - Maria Garcia Girokonto
                BankkontoEntity account3 = new BankkontoEntity();
                account3.setIban("DE89370400440532013002");
                account3.setKontoinhaber("K002");
                account3.setKontoart("Girokonto");
                account3.setKontostand(1200.75);
                Calendar cal2 = Calendar.getInstance();
                cal2.set(2021, Calendar.MARCH, 10);
                account3.setKontoeröffnungsdatum(cal2.getTime());
                account3.setKontostatus("aktiv");

                // Bank Account 4 - Hans Weber Geschäftskonto
                BankkontoEntity account4 = new BankkontoEntity();
                account4.setIban("DE89370400440532013003");
                account4.setKontoinhaber("K003");
                account4.setKontoart("Geschäftskonto");
                account4.setKontostand(-500.00);
                Calendar cal3 = Calendar.getInstance();
                cal3.set(2019, Calendar.NOVEMBER, 20);
                account4.setKontoeröffnungsdatum(cal3.getTime());
                account4.setKontostatus("gesperrt");

                entityManager.persist(account1);
                entityManager.persist(account2);
                entityManager.persist(account3);
                entityManager.persist(account4);

                System.out.println("Initial test bank accounts loaded successfully!");
            } else {
                System.out.println("Bank accounts already exist, skipping bank account load.");
            }

            // Load initial transactions
            Long transaktionenCount = entityManager.createQuery("SELECT COUNT(t) FROM TransaktionEntity t", Long.class)
                    .getSingleResult();

            if (transaktionenCount == 0) {
                // Transaction 1 - Salary deposit
                TransaktionEntity trans1 = new TransaktionEntity();
                trans1.setTransaktionsnummer("T001");
                trans1.setKonto("DE89370400440532013000");
                Calendar transDate1 = Calendar.getInstance();
                transDate1.set(2024, Calendar.JUNE, 1, 10, 30, 0);
                trans1.setTransaktionsdatum(transDate1.getTime());
                trans1.setBetrag(2000.00);
                trans1.setTransaktionsart("Einzahlung");
                trans1.setEmpfänger("Gehaltseingang");
                trans1.setTransaktionsstatus("abgeschlossen");

                // Transaction 2 - Utility payment
                TransaktionEntity trans2 = new TransaktionEntity();
                trans2.setTransaktionsnummer("T002");
                trans2.setKonto("DE89370400440532013000");
                Calendar transDate2 = Calendar.getInstance();
                transDate2.set(2024, Calendar.JUNE, 2, 14, 15, 0);
                trans2.setTransaktionsdatum(transDate2.getTime());
                trans2.setBetrag(-150.00);
                trans2.setTransaktionsart("Überweisung");
                trans2.setEmpfänger("Stadtwerke Düsseldorf");
                trans2.setTransaktionsstatus("abgeschlossen");

                // Transaction 3 - Transfer from parents
                TransaktionEntity trans3 = new TransaktionEntity();
                trans3.setTransaktionsnummer("T003");
                trans3.setKonto("DE89370400440532013002");
                Calendar transDate3 = Calendar.getInstance();
                transDate3.set(2024, Calendar.JUNE, 3, 9, 45, 0);
                trans3.setTransaktionsdatum(transDate3.getTime());
                trans3.setBetrag(500.00);
                trans3.setTransaktionsart("Einzahlung");
                trans3.setEmpfänger("Überweisung von Eltern");
                trans3.setTransaktionsstatus("abgeschlossen");

                entityManager.persist(trans1);
                entityManager.persist(trans2);
                entityManager.persist(trans3);

                System.out.println("Initial test transactions loaded successfully!");
            } else {
                System.out.println("Transactions already exist, skipping transaction load.");
            }

            // Load initial credit applications
            Long kreditantraegeCount = entityManager
                    .createQuery("SELECT COUNT(k) FROM KreditantragEntity k", Long.class).getSingleResult();

            if (kreditantraegeCount == 0) {
                // Credit Application 1 - In Processing
                KreditantragEntity ka001 = new KreditantragEntity();
                ka001.setKreditantragsnummer("KA001");
                ka001.setAntragsteller("K001");
                ka001.setKreditsumme(25000.00);
                ka001.setKreditlaufzeit("60 Monate");
                ka001.setKreditstatus("in Bearbeitung");
                ka001.setGenehmigenderMitarbeiter(null);

                // Credit Application 2 - Approved
                KreditantragEntity ka002 = new KreditantragEntity();
                ka002.setKreditantragsnummer("KA002");
                ka002.setAntragsteller("K002");
                ka002.setKreditsumme(10000.00);
                ka002.setKreditlaufzeit("36 Monate");
                ka002.setKreditstatus("genehmigt");
                ka002.setGenehmigenderMitarbeiter("kredit1");

                // Credit Application 3 - Rejected
                KreditantragEntity ka003 = new KreditantragEntity();
                ka003.setKreditantragsnummer("KA003");
                ka003.setAntragsteller("K003");
                ka003.setKreditsumme(50000.00);
                ka003.setKreditlaufzeit("84 Monate");
                ka003.setKreditstatus("abgelehnt");
                ka003.setGenehmigenderMitarbeiter(null);

                entityManager.persist(ka001);
                entityManager.persist(ka002);
                entityManager.persist(ka003);

                System.out.println("Initial test credit applications loaded successfully!");
            } else {
                System.out.println("Credit applications already exist, skipping credit application load.");
            }
        } catch (Exception e) {
            System.err.println("Error loading initial data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
