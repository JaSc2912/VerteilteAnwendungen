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
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
public class BenutzerEntity {

    @Id
    @Column(name = "BENUTZERNAME")
    protected String benutzername;

    @Column(name = "PASSWORT")
    protected String passwort;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "TELEFONNUMMER")
    protected String telefonnummer;

    @Column(name = "ROLLE")
    @Enumerated(EnumType.STRING)
    protected RolleT rolle;

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

}
