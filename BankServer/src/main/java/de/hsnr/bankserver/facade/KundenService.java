package de.hsnr.bankserver.facade;

import de.hsnr.bankserver.core.entities.Kunde;
import de.hsnr.bankserver.core.usecases.KundendatenPflegen;
import de.hsnr.bankserver.core.usecases.KundenSuchen;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * REST Facade für Kunden-Services
 */
@Path("/kunden")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class KundenService {
    
    @Inject
    private KundendatenPflegen kundendatenPflegen;
    
    @Inject
    private KundenSuchen kundenSuchen;
    
    @POST
    public Response addKunde(Kunde kunde) {
        try {
            kundendatenPflegen.addKunde(kunde);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @PUT
    public Response editKunde(Kunde kunde) {
        try {
            kundendatenPflegen.editKunde(kunde);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("/{kundennummer}")
    public Response deleteKunde(@PathParam("kundennummer") String kundennummer) {
        try {
            kundendatenPflegen.deleteKunde(kundennummer);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @GET
    public Response getAllKunden() {
        try {
            List<Kunde> kunden = kundendatenPflegen.getAllKunden();
            return Response.ok(kunden).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/{kundennummer}")
    public Response getKunde(@PathParam("kundennummer") String kundennummer) {
        try {
            Kunde kunde = kundendatenPflegen.getKunde(kundennummer);
            if (kunde != null) {
                return Response.ok(kunde).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/search/{name}")
    public Response searchKunden(@PathParam("name") String name) {
        try {
            List<Kunde> kunden = kundenSuchen.searchKunde(name);
            return Response.ok(kunden).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
