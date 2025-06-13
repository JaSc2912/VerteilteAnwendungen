/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.entities;

/**
 *
 * @author muell
 */
public class Bankkonto {
    private  String iban;
    private String kontoart;
    private Double kontostand;
    private Date kontoeroeffnungsdatum;
    private String kontostatus;
    private Kunde besitzer;

    public Bankkonto(String iban, String kontoart, Double kontostand, Date kontoeroeffnungsdatum, String kontostatus, Kunde besitzer) {
        this.iban = iban;
        this.kontoart = kontoart;
        this.kontostand = kontostand;
        this.kontoeroeffnungsdatum = kontoeroeffnungsdatum;
        this.kontostatus = kontostatus;
        this.besitzer = besitzer;
    }
    public String getIban() {
        return iban;
    }
    public void setIban(String iban) {
        this.iban = iban;
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
    public Date getKontoeroeffnungsdatum() {
        return kontoeroeffnungsdatum;
    }
    public void setKontoeroeffnungsdatum(Date kontoeroeffnungsdatum) {
        this.kontoeroeffnungsdatum = kontoeroeffnungsdatum;
    }
    public String getKontostatus() {
        return kontostatus;
    }
    public void setKontostatus(String kontostatus) {
        this.kontostatus = kontostatus;
    }
    public Kunde getBesitzer() {
        return besitzer;
    }
    public void setBesitzer(Kunde besitzer) {
        this.besitzer = besitzer;
    }
    
}
