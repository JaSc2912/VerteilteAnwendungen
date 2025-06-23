package de.hsnr.bank.beans;

import de.hsnr.bank.entities.Kunde;
import de.hsnr.bank.usecases.Interfaces.IKundePflegen;
import de.hsnr.bank.usecases.KundenManager;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("kundenPflegeBean")
@ViewScoped
public class KundenPflegeBean implements Serializable {

    @EJB
    private IKundePflegen kundePflegen;

    @EJB
    private KundenManager kundenManager;

    private String kundennummer;
    private Kunde kunde;

    public void ladeKunde() {
        if (kundennummer != null && !kundennummer.isEmpty()) {
            this.kunde = kundenManager.suchen(kundennummer);
        }
    }

    public void speichern() {
        if (kunde != null) {
            kundePflegen.updateKunde(kunde);
        }
    }

    // Getters and Setters
    public String getKundennummer() { return kundennummer; }
    public void setKundennummer(String kundennummer) { this.kundennummer = kundennummer; }
    public Kunde getKunde() { return kunde; }
    public void setKunde(Kunde kunde) { this.kunde = kunde; }
}