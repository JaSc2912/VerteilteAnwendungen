package de.hsnr.bank.beans;

import de.hsnr.bank.entities.Bankkonto;
import de.hsnr.bank.usecases.BankkontoManager;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named("bankkontoBean")
@ViewScoped
public class BankkontoBean implements Serializable {

    @EJB
    private BankkontoManager bankkontoManager;

    private String kundennummer;
    private List<Bankkonto> konten;

    public void ladeKonten() {
        if (kundennummer != null) {
            // Use the efficient DAO method
            konten = bankkontoManager.alleLesen().stream()
                    .filter(k -> k.getBesitzer() != null && kundennummer.equals(k.getBesitzer().getKundennummer()))
                    .collect(Collectors.toList());
        }
    }

    // Getters and Setters
    public String getKundennummer() {
        return kundennummer;
    }

    public void setKundennummer(String kundennummer) {
        this.kundennummer = kundennummer;
    }

    public List<Bankkonto> getKonten() {
        return konten;
    }

    public void setKonten(List<Bankkonto> konten) {
        this.konten = konten;
    }
}