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
     Double kreditsumme;

    @Column(name = "LAUFZEIT")
     String laufzeit;

    @Column(name = "ZINS")
     Double zins;

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