package de.hsnr.bank.beans;

import de.hsnr.bank.entities.Kunde;
import de.hsnr.bank.usecases.KundenManager;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("kundenSuchenBean")
@ViewScoped
public class KundenSuchenBean implements Serializable {

    @EJB
    private KundenManager kundenManager;

    private String suchbegriff;
    private List<Kunde> suchergebnisse;

    public void suchen() {
        // Note: A dedicated search method in the DAO would be more efficient.
        if (suchbegriff != null && !suchbegriff.trim().isEmpty()) {
            suchergebnisse = kundenManager.searchKunde(suchbegriff);
        } else {
            suchergebnisse = kundenManager.alleLesen();
        }
    }

    // Getters and Setters
    public String getSuchbegriff() {
        return suchbegriff;
    }

    public void setSuchbegriff(String suchbegriff) {
        this.suchbegriff = suchbegriff;
    }

    public List<Kunde> getSuchergebnisse() {
        return suchergebnisse;
    }

    public void setSuchergebnisse(List<Kunde> suchergebnisse) {
        this.suchergebnisse = suchergebnisse;
    }
}