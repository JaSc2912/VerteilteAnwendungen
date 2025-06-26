package de.hsnr.bank.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Credit application entity for banking application
 */
@Entity
@Table(name = "KREDITANTRAG")
@NamedQueries({
        @NamedQuery(name = "Kreditantrag.findAll", query = "SELECT k FROM Kreditantrag k"),
        @NamedQuery(name = "Kreditantrag.findByKreditantragsNummer", query = "SELECT k FROM Kreditantrag k WHERE k.kreditantragsNummer = :kreditantragsNummer"),
        @NamedQuery(name = "Kreditantrag.findByAntragssteller", query = "SELECT k FROM Kreditantrag k WHERE k.antragssteller = :antragssteller"),
        @NamedQuery(name = "Kreditantrag.findByStatus", query = "SELECT k FROM Kreditantrag k WHERE k.status = :status"),
        @NamedQuery(name = "Kreditantrag.findPending", query = "SELECT k FROM Kreditantrag k WHERE k.status = 'OFFEN'")
})
public class Kreditantrag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KREDITANTRAGS_NUMMER")
    private Long kreditantragsNummer;

    @NotNull
    @Column(name = "KREDITSUMME", precision = 15, scale = 2, nullable = false)
    private BigDecimal kreditsumme;

    @Column(name = "LAUFZEIT")
    private Integer laufzeit;

    @Column(name = "ZINSSATZ", precision = 5, scale = 2)
    private BigDecimal zinssatz;

    @Column(name = "ANTRAGSDATUM")
    private LocalDate antragsdatum;

    @Column(name = "STATUS", length = 20)
    private String status;

    @Column(name = "BEGRUENDUNG", length = 500)
    private String begruendung;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KUNDENNUMMER", nullable = false)
    private Kunde antragssteller;

    @Column(name = "BEARBEITER", length = 50)
    private String bearbeiter;

    @Column(name = "BEARBEITUNGSDATUM")
    private LocalDate bearbeitungsdatum;

    // Constructors
    public Kreditantrag() {
        this.antragsdatum = LocalDate.now();
        this.status = "OFFEN";
    }

    public Kreditantrag(BigDecimal kreditsumme, Integer laufzeit, Kunde antragssteller) {
        this();
        this.kreditsumme = kreditsumme;
        this.laufzeit = laufzeit;
        this.antragssteller = antragssteller;
    }

    public Kreditantrag(BigDecimal kreditsumme, Integer laufzeit, String begruendung, Kunde antragssteller) {
        this();
        this.kreditsumme = kreditsumme;
        this.laufzeit = laufzeit;
        this.begruendung = begruendung;
        this.antragssteller = antragssteller;
    }

    // Getters and Setters
    public Long getKreditantragsNummer() {
        return kreditantragsNummer;
    }

    public void setKreditantragsNummer(Long kreditantragsNummer) {
        this.kreditantragsNummer = kreditantragsNummer;
    }

    public BigDecimal getKreditsumme() {
        return kreditsumme;
    }

    public void setKreditsumme(BigDecimal kreditsumme) {
        this.kreditsumme = kreditsumme;
    }

    public Integer getLaufzeit() {
        return laufzeit;
    }

    public void setLaufzeit(Integer laufzeit) {
        this.laufzeit = laufzeit;
    }

    public BigDecimal getZinssatz() {
        return zinssatz;
    }

    public void setZinssatz(BigDecimal zinssatz) {
        this.zinssatz = zinssatz;
    }

    public LocalDate getAntragsdatum() {
        return antragsdatum;
    }

    public void setAntragsdatum(LocalDate antragsdatum) {
        this.antragsdatum = antragsdatum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBegruendung() {
        return begruendung;
    }

    public void setBegruendung(String begruendung) {
        this.begruendung = begruendung;
    }

    public Kunde getAntragssteller() {
        return antragssteller;
    }

    public void setAntragssteller(Kunde antragssteller) {
        this.antragssteller = antragssteller;
    }

    public String getBearbeiter() {
        return bearbeiter;
    }

    public void setBearbeiter(String bearbeiter) {
        this.bearbeiter = bearbeiter;
    }

    public LocalDate getBearbeitungsdatum() {
        return bearbeitungsdatum;
    }

    public void setBearbeitungsdatum(LocalDate bearbeitungsdatum) {
        this.bearbeitungsdatum = bearbeitungsdatum;
    }

    @Override
    public String toString() {
        return "Kreditantrag{" +
                "kreditantragsNummer=" + kreditantragsNummer +
                ", kreditsumme=" + kreditsumme +
                ", laufzeit=" + laufzeit +
                ", status='" + status + '\'' +
                ", antragsdatum=" + antragsdatum +
                '}';
    }
}
