package de.hsnr.bankserver.facade;

import de.hsnr.bankserver.core.entities.Transaktion;
import de.hsnr.bankserver.core.usecases.KontotransaktionenVerwalten;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * REST Facade für Transaktions-Services
 */
@Path("/transaktionen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransaktionenService {
    
    @Inject
    private KontotransaktionenVerwalten kontotransaktionenVerwalten;
    
    @POST
    public Response addTransaktion(Transaktion transaktion) {
        try {
            kontotransaktionenVerwalten.addTransaktion(transaktion);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{transaktionsnummer}/stornieren")
    public Response storniereTransaktion(@PathParam("transaktionsnummer") String transaktionsnummer) {
        try {
            kontotransaktionenVerwalten.storniereTransaktion(transaktionsnummer);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @GET
    public Response getAllTransaktionen() {
        try {
            List<Transaktion> transaktionen = kontotransaktionenVerwalten.getAllTransaktionen();
            return Response.ok(transaktionen).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/konto/{konto}")
    public Response getTransaktionenByKonto(@PathParam("konto") String konto) {
        try {
            List<Transaktion> transaktionen = kontotransaktionenVerwalten.getTransaktionenByKonto(konto);
            return Response.ok(transaktionen).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
