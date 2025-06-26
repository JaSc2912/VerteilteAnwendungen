package de.hsnr.bank.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Customer entity for banking application
 */
@Entity
@Table(name = "KUNDE")
@NamedQueries({
        @NamedQuery(name = "Kunde.findAll", query = "SELECT k FROM Kunde k"),
        @NamedQuery(name = "Kunde.findByKundennummer", query = "SELECT k FROM Kunde k WHERE k.kundennummer = :kundennummer"),
        @NamedQuery(name = "Kunde.searchByName", query = "SELECT k FROM Kunde k WHERE UPPER(k.name) LIKE UPPER(:searchTerm)"),
        @NamedQuery(name = "Kunde.searchByEmail", query = "SELECT k FROM Kunde k WHERE UPPER(k.email) LIKE UPPER(:searchTerm)")
})
public class Kunde implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KUNDENNUMMER")
    private Long kundennummer;

    @NotBlank
    @Column(name = "NAME", length = 100, nullable = false)
    private String name;

    @Column(name = "ADRESSE", length = 200)
    private String adresse;

    @Column(name = "TELEFONNUMMER", length = 20)
    private String telefonnummer;

    @Email
    @Column(name = "EMAIL", length = 100)
    private String email;

    @Column(name = "GEBURTSDATUM")
    private LocalDate geburtsdatum;

    @Column(name = "KUNDENSTATUS", length = 20)
    private String kundenstatus;

    @OneToMany(mappedBy = "kunde", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bankkonto> bankkonten;

    @OneToMany(mappedBy = "antragssteller", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Kreditantrag> kreditantraege;

    // Constructors
    public Kunde() {
    }

    public Kunde(String name, String adresse, String telefonnummer, String email, LocalDate geburtsdatum) {
        this.name = name;
        this.adresse = adresse;
        this.telefonnummer = telefonnummer;
        this.email = email;
        this.geburtsdatum = geburtsdatum;
        this.kundenstatus = "AKTIV";
    }

    // Getters and Setters
    public Long getKundennummer() {
        return kundennummer;
    }

    public void setKundennummer(Long kundennummer) {
        this.kundennummer = kundennummer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getKundenstatus() {
        return kundenstatus;
    }

    public void setKundenstatus(String kundenstatus) {
        this.kundenstatus = kundenstatus;
    }

    public List<Bankkonto> getBankkonten() {
        return bankkonten;
    }

    public void setBankkonten(List<Bankkonto> bankkonten) {
        this.bankkonten = bankkonten;
    }

    public List<Kreditantrag> getKreditantraege() {
        return kreditantraege;
    }

    public void setKreditantraege(List<Kreditantrag> kreditantraege) {
        this.kreditantraege = kreditantraege;
    }

    @Override
    public String toString() {
        return "Kunde{" +
                "kundennummer=" + kundennummer +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", kundenstatus='" + kundenstatus + '\'' +
                '}';
    }
}
