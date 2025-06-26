package de.hsnr.bankserver.facade;

import de.hsnr.bankserver.core.entities.Kreditantrag;
import de.hsnr.bankserver.core.usecases.KreditanträgeVerwalten;
import de.hsnr.bankserver.core.usecases.KundenAuswerten;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Facade für Kreditantrags-Services
 */
@Path("/kreditantraege")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class KreditanträgeService {
    
    @Inject
    private KreditanträgeVerwalten kreditanträgeVerwalten;
    
    @Inject
    private KundenAuswerten kundenAuswerten;
    
    @POST
    public Response addKreditantrag(Kreditantrag kreditantrag) {
        try {
            kreditanträgeVerwalten.addKreditantrag(kreditantrag);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @PUT
    public Response editKreditantrag(Kreditantrag kreditantrag) {
        try {
            kreditanträgeVerwalten.editKreditantrag(kreditantrag);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{kreditantragsnummer}/genehmigen/{mitarbeiter}")
    public Response acceptKreditantrag(@PathParam("kreditantragsnummer") String kreditantragsnummer,
                                       @PathParam("mitarbeiter") String mitarbeiter) {
        try {
            kreditanträgeVerwalten.acceptKreditantrag(kreditantragsnummer, mitarbeiter);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{kreditantragsnummer}/ablehnen")
    public Response denyKreditantrag(@PathParam("kreditantragsnummer") String kreditantragsnummer) {
        try {
            kreditanträgeVerwalten.denyKreditantrag(kreditantragsnummer);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @GET
    public Response getAllKreditanträge() {
        try {
            List<Kreditantrag> kreditanträge = kreditanträgeVerwalten.getAllKreditanträge();
            return Response.ok(kreditanträge).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/status/{status}")
    public Response getKreditanträgeByStatus(@PathParam("status") String status) {
        try {
            List<Kreditantrag> kreditanträge = kreditanträgeVerwalten.getKreditanträgeByStatus(status);
            return Response.ok(kreditanträge).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/auswertung/{konto}/{year}/{month}")
    public Response getKundenauswertung(@PathParam("konto") String konto,
                                        @PathParam("year") int year,
                                        @PathParam("month") int month) {
        try {
            Double income = kundenAuswerten.calculateMonthlyIncome(konto, year, month);
            Double outcome = kundenAuswerten.calculateMonthlyOutcome(konto, year, month);
            int negativeMonths = kundenAuswerten.countNegativeBalanceMonths(konto);
            
            Map<String, Object> result = new HashMap<>();
            result.put("eingehendezahlungen", income);
            result.put("ausgehendeZahlungen", outcome);
            result.put("negativeMonate", negativeMonths);
            
            return Response.ok(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
