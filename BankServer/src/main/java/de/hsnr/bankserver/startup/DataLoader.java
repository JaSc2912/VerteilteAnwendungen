package de.hsnr.bankserver.startup;

import de.hsnr.bankserver.dataaccess.entities.BenutzerEntity;
import de.hsnr.bankserver.dataaccess.entities.KundeEntity;
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
        } catch (Exception e) {
            System.err.println("Error loading initial data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
