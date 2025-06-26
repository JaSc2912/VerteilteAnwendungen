package de.hsnr.bankclient;

import java.io.Serializable;

/**
 * Transfer Object für Transaktion
 */
public class TransaktionTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String transaktionsnummer;
    private String konto; // IBAN
    private Double betrag;
    private String empfaenger;
    private String transaktionsart;
    private String transaktionsstatus;
    private String transaktionsdatum; // Changed from LocalDateTime to String

    // Default constructor
    public TransaktionTO() {
    }

    // Constructor with all fields
    public TransaktionTO(String transaktionsnummer, String konto, Double betrag,
            String empfaenger, String transaktionsart, String transaktionsstatus,
            String transaktionsdatum) {
        this.transaktionsnummer = transaktionsnummer;
        this.konto = konto;
        this.betrag = betrag;
        this.empfaenger = empfaenger;
        this.transaktionsart = transaktionsart;
        this.transaktionsstatus = transaktionsstatus;
        this.transaktionsdatum = transaktionsdatum;
    }

    // Getters and Setters
    public String getTransaktionsnummer() {
        return transaktionsnummer;
    }

    public void setTransaktionsnummer(String transaktionsnummer) {
        this.transaktionsnummer = transaktionsnummer;
    }

    public String getKonto() {
        return konto;
    }

    public void setKonto(String konto) {
        this.konto = konto;
    }

    public Double getBetrag() {
        return betrag;
    }

    public void setBetrag(Double betrag) {
        this.betrag = betrag;
    }

    public String getEmpfaenger() {
        return empfaenger;
    }

    public void setEmpfaenger(String empfaenger) {
        this.empfaenger = empfaenger;
    }

    public String getTransaktionsart() {
        return transaktionsart;
    }

    public void setTransaktionsart(String transaktionsart) {
        this.transaktionsart = transaktionsart;
    }

    public String getTransaktionsstatus() {
        return transaktionsstatus;
    }

    public void setTransaktionsstatus(String transaktionsstatus) {
        this.transaktionsstatus = transaktionsstatus;
    }

    public String getTransaktionsdatum() {
        return transaktionsdatum;
    }

    public void setTransaktionsdatum(String transaktionsdatum) {
        this.transaktionsdatum = transaktionsdatum;
    }
}
