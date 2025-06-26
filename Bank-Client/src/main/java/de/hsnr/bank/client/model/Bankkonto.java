package de.hsnr.bank.client.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Bank account model for client-side operations
 */
public class Bankkonto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String iban;
    private BigDecimal kontostand;
    private String kontostatus;
    private BigDecimal kreditlimit;
    private Kunde kunde;

    // Default constructor
    public Bankkonto() {
    }

    // Constructor with parameters
    public Bankkonto(String iban, Kunde kunde) {
        this.iban = iban;
        this.kunde = kunde;
        this.kontostand = BigDecimal.ZERO;
        this.kreditlimit = BigDecimal.ZERO;
        this.kontostatus = "AKTIV";
    }

    // Getters and setters
    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    /**
     * Alias for getIban() - used in JSF beans
     */
    public String getKontonummer() {
        return iban;
    }

    /**
     * Alias for setIban() - used in JSF beans
     */
    public void setKontonummer(String kontonummer) {
        this.iban = kontonummer;
    }

    public BigDecimal getKontostand() {
        return kontostand;
    }

    public void setKontostand(BigDecimal kontostand) {
        this.kontostand = kontostand;
    }

    public String getKontostatus() {
        return kontostatus;
    }

    public void setKontostatus(String kontostatus) {
        this.kontostatus = kontostatus;
    }

    public BigDecimal getKreditlimit() {
        return kreditlimit;
    }

    public void setKreditlimit(BigDecimal kreditlimit) {
        this.kreditlimit = kreditlimit;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    @Override
    public String toString() {
        return "Bankkonto{" +
                "iban='" + iban + '\'' +
                ", kontostand=" + kontostand +
                ", kontostatus='" + kontostatus + '\'' +
                '}';
    }
}
