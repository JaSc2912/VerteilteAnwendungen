/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.dataaccess;

import de.hsnr.bank.entities.Kunde;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Date;

/**
 *
 * @author muell
 */
@Entity
public class KundeEntity {

    @Id
    @Column(name = "KUNDENNUMMER")
    protected String kundennummer;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "ADRESSE")
    protected String adresse;

    @Column(name = "KUNDENSTATUS")
    protected String kundenstatus;

    @Column(name = "GEBURTSDATUM")
    protected Date geburtsdatum;

    @Column(name = "TELEFONNUMMER")
    protected String telefonnummer;

    @Column(name = "EMAIL")
    protected String email;

    public KundeEntity() {
    }

    public KundeEntity(Kunde kunde) {
        this.kundennummer = kunde.getKundennummer();
        this.name = kunde.getName();
        this.adresse = kunde.getAdresse();
        this.kundenstatus = kunde.getKundenstatus();
        this.geburtsdatum = kunde.getGeburtsdatum();
        this.telefonnummer = kunde.getTelefonnummer();
        this.email = kunde.getEmail();
    }

    public Kunde toKunde() {
        return new Kunde(kundennummer, name, adresse, telefonnummer, email, geburtsdatum, kundenstatus);
    }
}
