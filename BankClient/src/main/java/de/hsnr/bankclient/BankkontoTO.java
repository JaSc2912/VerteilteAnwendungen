package de.hsnr.bankclient;

import java.io.Serializable;

/**
 * Transfer Object für Bankkonto
 */
public class BankkontoTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String iban;
    private String kontoinhaber;
    private String kontoart; // Fixed field name
    private Double kontostand; // Changed from BigDecimal to Double
    private String kontoeroeffnungsdatum; // Changed from LocalDate to String and fixed field name
    private String kontostatus;

    // Default constructor
    public BankkontoTO() {
    }

    // Constructor with all fields
    public BankkontoTO(String iban, String kontoinhaber, String kontoart,
            Double kontostand, String kontoeroeffnungsdatum, String kontostatus) {
        this.iban = iban;
        this.kontoinhaber = kontoinhaber;
        this.kontoart = kontoart;
        this.kontostand = kontostand;
        this.kontoeroeffnungsdatum = kontoeroeffnungsdatum;
        this.kontostatus = kontostatus;
    }

    // Getters and Setters
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

    // Additional getter for XHTML compatibility
    public String getKontenart() {
        return kontoart;
    }

    public Double getKontostand() {
        return kontostand;
    }

    public void setKontostand(Double kontostand) {
        this.kontostand = kontostand;
    }

    public String getKontoeroeffnungsdatum() {
        return kontoeroeffnungsdatum;
    }

    public void setKontoeroeffnungsdatum(String kontoeroeffnungsdatum) {
        this.kontoeroeffnungsdatum = kontoeroeffnungsdatum;
    }

    // Additional getter for XHTML compatibility (with ö)
    public String getKontoeröffnungsdatum() {
        return kontoeroeffnungsdatum;
    }

    public String getKontostatus() {
        return kontostatus;
    }

    public void setKontostatus(String kontostatus) {
        this.kontostatus = kontostatus;
    }
}
