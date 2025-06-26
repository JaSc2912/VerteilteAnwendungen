package de.hsnr.bank.beans;

import de.hsnr.bank.entities.Benutzer;
import de.hsnr.bank.usecases.Interfaces.IAnmelden;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("loginBean")
@RequestScoped
public class LoginBean {

    @EJB
    private IAnmelden anmelden;

    @Inject
    private SessionBean sessionBean;

    private String benutzername;
    private String passwort;

    public String doLogin() {
        try {
            Benutzer benutzer = anmelden.login(benutzername, passwort);

            // Store user in session
            sessionBean.setLoggedInUser(benutzer);

            // Redirect to menu on successful login
            return "menu.xhtml?faces-redirect=true";
        } catch (SecurityException e) {
            // Add error message to display to user
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Anmeldung fehlgeschlagen", e.getMessage()));
            return null; // Stay on current page
        }
    }

    public String login() {
        return doLogin();
    }

    public String logout() {
        sessionBean.logout();
        return "login.xhtml?faces-redirect=true";
    }

    // Getters and Setters
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