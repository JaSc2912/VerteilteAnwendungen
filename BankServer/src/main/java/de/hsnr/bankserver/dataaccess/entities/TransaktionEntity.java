package de.hsnr.bankserver.dataaccess.entities;

import jakarta.persistence.*;
import java.util.Date;

/**
 * JPA Entity für Transaktion
 */
@Entity
@Table(name = "TRANSAKTION")
public class TransaktionEntity {
    
    @Id
    @Column(name = "TRANSAKTIONSNUMMER")
    private String transaktionsnummer;
    
    @Column(name = "KONTO")
    private String konto;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSAKTIONSDATUM")
    private Date transaktionsdatum;
    
    @Column(name = "BETRAG")
    private Double betrag;
    
    @Column(name = "TRANSAKTIONSART")
    private String transaktionsart;
    
    @Column(name = "EMPFÄNGER")
    private String empfänger;
    
    @Column(name = "TRANSAKTIONSSTATUS")
    private String transaktionsstatus;

    public TransaktionEntity() {
    }

    public TransaktionEntity(String transaktionsnummer, String konto, Date transaktionsdatum, 
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
