package de.hsnr.bank.beans;

import de.hsnr.bank.usecases.Interfaces.IAnmelden;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;

@Named("loginBean")
@RequestScoped
public class LoginBean {

    @EJB
    private IAnmelden anmelden;

    private String benutzername;
    private String passwort;

    public void login() throws IOException {
        // This is a simplified login. You should handle success/failure properly.
        anmelden.login(benutzername, passwort);
        FacesContext.getCurrentInstance().getExternalContext().redirect("menu.xhtml");
    }

    // Getters and Setters
    public String getBenutzername() { return benutzername; }
    public void setBenutzername(String benutzername) { this.benutzername = benutzername; }
    public String getPasswort() { return passwort; }
    public void setPasswort(String passwort) { this.passwort = passwort; }
}