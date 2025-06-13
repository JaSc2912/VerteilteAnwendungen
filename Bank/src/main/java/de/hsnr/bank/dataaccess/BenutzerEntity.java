/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.dataaccess;

import de.hsnr.bank.entities.Benutzer;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import de.hsnr.bank.usecases.RolleT;
import jakarta.persistence.Entity;

@Entity
public class BenutzerEntity {

    @Column(name = "BENUTZERKENNUNG")
    @Id
    private String benutzername;
    @Column(name = "PASSWORT")
    private String passwort;
    @Column(name = "NAME")
    private String name;
    @Column(name = "TELEFONNUMMER")
    private String telefonnummer;
    @Column(name = "ROLLE")
    private RolleT rolle;

    public BenutzerEntity() {
    }

    public BenutzerEntity(Benutzer benutzer) {
        this.benutzername = benutzer.getBenutzername();
        this.name = benutzer.getName();
    }

    public String getBenutzername() {
        return this.benutzername;
    }
    
    public String getPasswort() {
        return this.passwort;
    }

    public String getName() {
        return name;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public RolleT getRolle() {
        return rolle;
    }

    public Benutzer toBenutzer() {
        return new Benutzer(this.benutzername, this.name, this.passwort, this.telefonnummer, this.rolle);
    }

    public void setRolle(RolleT rolle) {
        this.rolle = rolle;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }
}
