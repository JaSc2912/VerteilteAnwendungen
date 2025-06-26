package de.hsnr.bank.client.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Customer model for client-side operations
 */
public class Kunde implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long kundennummer;
    private String name;
    private String adresse;
    private String telefonnummer;
    private String email;
    private LocalDate geburtsdatum;
    private String kundenstatus;

    // Default constructor
    public Kunde() {
    }

    // Constructor with parameters
    public Kunde(String name, String email, String adresse, String telefonnummer) {
        this.name = name;
        this.email = email;
        this.adresse = adresse;
        this.telefonnummer = telefonnummer;
        this.kundenstatus = "AKTIV";
    }

    // Getters and setters
    public Long getKundennummer() {
        return kundennummer;
    }

    public void setKundennummer(Long kundennummer) {
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

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getKundenstatus() {
        return kundenstatus;
    }

    public void setKundenstatus(String kundenstatus) {
        this.kundenstatus = kundenstatus;
    }

    @Override
    public String toString() {
        return "Kunde{" +
                "kundennummer=" + kundennummer +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
