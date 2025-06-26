package de.hsnr.bank.services.impl;

import de.hsnr.bank.entities.Kreditantrag;
import de.hsnr.bank.entities.Kunde;
import de.hsnr.bank.services.interfaces.ICreditService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

/**
 * Credit service EJB implementation
 */
@Stateless
public class CreditService implements ICreditService {

    private static final Logger LOGGER = Logger.getLogger(CreditService.class.getName());

    @PersistenceContext(unitName = "BankPU")
    private EntityManager em;

    @Override
    public Kreditantrag findByKreditantragsNummer(Long kreditantragsNummer) {
        LOGGER.info("Finding credit application by number: " + kreditantragsNummer);
        try {
            return em.find(Kreditantrag.class, kreditantragsNummer);
        } catch (Exception e) {
            LOGGER.warning("Error finding credit application: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Kreditantrag> getAllCreditApplications() {
        LOGGER.info("Getting all credit applications");
        try {
            TypedQuery<Kreditantrag> query = em.createNamedQuery("Kreditantrag.findAll", Kreditantrag.class);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.warning("Error getting all credit applications: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public List<Kreditantrag> getPendingCreditApplications() {
        LOGGER.info("Getting pending credit applications");
        try {
            TypedQuery<Kreditantrag> query = em.createNamedQuery("Kreditantrag.findPending", Kreditantrag.class);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.warning("Error getting pending credit applications: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public List<Kreditantrag> getCreditApplicationsByCustomer(Long kundennummer) {
        LOGGER.info("Getting credit applications for customer: " + kundennummer);
        try {
            Kunde kunde = em.find(Kunde.class, kundennummer);
            if (kunde == null) {
                return List.of();
            }

            TypedQuery<Kreditantrag> query = em.createNamedQuery("Kreditantrag.findByAntragssteller",
                    Kreditantrag.class);
            query.setParameter("antragssteller", kunde);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.warning("Error getting customer credit applications: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public Kreditantrag createCreditApplication(Kreditantrag kreditantrag) {
        LOGGER.info("Creating new credit application for customer: " +
                kreditantrag.getAntragssteller().getKundennummer());
        try {
            // Set default values
            kreditantrag.setAntragsdatum(LocalDate.now());
            kreditantrag.setStatus("OFFEN");

            // Validate customer exists
            Kunde kunde = em.find(Kunde.class, kreditantrag.getAntragssteller().getKundennummer());
            if (kunde == null) {
                throw new RuntimeException("Customer not found");
            }
            kreditantrag.setAntragssteller(kunde);

            em.persist(kreditantrag);
            em.flush();
            LOGGER.info("Credit application created with ID: " + kreditantrag.getKreditantragsNummer());
            return kreditantrag;
        } catch (Exception e) {
            LOGGER.severe("Error creating credit application: " + e.getMessage());
            throw new RuntimeException("Failed to create credit application", e);
        }
    }

    @Override
    public Kreditantrag approveCreditApplication(Long kreditantragsNummer, String bearbeiter) {
        LOGGER.info("Approving credit application: " + kreditantragsNummer + " by " + bearbeiter);
        try {
            Kreditantrag kreditantrag = findByKreditantragsNummer(kreditantragsNummer);
            if (kreditantrag == null) {
                throw new RuntimeException("Credit application not found: " + kreditantragsNummer);
            }

            if (!"OFFEN".equals(kreditantrag.getStatus())) {
                throw new RuntimeException("Credit application is not pending");
            }

            // Update status
            kreditantrag.setStatus("GENEHMIGT");
            kreditantrag.setBearbeiter(bearbeiter);
            kreditantrag.setBearbeitungsdatum(LocalDate.now());

            // Set interest rate based on amount and term (simple logic)
            if (kreditantrag.getZinssatz() == null) {
                kreditantrag.setZinssatz(calculateInterestRate(kreditantrag));
            }

            em.merge(kreditantrag);
            em.flush();

            LOGGER.info("Credit application approved successfully");
            return kreditantrag;
        } catch (Exception e) {
            LOGGER.severe("Error approving credit application: " + e.getMessage());
            throw new RuntimeException("Failed to approve credit application", e);
        }
    }

    @Override
    public Kreditantrag rejectCreditApplication(Long kreditantragsNummer, String bearbeiter, String begruendung) {
        LOGGER.info("Rejecting credit application: " + kreditantragsNummer + " by " + bearbeiter);
        try {
            Kreditantrag kreditantrag = findByKreditantragsNummer(kreditantragsNummer);
            if (kreditantrag == null) {
                throw new RuntimeException("Credit application not found: " + kreditantragsNummer);
            }

            if (!"OFFEN".equals(kreditantrag.getStatus())) {
                throw new RuntimeException("Credit application is not pending");
            }

            // Update status
            kreditantrag.setStatus("ABGELEHNT");
            kreditantrag.setBearbeiter(bearbeiter);
            kreditantrag.setBearbeitungsdatum(LocalDate.now());
            if (begruendung != null && !begruendung.trim().isEmpty()) {
                kreditantrag.setBegruendung(begruendung);
            }

            em.merge(kreditantrag);
            em.flush();

            LOGGER.info("Credit application rejected successfully");
            return kreditantrag;
        } catch (Exception e) {
            LOGGER.severe("Error rejecting credit application: " + e.getMessage());
            throw new RuntimeException("Failed to reject credit application", e);
        }
    }

    /**
     * Calculate interest rate based on credit amount and term
     * Simple logic for demonstration purposes
     */
    private java.math.BigDecimal calculateInterestRate(Kreditantrag kreditantrag) {
        java.math.BigDecimal baseRate = new java.math.BigDecimal("3.5");

        // Increase rate for higher amounts
        if (kreditantrag.getKreditsumme().compareTo(new java.math.BigDecimal("100000")) > 0) {
            baseRate = baseRate.add(new java.math.BigDecimal("0.5"));
        }

        // Increase rate for longer terms
        if (kreditantrag.getLaufzeit() != null && kreditantrag.getLaufzeit() > 120) {
            baseRate = baseRate.add(new java.math.BigDecimal("0.3"));
        }

        return baseRate;
    }
}
