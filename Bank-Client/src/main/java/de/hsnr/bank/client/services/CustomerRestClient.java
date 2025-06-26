package de.hsnr.bank.client.services;

import de.hsnr.bank.client.model.Kunde;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.math.BigDecimal;
import java.util.List;

/**
 * REST client interface for customer operations
 */
@RegisterRestClient(baseUri = "http://localhost:8080/Bank-Server/api")
@Path("/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface CustomerRestClient {

    /**
     * Get all customers
     */
    @GET
    List<Kunde> getAllCustomers(@HeaderParam("Authorization") String token);

    /**
     * Get customer by ID
     */
    @GET
    @Path("/{id}")
    Kunde getCustomer(@PathParam("id") Long kundennummer, @HeaderParam("Authorization") String token);

    /**
     * Search customers
     */
    @GET
    @Path("/search")
    List<Kunde> searchCustomers(@QueryParam("q") String searchTerm, @HeaderParam("Authorization") String token);

    /**
     * Create new customer
     */
    @POST
    Response createCustomer(Kunde kunde, @HeaderParam("Authorization") String token);

    /**
     * Update customer
     */
    @PUT
    @Path("/{id}")
    Response updateCustomer(@PathParam("id") Long kundennummer, Kunde kunde,
            @HeaderParam("Authorization") String token);

    /**
     * Delete customer
     */
    @DELETE
    @Path("/{id}")
    Response deleteCustomer(@PathParam("id") Long kundennummer, @HeaderParam("Authorization") String token);

    /**
     * Check customer creditworthiness
     */
    @GET
    @Path("/{id}/creditworthiness")
    Response checkCreditworthiness(@PathParam("id") Long kundennummer, @HeaderParam("Authorization") String token);
}
