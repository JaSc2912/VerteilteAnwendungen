package de.hsnr.bankserver.dataaccess.entities;

import jakarta.persistence.*;

/**
 * JPA Entity für Kreditantrag
 */
@Entity
@Table(name = "KREDITANTRAG")
public class KreditantragEntity {
    
    @Id
    @Column(name = "KREDITANTRAGSNUMMER")
    private String kreditantragsnummer;
    
    @Column(name = "ANTRAGSTELLER")
    private String antragsteller;
    
    @Column(name = "KREDITSUMME")
    private Double kreditsumme;
    
    @Column(name = "KREDITLAUFZEIT")
    private String kreditlaufzeit;
    
    @Column(name = "KREDITSTATUS")
    private String kreditstatus;
    
    @Column(name = "GENEHMIGENDER_MITARBEITER")
    private String genehmigenderMitarbeiter;

    public KreditantragEntity() {
    }

    public KreditantragEntity(String kreditantragsnummer, String antragsteller, Double kreditsumme, 
                              String kreditlaufzeit, String kreditstatus, String genehmigenderMitarbeiter) {
        this.kreditantragsnummer = kreditantragsnummer;
        this.antragsteller = antragsteller;
        this.kreditsumme = kreditsumme;
        this.kreditlaufzeit = kreditlaufzeit;
        this.kreditstatus = kreditstatus;
        this.genehmigenderMitarbeiter = genehmigenderMitarbeiter;
    }

    public String getKreditantragsnummer() {
        return kreditantragsnummer;
    }

    public void setKreditantragsnummer(String kreditantragsnummer) {
        this.kreditantragsnummer = kreditantragsnummer;
    }

    public String getAntragsteller() {
        return antragsteller;
    }

    public void setAntragsteller(String antragsteller) {
        this.antragsteller = antragsteller;
    }

    public Double getKreditsumme() {
        return kreditsumme;
    }

    public void setKreditsumme(Double kreditsumme) {
        this.kreditsumme = kreditsumme;
    }

    public String getKreditlaufzeit() {
        return kreditlaufzeit;
    }

    public void setKreditlaufzeit(String kreditlaufzeit) {
        this.kreditlaufzeit = kreditlaufzeit;
    }

    public String getKreditstatus() {
        return kreditstatus;
    }

    public void setKreditstatus(String kreditstatus) {
        this.kreditstatus = kreditstatus;
    }

    public String getGenehmigenderMitarbeiter() {
        return genehmigenderMitarbeiter;
    }

    public void setGenehmigenderMitarbeiter(String genehmigenderMitarbeiter) {
        this.genehmigenderMitarbeiter = genehmigenderMitarbeiter;
    }
}
