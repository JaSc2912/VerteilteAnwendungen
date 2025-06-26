package de.hsnr.bank.client.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * REST client interface for authentication operations
 */
@RegisterRestClient(baseUri = "http://localhost:8080/Bank-Server/api")
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AuthRestClient {

    /**
     * User login
     */
    @POST
    @Path("/login")
    Response login(LoginRequest request);

    /**
     * Token validation
     */
    @POST
    @Path("/validate")
    Response validateToken(TokenRequest request);

    /**
     * User logout
     */
    @POST
    @Path("/logout")
    Response logout(@HeaderParam("Authorization") String token);

    // Request DTOs
    public static class LoginRequest {
        private String benutzername;
        private String passwort;

        public LoginRequest() {
        }

        public LoginRequest(String benutzername, String passwort) {
            this.benutzername = benutzername;
            this.passwort = passwort;
        }

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

        public TokenRequest() {
        }

        public TokenRequest(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
