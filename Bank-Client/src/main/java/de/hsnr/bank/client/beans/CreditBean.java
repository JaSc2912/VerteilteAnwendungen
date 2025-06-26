package de.hsnr.bank.client.beans;

import de.hsnr.bank.client.model.Kreditantrag;
import de.hsnr.bank.client.services.CreditRestClient;
import de.hsnr.bank.client.services.SessionService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * JSF managed bean for credit operations
 */
@Named
@ViewScoped
public class CreditBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(CreditBean.class.getName());

    @Inject
    @RestClient
    private CreditRestClient creditRestClient;

    @Inject
    private SessionService sessionService;

    // Credit applications
    private List<Kreditantrag> creditApplications = new ArrayList<>();
    private List<Kreditantrag> pendingApplications = new ArrayList<>();
    private List<Kreditantrag> approvedApplications = new ArrayList<>();
    private Kreditantrag selectedApplication;

    // New credit application
    private BigDecimal requestedAmount;
    private int termInMonths = 12;
    private String purpose;
    private BigDecimal monthlyIncome;
    private String employmentStatus;

    // Credit decision
    private String decisionReason;

    // UI state
    private boolean showCreateApplicationDialog = false;
    private boolean showApplicationDetailsDialog = false;
    private boolean showDecisionDialog = false;
    private String activeTab = "all"; // all, pending, approved

    @PostConstruct
    public void init() {
        loadCreditApplications();
    }

    /**
     * Load all credit applications for current customer
     */
    public void loadCreditApplications() {
        try {
            if (sessionService.isLoggedIn()) {
                String currentCustomerId = sessionService.getCurrentUserId();
                Response response = creditRestClient.getCreditApplicationsByCustomer(
                        Long.parseLong(currentCustomerId),
                        sessionService.getAuthorizationHeader());

                if (response.getStatus() == 200) {
                    // In a real implementation, you would parse the response
                    // For now, we'll simulate with empty lists
                    creditApplications = new ArrayList<>();
                    pendingApplications = new ArrayList<>();
                    approvedApplications = new ArrayList<>();
                    LOGGER.info("Credit applications loaded successfully");
                } else {
                    showError("Fehler beim Laden der Kreditanträge");
                }
            }
        } catch (Exception e) {
            LOGGER.warning("Error loading credit applications: " + e.getMessage());
            showError("Verbindungsfehler beim Laden der Kreditanträge");
        }
    }

    /**
     * Submit a new credit application
     */
    public void submitCreditApplication() {
        try {
            if (validateCreditApplication()) {
                String currentCustomerId = sessionService.getCurrentUserId();
                CreditRestClient.CreditApplicationRequest request = new CreditRestClient.CreditApplicationRequest(
                        Long.parseLong(currentCustomerId),
                        requestedAmount,
                        termInMonths,
                        purpose,
                        monthlyIncome,
                        employmentStatus);

                Response response = creditRestClient.submitCreditApplication(request,
                        sessionService.getAuthorizationHeader());

                if (response.getStatus() == 201) {
                    showSuccess("Kreditantrag erfolgreich eingereicht");
                    loadCreditApplications();
                    closeCreateApplicationDialog();
                } else {
                    showError("Fehler beim Einreichen des Kreditantrags");
                }
            }
        } catch (Exception e) {
            LOGGER.warning("Error submitting credit application: " + e.getMessage());
            showError("Verbindungsfehler beim Einreichen des Kreditantrags");
        }
    }

    /**
     * Approve a credit application (for bank employees)
     */
    public void approveCreditApplication() {
        try {
            if (selectedApplication != null) {
                CreditRestClient.ApprovalRequest request = new CreditRestClient.ApprovalRequest(decisionReason);

                Response response = creditRestClient.approveCreditApplication(
                        selectedApplication.getId(),
                        request,
                        sessionService.getAuthorizationHeader());

                if (response.getStatus() == 200) {
                    showSuccess("Kreditantrag genehmigt");
                    loadCreditApplications();
                    closeDecisionDialog();
                } else {
                    showError("Fehler bei der Genehmigung");
                }
            }
        } catch (Exception e) {
            LOGGER.warning("Error approving credit application: " + e.getMessage());
            showError("Verbindungsfehler bei der Genehmigung");
        }
    }

    /**
     * Reject a credit application (for bank employees)
     */
    public void rejectCreditApplication() {
        try {
            if (selectedApplication != null) {
                CreditRestClient.RejectionRequest request = new CreditRestClient.RejectionRequest(decisionReason);

                Response response = creditRestClient.rejectCreditApplication(
                        selectedApplication.getId(),
                        request,
                        sessionService.getAuthorizationHeader());

                if (response.getStatus() == 200) {
                    showSuccess("Kreditantrag abgelehnt");
                    loadCreditApplications();
                    closeDecisionDialog();
                } else {
                    showError("Fehler bei der Ablehnung");
                }
            }
        } catch (Exception e) {
            LOGGER.warning("Error rejecting credit application: " + e.getMessage());
            showError("Verbindungsfehler bei der Ablehnung");
        }
    }

    /**
     * Calculate estimated monthly payment
     */
    public BigDecimal calculateEstimatedPayment() {
        if (requestedAmount != null && termInMonths > 0) {
            // Simple calculation with 5% annual interest rate
            double monthlyRate = 0.05 / 12;
            double payment = requestedAmount.doubleValue() *
                    (monthlyRate * Math.pow(1 + monthlyRate, termInMonths)) /
                    (Math.pow(1 + monthlyRate, termInMonths) - 1);
            return BigDecimal.valueOf(Math.round(payment * 100.0) / 100.0);
        }
        return BigDecimal.ZERO;
    }

    /**
     * Validate credit application form
     */
    private boolean validateCreditApplication() {
        if (requestedAmount == null || requestedAmount.compareTo(BigDecimal.ZERO) <= 0) {
            showError("Bitte geben Sie einen gültigen Kreditbetrag ein");
            return false;
        }

        if (termInMonths <= 0 || termInMonths > 360) {
            showError("Laufzeit muss zwischen 1 und 360 Monaten liegen");
            return false;
        }

        if (purpose == null || purpose.trim().isEmpty()) {
            showError("Bitte geben Sie den Verwendungszweck an");
            return false;
        }

        if (monthlyIncome == null || monthlyIncome.compareTo(BigDecimal.ZERO) <= 0) {
            showError("Bitte geben Sie Ihr monatliches Einkommen an");
            return false;
        }

        if (employmentStatus == null || employmentStatus.trim().isEmpty()) {
            showError("Bitte geben Sie Ihren Beschäftigungsstatus an");
            return false;
        }

        return true;
    }

    // Dialog management methods
    public void openCreateApplicationDialog() {
        showCreateApplicationDialog = true;
    }

    public void closeCreateApplicationDialog() {
        showCreateApplicationDialog = false;
        requestedAmount = null;
        termInMonths = 12;
        purpose = null;
        monthlyIncome = null;
        employmentStatus = null;
    }

    public void openApplicationDetailsDialog(Kreditantrag application) {
        selectedApplication = application;
        showApplicationDetailsDialog = true;
    }

    public void closeApplicationDetailsDialog() {
        showApplicationDetailsDialog = false;
        selectedApplication = null;
    }

    public void openDecisionDialog(Kreditantrag application) {
        selectedApplication = application;
        decisionReason = null;
        showDecisionDialog = true;
    }

    public void closeDecisionDialog() {
        showDecisionDialog = false;
        selectedApplication = null;
        decisionReason = null;
    }

    /**
     * Filter applications by status
     */
    public List<Kreditantrag> getFilteredApplications() {
        switch (activeTab) {
            case "pending":
                return pendingApplications;
            case "approved":
                return approvedApplications;
            default:
                return creditApplications;
        }
    }

    private void showSuccess(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg", message));
    }

    private void showError(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", message));
    }

    // Getters and setters
    public List<Kreditantrag> getCreditApplications() {
        return creditApplications;
    }

    public void setCreditApplications(List<Kreditantrag> creditApplications) {
        this.creditApplications = creditApplications;
    }

    public List<Kreditantrag> getPendingApplications() {
        return pendingApplications;
    }

    public void setPendingApplications(List<Kreditantrag> pendingApplications) {
        this.pendingApplications = pendingApplications;
    }

    public List<Kreditantrag> getApprovedApplications() {
        return approvedApplications;
    }

    public void setApprovedApplications(List<Kreditantrag> approvedApplications) {
        this.approvedApplications = approvedApplications;
    }

    public Kreditantrag getSelectedApplication() {
        return selectedApplication;
    }

    public void setSelectedApplication(Kreditantrag selectedApplication) {
        this.selectedApplication = selectedApplication;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public int getTermInMonths() {
        return termInMonths;
    }

    public void setTermInMonths(int termInMonths) {
        this.termInMonths = termInMonths;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public String getDecisionReason() {
        return decisionReason;
    }

    public void setDecisionReason(String decisionReason) {
        this.decisionReason = decisionReason;
    }

    public boolean isShowCreateApplicationDialog() {
        return showCreateApplicationDialog;
    }

    public void setShowCreateApplicationDialog(boolean showCreateApplicationDialog) {
        this.showCreateApplicationDialog = showCreateApplicationDialog;
    }

    public boolean isShowApplicationDetailsDialog() {
        return showApplicationDetailsDialog;
    }

    public void setShowApplicationDetailsDialog(boolean showApplicationDetailsDialog) {
        this.showApplicationDetailsDialog = showApplicationDetailsDialog;
    }

    public boolean isShowDecisionDialog() {
        return showDecisionDialog;
    }

    public void setShowDecisionDialog(boolean showDecisionDialog) {
        this.showDecisionDialog = showDecisionDialog;
    }

    public String getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(String activeTab) {
        this.activeTab = activeTab;
    }
}
