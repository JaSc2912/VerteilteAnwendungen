package de.hsnr.bankserver.core.entities;

import java.util.Date;

/**
 * Business Object für Transaktion
 */
public class Transaktion {
    
    private String transaktionsnummer;
    private String konto;
    private Date transaktionsdatum;
    private Double betrag;
    private String transaktionsart; // Überweisung, Einzahlung, Abhebung
    private String empfänger;
    private String transaktionsstatus; // abgeschlossen, storniert, ausstehend

    public Transaktion() {
    }

    public Transaktion(String transaktionsnummer, String konto, Date transaktionsdatum, 
                       Double betrag, String transaktionsart, String empfänger, String transaktionsstatus) {
        this.transaktionsnummer = transaktionsnummer;
        this.konto = konto;
        this.transaktionsdatum = transaktionsdatum;
        this.betrag = betrag;
        this.transaktionsart = transaktionsart;
        this.empfänger = empfänger;
        this.transaktionsstatus = transaktionsstatus;
    }

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

    public Date getTransaktionsdatum() {
        return transaktionsdatum;
    }

    public void setTransaktionsdatum(Date transaktionsdatum) {
        this.transaktionsdatum = transaktionsdatum;
    }

    public Double getBetrag() {
        return betrag;
    }

    public void setBetrag(Double betrag) {
        this.betrag = betrag;
    }

    public String getTransaktionsart() {
        return transaktionsart;
    }

    public void setTransaktionsart(String transaktionsart) {
        this.transaktionsart = transaktionsart;
    }

    public String getEmpfänger() {
        return empfänger;
    }

    public void setEmpfänger(String empfänger) {
        this.empfänger = empfänger;
    }

    public String getTransaktionsstatus() {
        return transaktionsstatus;
    }

    public void setTransaktionsstatus(String transaktionsstatus) {
        this.transaktionsstatus = transaktionsstatus;
    }
}
