package de.hsnr.bank.rest.resources;

import de.hsnr.bank.entities.Benutzer;
import de.hsnr.bank.services.interfaces.IAuthenticationService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * REST resource for authentication operations
 */
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationResource.class.getName());

    @EJB
    private IAuthenticationService authService;

    /**
     * Login endpoint
     */
    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        try {
            Benutzer benutzer = authService.login(request.getBenutzername(), request.getPasswort());

            // Create response with user info (excluding password)
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Login erfolgreich");
            response.put("user", createUserResponse(benutzer));
            response.put("token", benutzer.getBenutzername()); // Simple token approach

            return Response.ok(response).build();

        } catch (SecurityException e) {
            LOGGER.warning("Login failed: " + e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());

            return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
        }
    }

    /**
     * Validate token endpoint
     */
    @POST
    @Path("/validate")
    public Response validateToken(TokenRequest request) {
        try {
            Benutzer benutzer = authService.validateToken(request.getToken());

            Map<String, Object> response = new HashMap<>();
            response.put("valid", true);
            response.put("user", createUserResponse(benutzer));

            return Response.ok(response).build();

        } catch (SecurityException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("valid", false);
            response.put("message", e.getMessage());

            return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
        }
    }

    /**
     * Logout endpoint
     */
    @POST
    @Path("/logout")
    public Response logout() {
        // For stateless REST, logout is mainly handled on client side
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Logout erfolgreich");

        return Response.ok(response).build();
    }

    private Map<String, Object> createUserResponse(Benutzer benutzer) {
        Map<String, Object> userResponse = new HashMap<>();
        userResponse.put("benutzername", benutzer.getBenutzername());
        userResponse.put("name", benutzer.getName());
        userResponse.put("rolle", benutzer.getRolle().toString());
        userResponse.put("telefonnummer", benutzer.getTelefonnummer());
        // Never include password in response
        return userResponse;
    }

    // Request DTOs
    public static class LoginRequest {
        private String benutzername;
        private String passwort;

        public String getBenutzername() {
            return benutzername;
        }

        public void setBenutzername(String benutzername) {
            this.benutzername = benutzername;
        }

        public String getPasswort() {
            return passwort;
        }

        public void setPasswort(String passwort) {
            this.passwort = passwort;
        }
    }

    public static class TokenRequest {
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
