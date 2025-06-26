package de.hsnr.bank.client.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Credit application model for client-side operations
 */
public class Kreditantrag implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long kreditantragsNummer;
    private BigDecimal kreditsumme;
    private Integer laufzeit;
    private BigDecimal zinssatz;
    private String verwendungszweck;
    private LocalDate antragsdatum;
    private String status;
    private String bearbeiter;
    private LocalDate bearbeitungsdatum;
    private String begruendung;
    private Kunde antragssteller;

    // Default constructor
    public Kreditantrag() {
    }

    // Constructor with parameters
    public Kreditantrag(BigDecimal kreditsumme, Integer laufzeit, String verwendungszweck, Kunde antragssteller) {
        this.kreditsumme = kreditsumme;
        this.laufzeit = laufzeit;
        this.verwendungszweck = verwendungszweck;
        this.antragssteller = antragssteller;
        this.antragsdatum = LocalDate.now();
        this.status = "OFFEN";
    }

    // Getters and setters
    public Long getKreditantragsNummer() {
        return kreditantragsNummer;
    }

    public void setKreditantragsNummer(Long kreditantragsNummer) {
        this.kreditantragsNummer = kreditantragsNummer;
    }

    /**
     * Alias for getKreditantragsNummer() - used in JSF beans
     */
    public Long getId() {
        return kreditantragsNummer;
    }

    /**
     * Alias for setKreditantragsNummer() - used in JSF beans
     */
    public void setId(Long id) {
        this.kreditantragsNummer = id;
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

    public String getVerwendungszweck() {
        return verwendungszweck;
    }

    public void setVerwendungszweck(String verwendungszweck) {
        this.verwendungszweck = verwendungszweck;
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

    @Override
    public String toString() {
        return "Kreditantrag{" +
                "kreditantragsNummer=" + kreditantragsNummer +
                ", kreditsumme=" + kreditsumme +
                ", laufzeit=" + laufzeit +
                ", status='" + status + '\'' +
                '}';
    }
}
