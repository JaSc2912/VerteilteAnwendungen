package de.hsnr.bank.dataaccess;

import de.hsnr.bank.entities.Kreditantrag;
import jakarta.persistence.*;

@Entity
public class KreditantragEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kreditantragsNummer;

    @Column(name = "KREDITSUMME")
    Double kreditsumme;

    @Column(name = "LAUFZEIT")
    String laufzeit;

    @Column(name = "ZINS")
    Double zins;

    @Column(name = "STATUS")
    String status;

    @ManyToOne
    @JoinColumn(name = "KUNDE_ID")
    KundeEntity antragssteller;

    public KreditantragEntity() {
    }

    public KreditantragEntity(Kreditantrag kreditantrag) {
        this.kreditantragsNummer = kreditantrag.getKreditantragsNummer();
        this.kreditsumme = kreditantrag.getKreditsumme();
        this.laufzeit = kreditantrag.getLaufzeit();
        this.zins = kreditantrag.getZins();
        this.status = kreditantrag.getStatus();
        this.antragssteller = new KundeEntity(kreditantrag.getAntragssteller());
    }

    public Kreditantrag toKreditantrag() {
        return new Kreditantrag(
                kreditantragsNummer, kreditsumme, laufzeit, zins, status,
                antragssteller != null ? antragssteller.toKunde() : null);
    }
}