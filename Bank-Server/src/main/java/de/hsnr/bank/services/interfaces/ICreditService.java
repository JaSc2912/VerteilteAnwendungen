package de.hsnr.bank.services.interfaces;

import de.hsnr.bank.entities.Kreditantrag;
import jakarta.ejb.Local;
import java.util.List;

/**
 * Credit service interface
 */
@Local
public interface ICreditService {

    /**
     * Find credit application by number
     * 
     * @param kreditantragsNummer credit application number
     * @return credit application or null if not found
     */
    Kreditantrag findByKreditantragsNummer(Long kreditantragsNummer);

    /**
     * Get all credit applications
     * 
     * @return list of all credit applications
     */
    List<Kreditantrag> getAllCreditApplications();

    /**
     * Get pending credit applications
     * 
     * @return list of pending credit applications
     */
    List<Kreditantrag> getPendingCreditApplications();

    /**
     * Get credit applications for customer
     * 
     * @param kundennummer customer number
     * @return list of customer credit applications
     */
    List<Kreditantrag> getCreditApplicationsByCustomer(Long kundennummer);

    /**
     * Create new credit application
     * 
     * @param kreditantrag credit application to create
     * @return created credit application
     */
    Kreditantrag createCreditApplication(Kreditantrag kreditantrag);

    /**
     * Approve credit application
     * 
     * @param kreditantragsNummer credit application number
     * @param bearbeiter          processor username
     * @return updated credit application
     */
    Kreditantrag approveCreditApplication(Long kreditantragsNummer, String bearbeiter);

    /**
     * Reject credit application
     * 
     * @param kreditantragsNummer credit application number
     * @param bearbeiter          processor username
     * @param begruendung         reason for rejection
     * @return updated credit application
     */
    Kreditantrag rejectCreditApplication(Long kreditantragsNummer, String bearbeiter, String begruendung);
}
