package de.hsnr.bankclient;

import java.io.Serializable;

/**
 * Transfer Object für Kreditantrag
 */
public class KreditantragTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String kreditantragsnummer;
    private String antragsteller;
    private Double kreditsumme; // Changed from BigDecimal to Double
    private String kreditlaufzeit; // Changed from Integer to String (e.g., "60 Monate")
    private String kreditstatus;
    private String genehmigenderMitarbeiter;

    // Default constructor
    public KreditantragTO() {
    }

    // Constructor with all fields
    public KreditantragTO(String kreditantragsnummer, String antragsteller,
            Double kreditsumme, String kreditlaufzeit,
            String kreditstatus, String genehmigenderMitarbeiter) {
        this.kreditantragsnummer = kreditantragsnummer;
        this.antragsteller = antragsteller;
        this.kreditsumme = kreditsumme;
        this.kreditlaufzeit = kreditlaufzeit;
        this.kreditstatus = kreditstatus;
        this.genehmigenderMitarbeiter = genehmigenderMitarbeiter;
    }

    // Getters and Setters
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
