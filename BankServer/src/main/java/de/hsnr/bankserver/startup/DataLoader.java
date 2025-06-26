package de.hsnr.bankserver.startup;

import de.hsnr.bankserver.dataaccess.entities.BenutzerEntity;
import de.hsnr.bankserver.core.entities.RolleT;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
            long count = entityManager.createQuery("SELECT COUNT(b) FROM BenutzerEntity b", Long.class)
                    .getSingleResult();

            if (count == 0) {
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

                entityManager.persist(admin);
                entityManager.persist(mitarbeiter);
                entityManager.persist(kunde);

                System.out.println("Initial test users loaded successfully!");
            } else {
                System.out.println("Data already exists, skipping initial load.");
            }
        } catch (Exception e) {
            System.err.println("Error loading initial data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
