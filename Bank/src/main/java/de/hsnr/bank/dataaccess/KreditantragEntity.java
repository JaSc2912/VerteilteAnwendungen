package de.hsnr.bank.dataaccess;

import de.hsnr.bank.entities.Kreditantrag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class KreditantragEntity {

    @Id
    @Column(name = "KONTO")
    private String konto;

    @Column(name = "KREDITSUMME")
    private Double kreditsumme;

    @Column(name = "LAUFZEIT")
    private String laufzeit;

    @Column(name = "ZINS")
    private Double zins;

    public KreditantragEntity() {}

    public KreditantragEntity(Kreditantrag kreditantrag) {
        this.konto = kreditantrag.getKonto();
        this.kreditsumme = kreditantrag.getKreditsumme();
        this.laufzeit = kreditantrag.getLaufzeit();
        this.zins = kreditantrag.getZins();
    }

    public Kreditantrag toKreditantrag() {
        return new Kreditantrag(kreditsumme, konto, laufzeit, zins);
    }
}