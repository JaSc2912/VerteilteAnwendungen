/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.entities;

/**
 *
 * @author jannn
 */
public class Kreditantrag{
    private Double kreditsumme;
    private String konto;
    private String laufzeit;
    private Double zins;

    public Kreditantrag() {
    }

    public Kreditantrag(Double kreditsumme, String konto, String laufzeit, Double zins) {
        this.kreditsumme = kreditsumme;
        this.konto = konto;
        this.laufzeit = laufzeit;
        this.zins = zins;
    }
    public Double getKreditsumme() {
        return kreditsumme;
    }
    public void setKreditsumme(Double kreditsumme) {
        this.kreditsumme = kreditsumme;
    }
    public String getKonto() {
        return konto;
    }
    public void setKonto(String konto) {
        this.konto = konto;
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
    
}