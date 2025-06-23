package de.hsnr.bank.beans;

import de.hsnr.bank.entities.Kunde;
import de.hsnr.bank.usecases.Interfaces.IKundeAuswerten;
import de.hsnr.bank.usecases.KundenManager;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("auswertungBean")
@ViewScoped
public class AuswertungBean implements Serializable {

    @EJB
    private IKundeAuswerten kundeAuswerten;
    @EJB
    private KundenManager kundenManager;

    private String kundennummer;
    private Kunde kunde;
    private boolean kreditwuerdig;
    private Double gesamtsaldo;

    public void auswerten() {
        if (kundennummer != null) {
            this.kunde = kundenManager.suchen(kundennummer);
            if (this.kunde != null) {
                this.kreditwuerdig = kundeAuswerten.checkKreditwürdigkeit(this.kunde);
                this.gesamtsaldo = kundeAuswerten.checkSaldo(this.kunde);
            }
        }
    }

    // Getters and Setters
    public String getKundennummer() { return kundennummer; }
    public void setKundennummer(String kundennummer) { this.kundennummer = kundennummer; }
    public Kunde getKunde() { return kunde; }
    public void setKunde(Kunde kunde) { this.kunde = kunde; }
    public boolean isKreditwuerdig() { return kreditwuerdig; }
    public void setKreditwuerdig(boolean kreditwuerdig) { this.kreditwuerdig = kreditwuerdig; }
    public Double getGesamtsaldo() { return gesamtsaldo; }
    public void setGesamtsaldo(Double gesamtsaldo) { this.gesamtsaldo = gesamtsaldo; }
}