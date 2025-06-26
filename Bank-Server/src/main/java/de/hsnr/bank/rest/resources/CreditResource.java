package de.hsnr.bank.rest.resources;

import de.hsnr.bank.entities.Kreditantrag;
import de.hsnr.bank.services.interfaces.ICreditService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * REST resource for credit operations
 */
@Path("/credits")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CreditResource {

    private static final Logger LOGGER = Logger.getLogger(CreditResource.class.getName());

    @EJB
    private ICreditService creditService;

    /**
     * Get all credit applications
     */
    @GET
    public Response getAllCreditApplications() {
        try {
            List<Kreditantrag> creditApplications = creditService.getAllCreditApplications();
            return Response.ok(creditApplications).build();
        } catch (Exception e) {
            LOGGER.warning("Error getting all credit applications: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to retrieve credit applications");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    /**
     * Get credit application by ID
     */
    @GET
    @Path("/{id}")
    public Response getCreditApplication(@PathParam("id") Long kreditantragsNummer) {
        try {
            Kreditantrag creditApplication = creditService.findByKreditantragsNummer(kreditantragsNummer);
            if (creditApplication == null) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Credit application not found");
                error.put("kreditantragsNummer", kreditantragsNummer);
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }
            return Response.ok(creditApplication).build();
        } catch (Exception e) {
            LOGGER.warning("Error getting credit application: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to retrieve credit application");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    /**
     * Get pending credit applications
     */
    @GET
    @Path("/pending")
    public Response getPendingCreditApplications() {
        try {
            List<Kreditantrag> pendingApplications = creditService.getPendingCreditApplications();
            return Response.ok(pendingApplications).build();
        } catch (Exception e) {
            LOGGER.warning("Error getting pending credit applications: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to retrieve pending credit applications");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    /**
     * Get credit applications for customer
     */
    @GET
    @Path("/customer/{customerId}")
    public Response getCreditApplicationsByCustomer(@PathParam("customerId") Long kundennummer) {
        try {
            List<Kreditantrag> customerApplications = creditService.getCreditApplicationsByCustomer(kundennummer);
            return Response.ok(customerApplications).build();
        } catch (Exception e) {
            LOGGER.warning("Error getting customer credit applications: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to retrieve customer credit applications");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    /**
     * Create new credit application
     */
    @POST
    public Response createCreditApplication(Kreditantrag kreditantrag) {
        try {
            // Validate required fields
            if (kreditantrag.getKreditsumme() == null
                    || kreditantrag.getKreditsumme().compareTo(java.math.BigDecimal.ZERO) <= 0) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Valid credit amount is required");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            if (kreditantrag.getAntragssteller() == null
                    || kreditantrag.getAntragssteller().getKundennummer() == null) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Customer information is required");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            if (kreditantrag.getLaufzeit() == null || kreditantrag.getLaufzeit() <= 0) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Valid loan term is required");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            Kreditantrag createdApplication = creditService.createCreditApplication(kreditantrag);
            return Response.status(Response.Status.CREATED).entity(createdApplication).build();
        } catch (Exception e) {
            LOGGER.warning("Error creating credit application: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to create credit application");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    /**
     * Approve credit application
     */
    @PUT
    @Path("/{id}/approve")
    public Response approveCreditApplication(@PathParam("id") Long kreditantragsNummer, ApprovalRequest request) {
        try {
            if (request.getBearbeiter() == null || request.getBearbeiter().trim().isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Processor information is required");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            Kreditantrag approvedApplication = creditService.approveCreditApplication(kreditantragsNummer,
                    request.getBearbeiter());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Credit application approved successfully");
            response.put("creditApplication", approvedApplication);

            return Response.ok(response).build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Credit application not found");
                error.put("kreditantragsNummer", kreditantragsNummer);
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }

            if (e.getMessage().contains("not pending")) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Credit application is not pending");
                error.put("message", e.getMessage());
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            LOGGER.warning("Error approving credit application: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to approve credit application");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    /**
     * Reject credit application
     */
    @PUT
    @Path("/{id}/reject")
    public Response rejectCreditApplication(@PathParam("id") Long kreditantragsNummer, RejectionRequest request) {
        try {
            if (request.getBearbeiter() == null || request.getBearbeiter().trim().isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Processor information is required");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            Kreditantrag rejectedApplication = creditService.rejectCreditApplication(
                    kreditantragsNummer,
                    request.getBearbeiter(),
                    request.getBegruendung());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Credit application rejected successfully");
            response.put("creditApplication", rejectedApplication);

            return Response.ok(response).build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Credit application not found");
                error.put("kreditantragsNummer", kreditantragsNummer);
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }

            if (e.getMessage().contains("not pending")) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Credit application is not pending");
                error.put("message", e.getMessage());
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            LOGGER.warning("Error rejecting credit application: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to reject credit application");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    // Request DTOs
    public static class ApprovalRequest {
        private String bearbeiter;

        public String getBearbeiter() {
            return bearbeiter;
        }

        public void setBearbeiter(String bearbeiter) {
            this.bearbeiter = bearbeiter;
        }
    }

    public static class RejectionRequest {
        private String bearbeiter;
        private String begruendung;

        public String getBearbeiter() {
            return bearbeiter;
        }

        public void setBearbeiter(String bearbeiter) {
            this.bearbeiter = bearbeiter;
        }

        public String getBegruendung() {
            return begruendung;
        }

        public void setBegruendung(String begruendung) {
            this.begruendung = begruendung;
        }
    }
}
