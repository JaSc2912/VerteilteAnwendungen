package de.hsnr.bank.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Bank account entity for banking application
 */
@Entity
@Table(name = "BANKKONTO")
@NamedQueries({
        @NamedQuery(name = "Bankkonto.findAll", query = "SELECT b FROM Bankkonto b"),
        @NamedQuery(name = "Bankkonto.findByIban", query = "SELECT b FROM Bankkonto b WHERE b.iban = :iban"),
        @NamedQuery(name = "Bankkonto.findByKunde", query = "SELECT b FROM Bankkonto b WHERE b.kunde = :kunde"),
        @NamedQuery(name = "Bankkonto.findByKundennummer", query = "SELECT b FROM Bankkonto b WHERE b.kunde.kundennummer = :kundennummer")
})
public class Bankkonto implements Serializable {

    @Id
    @NotBlank
    @Column(name = "IBAN", length = 34)
    private String iban;

    @NotNull
    @Column(name = "KONTOSTAND", precision = 15, scale = 2)
    private BigDecimal kontostand;

    @Column(name = "KONTOSTATUS", length = 20)
    private String kontostatus;

    @Column(name = "KREDITLIMIT", precision = 15, scale = 2)
    private BigDecimal kreditlimit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KUNDENNUMMER", nullable = false)
    private Kunde kunde;

    @OneToMany(mappedBy = "bankkonto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaktion> transaktionen;

    // Constructors
    public Bankkonto() {
    }

    public Bankkonto(String iban, Kunde kunde) {
        this.iban = iban;
        this.kunde = kunde;
        this.kontostand = BigDecimal.ZERO;
        this.kontostatus = "AKTIV";
        this.kreditlimit = BigDecimal.ZERO;
    }

    public Bankkonto(String iban, Kunde kunde, BigDecimal anfangskontostand) {
        this.iban = iban;
        this.kunde = kunde;
        this.kontostand = anfangskontostand;
        this.kontostatus = "AKTIV";
        this.kreditlimit = BigDecimal.ZERO;
    }

    // Getters and Setters
    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public BigDecimal getKontostand() {
        return kontostand;
    }

    public void setKontostand(BigDecimal kontostand) {
        this.kontostand = kontostand;
    }

    public String getKontostatus() {
        return kontostatus;
    }

    public void setKontostatus(String kontostatus) {
        this.kontostatus = kontostatus;
    }

    public BigDecimal getKreditlimit() {
        return kreditlimit;
    }

    public void setKreditlimit(BigDecimal kreditlimit) {
        this.kreditlimit = kreditlimit;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public List<Transaktion> getTransaktionen() {
        return transaktionen;
    }

    public void setTransaktionen(List<Transaktion> transaktionen) {
        this.transaktionen = transaktionen;
    }

    @Override
    public String toString() {
        return "Bankkonto{" +
                "iban='" + iban + '\'' +
                ", kontostand=" + kontostand +
                ", kontostatus='" + kontostatus + '\'' +
                '}';
    }
}
