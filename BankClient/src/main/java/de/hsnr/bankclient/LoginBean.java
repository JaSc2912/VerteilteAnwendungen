package de.hsnr.bankclient;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;

/**
 * Managed Bean für Login
 */
@Named
@SessionScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String benutzername;
    private String passwort;
    private BenutzerTO currentUser;
    private String serverUrl = "http://localhost:8080/BankServer/api";

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

    public BenutzerTO getCurrentUser() {
        return currentUser;
    }

    public String login() {
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/benutzer/login");

            LoginRequest request = new LoginRequest();
            request.setBenutzername(benutzername);
            request.setPasswort(passwort);

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(request));

            if (response.getStatus() == 200) {
                currentUser = response.readEntity(BenutzerTO.class);

                FacesContext facesContext = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
                session.setAttribute("currentUser", currentUser);

                // Weiterleitung basierend auf Rolle
                switch (currentUser.getRolle()) {
                    case ADMIN:
                        return "/dashboard.xhtml?faces-redirect=true";
                    case KUNDENSERVICE:
                        return "/dashboard.xhtml?faces-redirect=true";
                    case KREDITBEARBEITER:
                        return "/dashboard.xhtml?faces-redirect=true";
                    default:
                        return "/dashboard.xhtml?faces-redirect=true";
                }
            } else {
                return "/loginError.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "/loginError.xhtml?faces-redirect=true";
        }
    }

    public String logout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        currentUser = null;
        return "/login.xhtml?faces-redirect=true";
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    // DTO für Login Request
    public static class LoginRequest implements Serializable {
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
}
