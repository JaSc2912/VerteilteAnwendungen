package de.hsnr.bank.client.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction model for client-side operations
 */
public class Transaktion implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long transaktionsId;
    private BigDecimal betrag;
    private String transaktionsart;
    private String empfaenger;
    private String verwendungszweck;
    private LocalDateTime transaktionsdatum;
    private String transaktionsstatus;
    private String iban; // Reference to account IBAN

    // Default constructor
    public Transaktion() {
    }

    // Constructor with parameters
    public Transaktion(BigDecimal betrag, String transaktionsart, String verwendungszweck) {
        this.betrag = betrag;
        this.transaktionsart = transaktionsart;
        this.verwendungszweck = verwendungszweck;
        this.transaktionsdatum = LocalDateTime.now();
        this.transaktionsstatus = "OFFEN";
    }

    // Getters and setters
    public Long getTransaktionsId() {
        return transaktionsId;
    }

    public void setTransaktionsId(Long transaktionsId) {
        this.transaktionsId = transaktionsId;
    }

    public BigDecimal getBetrag() {
        return betrag;
    }

    public void setBetrag(BigDecimal betrag) {
        this.betrag = betrag;
    }

    public String getTransaktionsart() {
        return transaktionsart;
    }

    public void setTransaktionsart(String transaktionsart) {
        this.transaktionsart = transaktionsart;
    }

    public String getEmpfaenger() {
        return empfaenger;
    }

    public void setEmpfaenger(String empfaenger) {
        this.empfaenger = empfaenger;
    }

    public String getVerwendungszweck() {
        return verwendungszweck;
    }

    public void setVerwendungszweck(String verwendungszweck) {
        this.verwendungszweck = verwendungszweck;
    }

    public LocalDateTime getTransaktionsdatum() {
        return transaktionsdatum;
    }

    public void setTransaktionsdatum(LocalDateTime transaktionsdatum) {
        this.transaktionsdatum = transaktionsdatum;
    }

    public String getTransaktionsstatus() {
        return transaktionsstatus;
    }

    public void setTransaktionsstatus(String transaktionsstatus) {
        this.transaktionsstatus = transaktionsstatus;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    @Override
    public String toString() {
        return "Transaktion{" +
                "transaktionsId=" + transaktionsId +
                ", betrag=" + betrag +
                ", transaktionsart='" + transaktionsart + '\'' +
                ", transaktionsdatum=" + transaktionsdatum +
                '}';
    }
}
