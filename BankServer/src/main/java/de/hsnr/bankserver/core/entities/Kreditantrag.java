package de.hsnr.bankserver.core.entities;

/**
 * Business Object für Kreditantrag
 */
public class Kreditantrag {
    
    private String kreditantragsnummer;
    private String antragsteller;
    private Double kreditsumme;
    private String kreditlaufzeit;
    private String kreditstatus; // genehmigt, abgelehnt, in Bearbeitung
    private String genehmigenderMitarbeiter;

    public Kreditantrag() {
    }

    public Kreditantrag(String kreditantragsnummer, String antragsteller, Double kreditsumme, 
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
