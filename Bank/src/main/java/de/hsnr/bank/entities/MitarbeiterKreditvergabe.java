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
public class MitarbeiterKreditvergabe extends Benutzer {

    private String mitarbeiterID;
    private String abteilung;

    public MitarbeiterKreditvergabe() {
        super();
    }

    public MitarbeiterKreditvergabe(String benutzername, String passwort) {
        super(benutzername, passwort);
    }

    public MitarbeiterKreditvergabe(String benutzername, String passwort, String mitarbeiterID, String abteilung) {
        super(benutzername, passwort);
        this.mitarbeiterID = mitarbeiterID;
        this.abteilung = abteilung;
    }

    public MitarbeiterKreditvergabe(String benutzername, String name, String passwort, String telefonnummer,
            String mitarbeiterID, String abteilung) {
        super(benutzername, name, passwort, telefonnummer, RolleT.KREDITVERGABE);
        this.mitarbeiterID = mitarbeiterID;
        this.abteilung = abteilung;
    }

    @Override
    public RolleT getRolle() {
        return RolleT.KREDITVERGABE;
    }

    public String getMitarbeiterID() {
        return mitarbeiterID;
    }

    public void setMitarbeiterID(String mitarbeiterID) {
        this.mitarbeiterID = mitarbeiterID;
    }

    public String getAbteilung() {
        return abteilung;
    }

    public void setAbteilung(String abteilung) {
        this.abteilung = abteilung;
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

    @Override
    public String toString() {
        return "MitarbeiterKreditvergabe{" +
                "benutzername=" + getBenutzername() +
                ", mitarbeiterID=" + mitarbeiterID +
                ", abteilung=" + abteilung +
                '}';
    }
}
