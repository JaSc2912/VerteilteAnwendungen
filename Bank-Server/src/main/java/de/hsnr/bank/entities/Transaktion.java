package de.hsnr.bank.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction entity for banking application
 */
@Entity
@Table(name = "TRANSAKTION")
@NamedQueries({
        @NamedQuery(name = "Transaktion.findAll", query = "SELECT t FROM Transaktion t"),
        @NamedQuery(name = "Transaktion.findByBankkonto", query = "SELECT t FROM Transaktion t WHERE t.bankkonto = :bankkonto ORDER BY t.transaktionsdatum DESC"),
        @NamedQuery(name = "Transaktion.findByIban", query = "SELECT t FROM Transaktion t WHERE t.bankkonto.iban = :iban ORDER BY t.transaktionsdatum DESC"),
        @NamedQuery(name = "Transaktion.findByStatus", query = "SELECT t FROM Transaktion t WHERE t.transaktionsstatus = :status")
})
public class Transaktion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSAKTIONS_ID")
    private Long transaktionsId;

    @NotNull
    @Column(name = "BETRAG", precision = 15, scale = 2, nullable = false)
    private BigDecimal betrag;

    @Column(name = "TRANSAKTIONSART", length = 50)
    private String transaktionsart;

    @Column(name = "EMPFAENGER", length = 100)
    private String empfaenger;

    @Column(name = "VERWENDUNGSZWECK", length = 500)
    private String verwendungszweck;

    @Column(name = "TRANSAKTIONSDATUM")
    private LocalDateTime transaktionsdatum;

    @Column(name = "TRANSAKTIONSSTATUS", length = 20)
    private String transaktionsstatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IBAN", nullable = false)
    private Bankkonto bankkonto;

    // Constructors
    public Transaktion() {
        this.transaktionsdatum = LocalDateTime.now();
        this.transaktionsstatus = "BEARBEITET";
    }

    public Transaktion(BigDecimal betrag, String transaktionsart, Bankkonto bankkonto) {
        this();
        this.betrag = betrag;
        this.transaktionsart = transaktionsart;
        this.bankkonto = bankkonto;
    }

    public Transaktion(BigDecimal betrag, String transaktionsart, String empfaenger,
            String verwendungszweck, Bankkonto bankkonto) {
        this();
        this.betrag = betrag;
        this.transaktionsart = transaktionsart;
        this.empfaenger = empfaenger;
        this.verwendungszweck = verwendungszweck;
        this.bankkonto = bankkonto;
    }

    // Getters and Setters
    public Long getTransaktionsId() {
        return transaktionsId;
    }

    public void setTransaktionsId(Long transaktionsId) {
        this.transaktionsId = transaktionsId;
    }

    public BigDecimal getBetrag() {
        return betrag;
    }

    public void setBetrag(BigDecimal betrag) {
        this.betrag = betrag;
    }

    public String getTransaktionsart() {
        return transaktionsart;
    }

    public void setTransaktionsart(String transaktionsart) {
        this.transaktionsart = transaktionsart;
    }

    public String getEmpfaenger() {
        return empfaenger;
    }

    public void setEmpfaenger(String empfaenger) {
        this.empfaenger = empfaenger;
    }

    public String getVerwendungszweck() {
        return verwendungszweck;
    }

    public void setVerwendungszweck(String verwendungszweck) {
        this.verwendungszweck = verwendungszweck;
    }

    public LocalDateTime getTransaktionsdatum() {
        return transaktionsdatum;
    }

    public void setTransaktionsdatum(LocalDateTime transaktionsdatum) {
        this.transaktionsdatum = transaktionsdatum;
    }

    public String getTransaktionsstatus() {
        return transaktionsstatus;
    }

    public void setTransaktionsstatus(String transaktionsstatus) {
        this.transaktionsstatus = transaktionsstatus;
    }

    public Bankkonto getBankkonto() {
        return bankkonto;
    }

    public void setBankkonto(Bankkonto bankkonto) {
        this.bankkonto = bankkonto;
    }

    @Override
    public String toString() {
        return "Transaktion{" +
                "transaktionsId=" + transaktionsId +
                ", betrag=" + betrag +
                ", transaktionsart='" + transaktionsart + '\'' +
                ", transaktionsdatum=" + transaktionsdatum +
                ", transaktionsstatus='" + transaktionsstatus + '\'' +
                '}';
    }
}
