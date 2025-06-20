/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.dataaccess;

import de.hsnr.bank.entities.Transaktion;
import jakarta.persistence.*;
import java.util.Date;

/**
 *
 * @author muell
 */
@Entity
public class TransaktionEntity {
    @Id
    @Column(name = "TRANSAKTIONSNUMMER")
    private String transaktionsnummer;

    @ManyToOne
    @JoinColumn(name = "BANKKONTO")
    private BankkontoEntity bankkonto;

    @Column(name = "EMPFAENGER")
    private String empfaenger;

    @Column(name = "BETRAG")
    private double betrag;

    @Column(name = "TRANSAKTIONSDATUM")
    private Date transaktionsdatum;

    @Column(name = "TRANSAKTIONSSTATUS")
    private String transaktionsstatus;

    @Column(name = "TRANSAKTIONSART")
    private String transaktionsart;

    public TransaktionEntity() {
    }

    public TransaktionEntity(Transaktion transaktion) {
        this.transaktionsnummer = transaktion.getTransaktionsnummer();
        if (transaktion.getKonto() != null) {
            this.bankkonto = new BankkontoEntity(transaktion.getKonto());
        }
        this.empfaenger = transaktion.getEmpfänger();
        this.betrag = transaktion.getBetrag();
        this.transaktionsdatum = transaktion.getTransaktionsdatum();
        this.transaktionsstatus = transaktion.getTransaktionsstatus();
        this.transaktionsart = transaktion.getTransaktionsart();
    }

    public String getTransaktionsnummer() {
        return transaktionsnummer;
    }

    public BankkontoEntity getBankkonto() {
        return bankkonto;
    }

    public String getEmpfaenger() {
        return empfaenger;
    }

    public double getBetrag() {
        return betrag;
    }

    public Date getTransaktionsdatum() {
        return transaktionsdatum;
    }

    public String getTransaktionsstatus() {
        return transaktionsstatus;
    }

    public String getTransaktionsart() {
        return transaktionsart;
    }

    public Transaktion toTransaktion() {
        return new Transaktion(
                bankkonto != null ? bankkonto.toBankkonto() : null,
                transaktionsnummer,
                transaktionsdatum,
                betrag,
                transaktionsart,
                empfaenger,
                transaktionsstatus);
    }
}
