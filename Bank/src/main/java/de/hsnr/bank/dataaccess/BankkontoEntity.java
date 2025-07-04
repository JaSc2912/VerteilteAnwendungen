/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.dataaccess;

import de.hsnr.bank.entities.Bankkonto;
import de.hsnr.bank.entities.Kunde;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Date;

/**
 *
 * @author muell
 */
@Entity
public class BankkontoEntity {

    @Id
    @Column(name = "IBAN")
    protected String iban;

    @Column(name = "KONTOART")
    protected String kontoart;

    @Column(name = "KONTOSTAND")
    protected Double kontostand;

    @Column(name = "KONTOEROEFFNUNGSDATUM")
    protected Date kontoeroeffnungsdatum;

    @Column(name = "KONTOSTATUS")
    protected String kontostatus;

    @ManyToOne
    protected KundeEntity besitzer;

    public BankkontoEntity() {
    }

    public BankkontoEntity(Bankkonto bankkonto) {
        this.iban = bankkonto.getIban();
        this.kontoart = bankkonto.getKontoart();
        this.kontostand = bankkonto.getKontostand();
        this.kontoeroeffnungsdatum = bankkonto.getKontoeroeffnungsdatum();
        this.kontostatus = bankkonto.getKontostatus();
        // The relationship is now set in the DAO, not here.
        // if (bankkonto.getBesitzer() != null) {
        // this.besitzer = new KundeEntity(bankkonto.getBesitzer());
        // }
    }

    public String getIban() {
        return iban;
    }

    public String getKontoart() {
        return kontoart;
    }

    public Double getKontostand() {
        return kontostand;
    }

    public Date getKontoeroeffnungsdatum() {
        return kontoeroeffnungsdatum;
    }

    public String getKontostatus() {
        return kontostatus;
    }

    public KundeEntity getBesitzer() {
        return besitzer;
    }

    public Bankkonto toBankkonto() {
        Kunde kunde = besitzer != null ? besitzer.toKunde() : null;
        return new Bankkonto(iban, kontoart, kontostand, kontoeroeffnungsdatum, kontostatus, kunde);
    }
}
