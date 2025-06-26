package de.hsnr.bank.services.interfaces;

import de.hsnr.bank.entities.Kunde;
import jakarta.ejb.Local;
import java.util.List;

/**
 * Customer service interface
 */
@Local
public interface ICustomerService {

    /**
     * Find customer by customer number
     * 
     * @param kundennummer customer number
     * @return customer or null if not found
     */
    Kunde findByKundennummer(Long kundennummer);

    /**
     * Search customers by name or email
     * 
     * @param searchTerm search term
     * @return list of matching customers
     */
    List<Kunde> searchCustomers(String searchTerm);

    /**
     * Get all customers
     * 
     * @return list of all customers
     */
    List<Kunde> getAllCustomers();

    /**
     * Create new customer
     * 
     * @param kunde customer to create
     * @return created customer with ID
     */
    Kunde createCustomer(Kunde kunde);

    /**
     * Update existing customer
     * 
     * @param kunde customer to update
     * @return updated customer
     */
    Kunde updateCustomer(Kunde kunde);

    /**
     * Delete customer
     * 
     * @param kundennummer customer number to delete
     */
    void deleteCustomer(Long kundennummer);

    /**
     * Check customer creditworthiness
     * 
     * @param kundennummer customer number
     * @return true if customer is creditworthy
     */
    boolean isCustomerCreditworthy(Long kundennummer);

    /**
     * Calculate total balance for customer
     * 
     * @param kundennummer customer number
     * @return total balance across all accounts
     */
    java.math.BigDecimal getTotalCustomerBalance(Long kundennummer);
}
