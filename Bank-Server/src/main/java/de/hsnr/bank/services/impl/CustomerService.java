package de.hsnr.bank.services.impl;

import de.hsnr.bank.entities.Kunde;
import de.hsnr.bank.services.interfaces.ICustomerService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

/**
 * Customer service EJB implementation
 */
@Stateless
public class CustomerService implements ICustomerService {

    private static final Logger LOGGER = Logger.getLogger(CustomerService.class.getName());

    @PersistenceContext(unitName = "BankPU")
    private EntityManager em;

    @Override
    public Kunde findByKundennummer(Long kundennummer) {
        LOGGER.info("Finding customer by number: " + kundennummer);
        try {
            return em.find(Kunde.class, kundennummer);
        } catch (Exception e) {
            LOGGER.warning("Error finding customer: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Kunde> searchCustomers(String searchTerm) {
        LOGGER.info("Searching customers with term: " + searchTerm);
        try {
            String pattern = "%" + searchTerm.toUpperCase() + "%";
            TypedQuery<Kunde> query = em.createQuery(
                    "SELECT k FROM Kunde k WHERE UPPER(k.name) LIKE :pattern OR UPPER(k.email) LIKE :pattern",
                    Kunde.class);
            query.setParameter("pattern", pattern);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.warning("Error searching customers: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public List<Kunde> getAllCustomers() {
        LOGGER.info("Getting all customers");
        try {
            TypedQuery<Kunde> query = em.createNamedQuery("Kunde.findAll", Kunde.class);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.warning("Error getting all customers: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public Kunde createCustomer(Kunde kunde) {
        LOGGER.info("Creating new customer: " + kunde.getName());
        try {
            kunde.setKundenstatus("AKTIV");
            em.persist(kunde);
            em.flush();
            LOGGER.info("Customer created with ID: " + kunde.getKundennummer());
            return kunde;
        } catch (Exception e) {
            LOGGER.severe("Error creating customer: " + e.getMessage());
            throw new RuntimeException("Failed to create customer", e);
        }
    }

    @Override
    public Kunde updateCustomer(Kunde kunde) {
        LOGGER.info("Updating customer: " + kunde.getKundennummer());
        try {
            Kunde existingKunde = em.find(Kunde.class, kunde.getKundennummer());
            if (existingKunde == null) {
                throw new RuntimeException("Customer not found: " + kunde.getKundennummer());
            }

            // Update fields
            existingKunde.setName(kunde.getName());
            existingKunde.setAdresse(kunde.getAdresse());
            existingKunde.setTelefonnummer(kunde.getTelefonnummer());
            existingKunde.setEmail(kunde.getEmail());
            existingKunde.setGeburtsdatum(kunde.getGeburtsdatum());
            existingKunde.setKundenstatus(kunde.getKundenstatus());

            em.merge(existingKunde);
            em.flush();
            LOGGER.info("Customer updated successfully");
            return existingKunde;
        } catch (Exception e) {
            LOGGER.severe("Error updating customer: " + e.getMessage());
            throw new RuntimeException("Failed to update customer", e);
        }
    }

    @Override
    public void deleteCustomer(Long kundennummer) {
        LOGGER.info("Deleting customer: " + kundennummer);
        try {
            Kunde kunde = em.find(Kunde.class, kundennummer);
            if (kunde != null) {
                // Set status to inactive instead of physical deletion
                kunde.setKundenstatus("GELOESCHT");
                em.merge(kunde);
                em.flush();
                LOGGER.info("Customer marked as deleted");
            }
        } catch (Exception e) {
            LOGGER.severe("Error deleting customer: " + e.getMessage());
            throw new RuntimeException("Failed to delete customer", e);
        }
    }

    @Override
    public boolean isCustomerCreditworthy(Long kundennummer) {
        LOGGER.info("Checking creditworthiness for customer: " + kundennummer);
        try {
            Kunde kunde = findByKundennummer(kundennummer);
            if (kunde == null || !"AKTIV".equals(kunde.getKundenstatus())) {
                return false;
            }

            // Simple creditworthiness check based on total balance
            BigDecimal totalBalance = getTotalCustomerBalance(kundennummer);
            return totalBalance.compareTo(BigDecimal.ZERO) >= 0;
        } catch (Exception e) {
            LOGGER.warning("Error checking creditworthiness: " + e.getMessage());
            return false;
        }
    }

    @Override
    public BigDecimal getTotalCustomerBalance(Long kundennummer) {
        LOGGER.info("Calculating total balance for customer: " + kundennummer);
        try {
            TypedQuery<BigDecimal> query = em.createQuery(
                    "SELECT COALESCE(SUM(b.kontostand), 0) FROM Bankkonto b WHERE b.kunde.kundennummer = :kundennummer AND b.kontostatus = 'AKTIV'",
                    BigDecimal.class);
            query.setParameter("kundennummer", kundennummer);

            BigDecimal result = query.getSingleResult();
            return result != null ? result : BigDecimal.ZERO;
        } catch (Exception e) {
            LOGGER.warning("Error calculating total balance: " + e.getMessage());
            return BigDecimal.ZERO;
        }
    }
}
