package de.hsnr.bank.client.services;

import de.hsnr.bank.client.model.Kreditantrag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

/**
 * REST client interface for credit operations
 */
@RegisterRestClient(baseUri = "http://localhost:8080/Bank-Server/api")
@Path("/credits")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface CreditRestClient {

    /**
     * Get all credit applications
     */
    @GET
    List<Kreditantrag> getAllCreditApplications(@HeaderParam("Authorization") String token);

    /**
     * Get credit application by ID
     */
    @GET
    @Path("/{id}")
    Kreditantrag getCreditApplication(@PathParam("id") Long kreditantragsNummer,
            @HeaderParam("Authorization") String token);

    /**
     * Get pending credit applications
     */
    @GET
    @Path("/pending")
    List<Kreditantrag> getPendingCreditApplications(@HeaderParam("Authorization") String token);

    /**
     * Get credit applications for customer
     */
    @GET
    @Path("/customer/{customerId}")
    Response getCreditApplicationsByCustomer(@PathParam("customerId") Long kundennummer,
            @HeaderParam("Authorization") String token);

    /**
     * Submit new credit application
     */
    @POST
    Response submitCreditApplication(CreditApplicationRequest request, @HeaderParam("Authorization") String token);

    /**
     * Approve credit application
     */
    @PUT
    @Path("/{id}/approve")
    Response approveCreditApplication(@PathParam("id") Long kreditantragsNummer, ApprovalRequest request,
            @HeaderParam("Authorization") String token);

    /**
     * Reject credit application
     */
    @PUT
    @Path("/{id}/reject")
    Response rejectCreditApplication(@PathParam("id") Long kreditantragsNummer, RejectionRequest request,
            @HeaderParam("Authorization") String token);

    // Request DTOs
    public static class CreditApplicationRequest {
        private Long customerId;
        private java.math.BigDecimal amount;
        private int termInMonths;
        private String purpose;
        private java.math.BigDecimal monthlyIncome;
        private String employmentStatus;

        public CreditApplicationRequest() {
        }

        public CreditApplicationRequest(Long customerId, java.math.BigDecimal amount, int termInMonths,
                String purpose, java.math.BigDecimal monthlyIncome, String employmentStatus) {
            this.customerId = customerId;
            this.amount = amount;
            this.termInMonths = termInMonths;
            this.purpose = purpose;
            this.monthlyIncome = monthlyIncome;
            this.employmentStatus = employmentStatus;
        }

        public Long getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Long customerId) {
            this.customerId = customerId;
        }

        public java.math.BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(java.math.BigDecimal amount) {
            this.amount = amount;
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

        public java.math.BigDecimal getMonthlyIncome() {
            return monthlyIncome;
        }

        public void setMonthlyIncome(java.math.BigDecimal monthlyIncome) {
            this.monthlyIncome = monthlyIncome;
        }

        public String getEmploymentStatus() {
            return employmentStatus;
        }

        public void setEmploymentStatus(String employmentStatus) {
            this.employmentStatus = employmentStatus;
        }
    }

    public static class ApprovalRequest {
        private String reason;

        public ApprovalRequest() {
        }

        public ApprovalRequest(String reason) {
            this.reason = reason;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

    public static class RejectionRequest {
        private String reason;

        public RejectionRequest() {
        }

        public RejectionRequest(String reason) {
            this.reason = reason;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}
