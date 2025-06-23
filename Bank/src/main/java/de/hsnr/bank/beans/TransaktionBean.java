package de.hsnr.bank.beans;

import de.hsnr.bank.dataaccess.TransaktionDAO;
import de.hsnr.bank.entities.Transaktion;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("transaktionBean")
@ViewScoped
public class TransaktionBean implements Serializable {

    @EJB
    private TransaktionDAO transaktionDAO;

    private String iban;
    private List<Transaktion> transaktionen;

    public void ladeTransaktionen() {
        if (iban != null && !iban.isEmpty()) {
            this.transaktionen = transaktionDAO.findByKonto(iban);
        }
    }

    // Getters and Setters
    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public List<Transaktion> getTransaktionen() {
        return transaktionen;
    }

    public void setTransaktionen(List<Transaktion> transaktionen) {
        this.transaktionen = transaktionen;
    }
}