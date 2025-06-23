package de.hsnr.bank.dataaccess;

import de.hsnr.bank.entities.Kreditantrag;
import jakarta.persistence.*;

@Entity
public class KreditantragEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long kreditantragsNummer;

    @Column(name = "KREDITSUMME")
    protected Double kreditsumme;

    @Column(name = "LAUFZEIT")
    protected String laufzeit;

    @Column(name = "ZINS")
    protected Double zins;

    @Column(name = "STATUS")
    protected String status;

    @ManyToOne
    @JoinColumn(name = "KUNDE_ID")
    protected KundeEntity antragssteller;

    public KreditantragEntity() {
    }

    public KreditantragEntity(Kreditantrag kreditantrag) {
        this.kreditantragsNummer = kreditantrag.getKreditantragsNummer();
        this.kreditsumme = kreditantrag.getKreditsumme();
        this.laufzeit = kreditantrag.getLaufzeit();
        this.zins = kreditantrag.getZins();
        this.status = kreditantrag.getStatus();
        // The relationship is now set in the DAO, not here.
        // this.antragssteller = new KundeEntity(kreditantrag.getAntragssteller());
    }

    public Kreditantrag toKreditantrag() {
        return new Kreditantrag(
                kreditantragsNummer, kreditsumme, laufzeit, zins, status,
                antragssteller != null ? antragssteller.toKunde() : null);
    }
}