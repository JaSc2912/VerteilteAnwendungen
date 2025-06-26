package de.hsnr.bank.client.beans;

import de.hsnr.bank.client.services.AuthRestClient;
import de.hsnr.bank.client.services.SessionService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * JSF managed bean for authentication operations
 */
@Named
@RequestScoped
public class AuthBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(AuthBean.class.getName());

    @Inject
    @RestClient
    private AuthRestClient authRestClient;

    @Inject
    private SessionService sessionService;

    private String username;
    private String password;

    /**
     * Handle user login
     */
    public String login() {
        try {
            AuthRestClient.LoginRequest request = new AuthRestClient.LoginRequest(username, password);
            Response response = authRestClient.login(request);

            if (response.getStatus() == 200) {
                // For simplicity, use username as token (in real app, extract from response)
                sessionService.login(username, username);

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Login erfolgreich", "Willkommen " + username));

                return "dashboard?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login fehlgeschlagen",
                                "Ungültige Anmeldedaten"));
                return null;
            }
        } catch (Exception e) {
            LOGGER.warning("Login error: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login fehlgeschlagen",
                            "Verbindungsfehler zum Server"));
            return null;
        }
    }

    /**
     * Handle user logout
     */
    public String logout() {
        try {
            if (sessionService.isLoggedIn()) {
                authRestClient.logout(sessionService.getAuthorizationHeader());
            }
        } catch (Exception e) {
            LOGGER.warning("Logout error: " + e.getMessage());
        } finally {
            sessionService.logout();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Abgemeldet", "Sie wurden erfolgreich abgemeldet"));
        }
        return "index?faces-redirect=true";
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
