package de.hsnr.bankserver.dataaccess.entities;

import jakarta.persistence.*;
import java.util.Date;

/**
 * JPA Entity für Bankkonto
 */
@Entity
@Table(name = "BANKKONTO")
public class BankkontoEntity {
    
    @Id
    @Column(name = "IBAN")
    private String iban;
    
    @Column(name = "KONTOINHABER")
    private String kontoinhaber;
    
    @Column(name = "KONTOART")
    private String kontoart;
    
    @Column(name = "KONTOSTAND")
    private Double kontostand;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "KONTOERÖFFNUNGSDATUM")
    private Date kontoeröffnungsdatum;
    
    @Column(name = "KONTOSTATUS")
    private String kontostatus;

    public BankkontoEntity() {
    }

    public BankkontoEntity(String iban, String kontoinhaber, String kontoart, Double kontostand, 
                           Date kontoeröffnungsdatum, String kontostatus) {
        this.iban = iban;
        this.kontoinhaber = kontoinhaber;
        this.kontoart = kontoart;
        this.kontostand = kontostand;
        this.kontoeröffnungsdatum = kontoeröffnungsdatum;
        this.kontostatus = kontostatus;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getKontoinhaber() {
        return kontoinhaber;
    }

    public void setKontoinhaber(String kontoinhaber) {
        this.kontoinhaber = kontoinhaber;
    }

    public String getKontoart() {
        return kontoart;
    }

    public void setKontoart(String kontoart) {
        this.kontoart = kontoart;
    }

    public Double getKontostand() {
        return kontostand;
    }

    public void setKontostand(Double kontostand) {
        this.kontostand = kontostand;
    }

    public Date getKontoeröffnungsdatum() {
        return kontoeröffnungsdatum;
    }

    public void setKontoeröffnungsdatum(Date kontoeröffnungsdatum) {
        this.kontoeröffnungsdatum = kontoeröffnungsdatum;
    }

    public String getKontostatus() {
        return kontostatus;
    }

    public void setKontostatus(String kontostatus) {
        this.kontostatus = kontostatus;
    }
}
