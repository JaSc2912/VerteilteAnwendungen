package de.hsnr.bankserver.facade;

import de.hsnr.bankserver.core.entities.Benutzer;
import de.hsnr.bankserver.core.usecases.BenutzerVerwalten;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * REST Facade für Benutzer-Services
 */
@Path("/benutzer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BenutzerService {
    
    @Inject
    private BenutzerVerwalten benutzerVerwalten;
    
    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        try {
            Benutzer benutzer = benutzerVerwalten.login(request.getBenutzername(), request.getPasswort());
            if (benutzer != null) {
                return Response.ok(benutzer).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @POST
    public Response addBenutzer(Benutzer benutzer) {
        try {
            benutzerVerwalten.addBenutzer(benutzer);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @PUT
    public Response editBenutzer(Benutzer benutzer) {
        try {
            benutzerVerwalten.editBenutzer(benutzer);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("/{benutzername}")
    public Response deleteBenutzer(@PathParam("benutzername") String benutzername) {
        try {
            benutzerVerwalten.deleteBenutzer(benutzername);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @GET
    public Response getAllBenutzer() {
        try {
            List<Benutzer> benutzer = benutzerVerwalten.getAllBenutzer();
            return Response.ok(benutzer).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    // DTO für Login Request
    public static class LoginRequest {
        private String benutzername;
        private String passwort;
        
        public LoginRequest() {}
        
        public String getBenutzername() { return benutzername; }
        public void setBenutzername(String benutzername) { this.benutzername = benutzername; }
        
        public String getPasswort() { return passwort; }
        public void setPasswort(String passwort) { this.passwort = passwort; }
    }
}
