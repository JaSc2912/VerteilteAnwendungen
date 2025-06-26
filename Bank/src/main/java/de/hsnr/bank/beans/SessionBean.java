package de.hsnr.bank.beans;

import de.hsnr.bank.entities.Benutzer;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;

@Named("sessionBean")
@SessionScoped
public class SessionBean implements Serializable {

    private Benutzer loggedInUser;

    public void setLoggedInUser(Benutzer user) {
        this.loggedInUser = user;
    }

    public Benutzer getLoggedInUser() {
        return loggedInUser;
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public String getCurrentUserName() {
        return loggedInUser != null ? loggedInUser.getName() : "Guest";
    }

    public String getCurrentUserRole() {
        return loggedInUser != null ? loggedInUser.getRolle().toString() : "NONE";
    }

    public boolean isAdmin() {
        return loggedInUser != null && "ADMIN".equals(loggedInUser.getRolle().toString());
    }

    public boolean isKundenservice() {
        return loggedInUser != null && "KUNDENSERVICE".equals(loggedInUser.getRolle().toString());
    }

    public boolean isKreditvergabe() {
        return loggedInUser != null && "KREDITVERGABE".equals(loggedInUser.getRolle().toString());
    }

    public void logout() {
        this.loggedInUser = null;

        // Invalidate the HTTP session
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
