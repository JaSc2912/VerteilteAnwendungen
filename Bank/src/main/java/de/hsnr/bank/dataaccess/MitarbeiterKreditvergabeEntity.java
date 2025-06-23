/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.dataaccess;

import de.hsnr.bank.entities.MitarbeiterKreditvergabe;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/**
 *
 * @author jannn
 */
@Entity
public class MitarbeiterKreditvergabeEntity extends BenutzerEntity {

    @Column(name = "MITARBEITER_ID")
    protected String mitarbeiterID;

    @Column(name = "ABTEILUNG")
    protected String abteilung;

    public MitarbeiterKreditvergabeEntity() {
        super();
    }

    public MitarbeiterKreditvergabeEntity(MitarbeiterKreditvergabe mitarbeiter) {
        super(mitarbeiter);
        this.mitarbeiterID = mitarbeiter.getMitarbeiterID();
        this.abteilung = mitarbeiter.getAbteilung();
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

    public MitarbeiterKreditvergabe toMitarbeiterKreditvergabe() {
        MitarbeiterKreditvergabe mitarbeiter = new MitarbeiterKreditvergabe(
                this.benutzername,
                this.name,
                this.passwort,
                this.telefonnummer,
                this.mitarbeiterID,
                this.abteilung);
        mitarbeiter.setRolle(this.rolle);
        return mitarbeiter;
    }
}
