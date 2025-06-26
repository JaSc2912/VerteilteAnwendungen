package de.hsnr.bankclient;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Transfer Object für Bankkonto
 */
public class BankkontoTO {
    private String iban;
    private String kontoinhaber;
    private String kontenart;
    private BigDecimal kontostand;
    private LocalDate kontoeröffnungsdatum;
    private String kontostatus;

    // Default constructor
    public BankkontoTO() {
    }

    // Constructor with all fields
    public BankkontoTO(String iban, String kontoinhaber, String kontenart,
            BigDecimal kontostand, LocalDate kontoeröffnungsdatum, String kontostatus) {
        this.iban = iban;
        this.kontoinhaber = kontoinhaber;
        this.kontenart = kontenart;
        this.kontostand = kontostand;
        this.kontoeröffnungsdatum = kontoeröffnungsdatum;
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

    public String getKontenart() {
        return kontenart;
    }

    public void setKontenart(String kontenart) {
        this.kontenart = kontenart;
    }

    public BigDecimal getKontostand() {
        return kontostand;
    }

    public void setKontostand(BigDecimal kontostand) {
        this.kontostand = kontostand;
    }

    public LocalDate getKontoeröffnungsdatum() {
        return kontoeröffnungsdatum;
    }

    public void setKontoeröffnungsdatum(LocalDate kontoeröffnungsdatum) {
        this.kontoeröffnungsdatum = kontoeröffnungsdatum;
    }

    public String getKontostatus() {
        return kontostatus;
    }

    public void setKontostatus(String kontostatus) {
        this.kontostatus = kontostatus;
    }
}
