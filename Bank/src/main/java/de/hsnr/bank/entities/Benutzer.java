/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.entities;

import de.hsnr.bank.usecases.RolleT;

/**
 *
 * @author muell
 */
public class Benutzer {

    String benutzername;
    String passwort;
    String name;
    String telefonnummer;
    RolleT rolle;

    public Benutzer() {
    }

    public Benutzer(String benutzername, String passwort) {

    }

    public Benutzer(String benutzername, String name, String passwort, String telefonnummer, RolleT rolle) {
        this.benutzername = benutzername;
        this.name = name;
        this.passwort = passwort;
        this.telefonnummer = telefonnummer;
        this.rolle = rolle;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public RolleT getRolle() {
        return rolle;
    }

    public void setRolle(RolleT rolle) {
        this.rolle = rolle;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
}
