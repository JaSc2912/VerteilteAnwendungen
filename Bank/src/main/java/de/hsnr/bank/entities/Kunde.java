/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.entities;

/**
 *
 * @author muell
 */
public class Kunde {
    private String kundennummer;
    private String name;
    private String adresse;
    private String telefonnummer;
    private String email;
    private String geburtsdatum;
    private String kundenstatus;
    
    public Kunde() {
    }
    public Kunde(String kundennummer, String name, String adresse, String telefonnummer, String email, String geburtsdatum, String kundenstatus) {
        this.kundennummer = kundennummer;
        this.name = name;
        this.adresse = adresse;
        this.telefonnummer = telefonnummer;
        this.email = email;
        this.geburtsdatum = geburtsdatum;
        this.kundenstatus = kundenstatus;
    }
    public String getKundennummer() {
        return kundennummer;
    }
    public void setKundennummer(String kundennummer) {
        this.kundennummer = kundennummer;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public String getTelefonnummer() {
        return telefonnummer;
    }
    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getGeburtsdatum() {
        return geburtsdatum;
    }
    public void setGeburtsdatum(String geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }
    public String getKundenstatus() {
        return kundenstatus;
    }
    public void setKundenstatus(String kundenstatus) {
        this.kundenstatus = kundenstatus;
    }
}
