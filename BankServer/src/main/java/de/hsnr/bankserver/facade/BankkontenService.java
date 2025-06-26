package de.hsnr.bankserver.facade;

import de.hsnr.bankserver.core.entities.Bankkonto;
import de.hsnr.bankserver.core.usecases.BankkontenVerwalten;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * REST Facade für Bankkonto-Services
 */
@Path("/bankkonten")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BankkontenService {
    
    @Inject
    private BankkontenVerwalten bankkontenVerwalten;
    
    @POST
    public Response addBankkonto(Bankkonto bankkonto) {
        try {
            bankkontenVerwalten.addBankkonto(bankkonto);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @PUT
    public Response editBankkonto(Bankkonto bankkonto) {
        try {
            bankkontenVerwalten.editBankkonto(bankkonto);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("/{iban}")
    public Response deleteBankkonto(@PathParam("iban") String iban) {
        try {
            bankkontenVerwalten.deleteBankkonto(iban);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @GET
    public Response getAllBankkonten() {
        try {
            List<Bankkonto> bankkonten = bankkontenVerwalten.getAllBankkonten();
            return Response.ok(bankkonten).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/positive")
    public Response getActiveAccountsWithPositiveBalance() {
        try {
            List<Bankkonto> bankkonten = bankkontenVerwalten.getActiveAccountsWithPositiveBalance();
            return Response.ok(bankkonten).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/{iban}")
    public Response getBankkonto(@PathParam("iban") String iban) {
        try {
            Bankkonto bankkonto = bankkontenVerwalten.getBankkonto(iban);
            if (bankkonto != null) {
                return Response.ok(bankkonto).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
