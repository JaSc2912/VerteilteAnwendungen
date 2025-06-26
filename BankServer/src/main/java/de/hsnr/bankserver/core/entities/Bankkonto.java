package de.hsnr.bankserver.core.entities;

import java.util.Date;

/**
 * Business Object für Bankkonto
 */
public class Bankkonto {
    
    private String iban;
    private String kontoinhaber;
    private String kontoart; // Girokonto, Sparkonto, Geschäftskonto
    private Double kontostand;
    private Date kontoeröffnungsdatum;
    private String kontostatus; // aktiv, gesperrt, geschlossen

    public Bankkonto() {
    }

    public Bankkonto(String iban, String kontoinhaber, String kontoart, Double kontostand, 
                     Date kontoeröffnungsdatum, String kontostatus) {
        this.iban = iban;
        this.kontoinhaber = kontoinhaber;
        this.kontoart = kontoart;
        this.kontostand = kontostand;
        this.kontoeröffnungsdatum = kontoeröffnungsdatum;
        this.kontostatus = kontostatus;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getKontoinhaber() {
        return kontoinhaber;
    }

    public void setKontoinhaber(String kontoinhaber) {
        this.kontoinhaber = kontoinhaber;
    }

    public String getKontoart() {
        return kontoart;
    }

    public void setKontoart(String kontoart) {
        this.kontoart = kontoart;
    }

    public Double getKontostand() {
        return kontostand;
    }

    public void setKontostand(Double kontostand) {
        this.kontostand = kontostand;
    }

    public Date getKontoeröffnungsdatum() {
        return kontoeröffnungsdatum;
    }

    public void setKontoeröffnungsdatum(Date kontoeröffnungsdatum) {
        this.kontoeröffnungsdatum = kontoeröffnungsdatum;
    }

    public String getKontostatus() {
        return kontostatus;
    }

    public void setKontostatus(String kontostatus) {
        this.kontostatus = kontostatus;
    }
}
