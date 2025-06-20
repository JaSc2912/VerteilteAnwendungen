/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.entities;

/**
 *
 * @author jannn
 */
public class Kreditantrag {
    private Long kreditantragsNummer;
    private Double kreditsumme;
    private String laufzeit;
    private Double zins;
    private String status; // "In Arbeit", "Akzeptiert", "Abgelehnt"
    private Kunde antragssteller;

    public Kreditantrag() {
    }

    public Kreditantrag(Long kreditantragsNummer, Double kreditsumme, String laufzeit, Double zins, String status,
            Kunde antragssteller) {
        this.kreditantragsNummer = kreditantragsNummer;
        this.kreditsumme = kreditsumme;
        this.laufzeit = laufzeit;
        this.zins = zins;
        this.status = status;
        this.antragssteller = antragssteller;
    }

    // Getter und Setter für alle Felder
    public Long getKreditantragsNummer() {
        return kreditantragsNummer;
    }

    public void setKreditantragsNummer(Long kreditantragsNummer) {
        this.kreditantragsNummer = kreditantragsNummer;
    }

    public Double getKreditsumme() {
        return kreditsumme;
    }

    public void setKreditsumme(Double kreditsumme) {
        this.kreditsumme = kreditsumme;
    }

    public String getLaufzeit() {
        return laufzeit;
    }

    public void setLaufzeit(String laufzeit) {
        this.laufzeit = laufzeit;
    }

    public Double getZins() {
        return zins;
    }

    public void setZins(Double zins) {
        this.zins = zins;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Kunde getAntragssteller() {
        return antragssteller;
    }

    public void setAntragssteller(Kunde antragssteller) {
        this.antragssteller = antragssteller;
    }
}