package de.hsnr.bank.client.beans;

import de.hsnr.bank.client.model.Kunde;
import de.hsnr.bank.client.services.CustomerRestClient;
import de.hsnr.bank.client.services.SessionService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * JSF managed bean for customer operations
 */
@Named
@ViewScoped
public class CustomerBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(CustomerBean.class.getName());

    @Inject
    @RestClient
    private CustomerRestClient customerRestClient;

    @Inject
    private SessionService sessionService;

    private List<Kunde> customers = new ArrayList<>();
    private Kunde selectedCustomer = new Kunde();
    private String searchTerm;
    private boolean editMode = false;

    @PostConstruct
    public void init() {
        loadCustomers();
    }

    /**
     * Load all customers
     */
    public void loadCustomers() {
        try {
            if (sessionService.isLoggedIn()) {
                customers = customerRestClient.getAllCustomers(sessionService.getAuthorizationHeader());
            }
        } catch (Exception e) {
            LOGGER.warning("Error loading customers: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", "Kunden konnten nicht geladen werden"));
        }
    }

    /**
     * Search customers
     */
    public void searchCustomers() {
        try {
            if (sessionService.isLoggedIn() && searchTerm != null && !searchTerm.trim().isEmpty()) {
                customers = customerRestClient.searchCustomers(searchTerm.trim(),
                        sessionService.getAuthorizationHeader());
            } else {
                loadCustomers();
            }
        } catch (Exception e) {
            LOGGER.warning("Error searching customers: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", "Kundensuche fehlgeschlagen"));
        }
    }

    /**
     * Create new customer
     */
    public void createCustomer() {
        selectedCustomer = new Kunde();
        editMode = false;
    }

    /**
     * Edit selected customer
     */
    public void editCustomer(Kunde kunde) {
        selectedCustomer = new Kunde();
        selectedCustomer.setKundennummer(kunde.getKundennummer());
        selectedCustomer.setName(kunde.getName());
        selectedCustomer.setEmail(kunde.getEmail());
        selectedCustomer.setAdresse(kunde.getAdresse());
        selectedCustomer.setTelefonnummer(kunde.getTelefonnummer());
        selectedCustomer.setGeburtsdatum(kunde.getGeburtsdatum());
        selectedCustomer.setKundenstatus(kunde.getKundenstatus());
        editMode = true;
    }

    /**
     * Save customer
     */
    public void saveCustomer() {
        try {
            if (sessionService.isLoggedIn()) {
                Response response;

                if (editMode && selectedCustomer.getKundennummer() != null) {
                    response = customerRestClient.updateCustomer(
                            selectedCustomer.getKundennummer(),
                            selectedCustomer,
                            sessionService.getAuthorizationHeader());
                } else {
                    response = customerRestClient.createCustomer(
                            selectedCustomer,
                            sessionService.getAuthorizationHeader());
                }

                if (response.getStatus() == 200 || response.getStatus() == 201) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg",
                                    editMode ? "Kunde wurde aktualisiert" : "Kunde wurde erstellt"));

                    loadCustomers();
                    selectedCustomer = new Kunde();
                    editMode = false;
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler",
                                    "Kunde konnte nicht gespeichert werden"));
                }
            }
        } catch (Exception e) {
            LOGGER.warning("Error saving customer: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", "Kunde konnte nicht gespeichert werden"));
        }
    }

    /**
     * Delete customer
     */
    public void deleteCustomer(Kunde kunde) {
        try {
            if (sessionService.isLoggedIn()) {
                Response response = customerRestClient.deleteCustomer(
                        kunde.getKundennummer(),
                        sessionService.getAuthorizationHeader());

                if (response.getStatus() == 200) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg", "Kunde wurde gelöscht"));
                    loadCustomers();
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler",
                                    "Kunde konnte nicht gelöscht werden"));
                }
            }
        } catch (Exception e) {
            LOGGER.warning("Error deleting customer: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", "Kunde konnte nicht gelöscht werden"));
        }
    }

    // Getters and setters
    public List<Kunde> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Kunde> customers) {
        this.customers = customers;
    }

    public Kunde getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Kunde selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
}
