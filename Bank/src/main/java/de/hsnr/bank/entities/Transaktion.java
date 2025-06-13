/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.entities;

/**
 *
 * @author muell
 */
public class Transaktion {
    private Bankkonto konto;
    private String transaktionsnummer;
    private java.util.Date transaktionsdatum;
    private double betrag;
    private String transaktionsart;
    private String empfänger;
    private String transaktionsstatus;
    public Transaktion() {
    }
    public Transaktion(Bankkonto konto, String transaktionsnummer, java.util.Date transaktionsdatum, double
            betrag, String transaktionsart, String empfänger, String transaktionsstatus) {
        this.konto = konto;
        this.transaktionsnummer = transaktionsnummer;
        this.transaktionsdatum = transaktionsdatum;
        this.betrag = betrag; 
        this.transaktionsart = transaktionsart;
        this.empfänger = empfänger;
        this.transaktionsstatus = transaktionsstatus;
            }
    public Bankkonto getKonto() {
        return konto;
    }   
    public void setKonto(Bankkonto konto) {
        this.konto = konto;
    }
    public String getTransaktionsnummer() {
        return transaktionsnummer;
    }
    public void setTransaktionsnummer(String transaktionsnummer) {
        this.transaktionsnummer = transaktionsnummer;
    }
    public java.util.Date getTransaktionsdatum() {
        return transaktionsdatum;
    }
    public void setTransaktionsdatum(java.util.Date transaktionsdatum) {
        this.transaktionsdatum = transaktionsdatum;
    }
    public double getBetrag() {
        return betrag;
    }
    public void setBetrag(double betrag) {
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
