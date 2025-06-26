package de.hsnr.bank.rest.resources;

import de.hsnr.bank.entities.Kunde;
import de.hsnr.bank.services.interfaces.ICustomerService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * REST resource for customer operations
 */
@Path("/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

    private static final Logger LOGGER = Logger.getLogger(CustomerResource.class.getName());

    @EJB
    private ICustomerService customerService;

    /**
     * Get all customers
     */
    @GET
    public Response getAllCustomers() {
        try {
            List<Kunde> customers = customerService.getAllCustomers();
            return Response.ok(customers).build();
        } catch (Exception e) {
            LOGGER.warning("Error getting all customers: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to retrieve customers");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    /**
     * Get customer by ID
     */
    @GET
    @Path("/{id}")
    public Response getCustomer(@PathParam("id") Long kundennummer) {
        try {
            Kunde kunde = customerService.findByKundennummer(kundennummer);
            if (kunde == null) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Customer not found");
                error.put("kundennummer", kundennummer);
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }
            return Response.ok(kunde).build();
        } catch (Exception e) {
            LOGGER.warning("Error getting customer: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to retrieve customer");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    /**
     * Search customers
     */
    @GET
    @Path("/search")
    public Response searchCustomers(@QueryParam("q") String searchTerm) {
        try {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Search term is required");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            List<Kunde> customers = customerService.searchCustomers(searchTerm.trim());
            return Response.ok(customers).build();
        } catch (Exception e) {
            LOGGER.warning("Error searching customers: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to search customers");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    /**
     * Create new customer
     */
    @POST
    public Response createCustomer(Kunde kunde) {
        try {
            // Validate required fields
            if (kunde.getName() == null || kunde.getName().trim().isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Customer name is required");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            Kunde createdKunde = customerService.createCustomer(kunde);
            return Response.status(Response.Status.CREATED).entity(createdKunde).build();
        } catch (Exception e) {
            LOGGER.warning("Error creating customer: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to create customer");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    /**
     * Update customer
     */
    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") Long kundennummer, Kunde kunde) {
        try {
            // Ensure the ID in the path matches the entity
            kunde.setKundennummer(kundennummer);

            // Validate required fields
            if (kunde.getName() == null || kunde.getName().trim().isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Customer name is required");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            Kunde updatedKunde = customerService.updateCustomer(kunde);
            return Response.ok(updatedKunde).build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Customer not found");
                error.put("kundennummer", kundennummer);
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }

            LOGGER.warning("Error updating customer: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to update customer");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    /**
     * Delete customer (mark as inactive)
     */
    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") Long kundennummer) {
        try {
            customerService.deleteCustomer(kundennummer);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Customer marked as deleted");
            response.put("kundennummer", kundennummer);
            return Response.ok(response).build();
        } catch (Exception e) {
            LOGGER.warning("Error deleting customer: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to delete customer");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    /**
     * Check customer creditworthiness
     */
    @GET
    @Path("/{id}/creditworthiness")
    public Response checkCreditworthiness(@PathParam("id") Long kundennummer) {
        try {
            boolean creditworthy = customerService.isCustomerCreditworthy(kundennummer);
            BigDecimal totalBalance = customerService.getTotalCustomerBalance(kundennummer);

            Map<String, Object> response = new HashMap<>();
            response.put("kundennummer", kundennummer);
            response.put("creditworthy", creditworthy);
            response.put("totalBalance", totalBalance);

            return Response.ok(response).build();
        } catch (Exception e) {
            LOGGER.warning("Error checking creditworthiness: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to check creditworthiness");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    /**
     * Get customer total balance
     */
    @GET
    @Path("/{id}/balance")
    public Response getTotalBalance(@PathParam("id") Long kundennummer) {
        try {
            BigDecimal totalBalance = customerService.getTotalCustomerBalance(kundennummer);

            Map<String, Object> response = new HashMap<>();
            response.put("kundennummer", kundennummer);
            response.put("totalBalance", totalBalance);

            return Response.ok(response).build();
        } catch (Exception e) {
            LOGGER.warning("Error getting total balance: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to get total balance");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }
}
