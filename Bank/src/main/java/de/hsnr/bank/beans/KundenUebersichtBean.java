package de.hsnr.bank.beans;

import de.hsnr.bank.entities.Kunde;
import de.hsnr.bank.usecases.KundenManager;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import jakarta.faces.context.FacesContext;

@Named("kundenUebersichtBean")
@ViewScoped
public class KundenUebersichtBean implements Serializable {

    @EJB
    private KundenManager kundenManager;

    private String kundennummer;
    private Kunde kunde;

    @PostConstruct
    public void init() {
        // Get customer number from URL parameter
        String kdnrParam = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("kdnr");

        if (kdnrParam != null && !kdnrParam.trim().isEmpty()) {
            this.kundennummer = kdnrParam;
            laden();
        }
    }

    public void laden() {
        if (kundennummer != null && !kundennummer.isEmpty()) {
            this.kunde = kundenManager.suchen(kundennummer);
        }
    }

    // Getters and Setters
    public String getKundennummer() {
        return kundennummer;
    }

    public void setKundennummer(String kundennummer) {
        this.kundennummer = kundennummer;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }
}