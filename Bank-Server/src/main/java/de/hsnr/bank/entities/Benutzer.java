package de.hsnr.bank.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * User entity for banking application
 */
@Entity
@Table(name = "BENUTZER")
@NamedQueries({
        @NamedQuery(name = "Benutzer.findAll", query = "SELECT b FROM Benutzer b"),
        @NamedQuery(name = "Benutzer.findByUsername", query = "SELECT b FROM Benutzer b WHERE b.benutzername = :benutzername"),
        @NamedQuery(name = "Benutzer.findByRole", query = "SELECT b FROM Benutzer b WHERE b.rolle = :rolle")
})
public class Benutzer implements Serializable {

    @Id
    @NotBlank
    @Column(name = "BENUTZERNAME", length = 50)
    private String benutzername;

    @NotBlank
    @Column(name = "PASSWORT", length = 255)
    // @JsonbTransient // Exclude password from JSON serialization
    private String passwort;

    @NotBlank
    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "TELEFONNUMMER", length = 20)
    private String telefonnummer;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLLE", length = 20)
    private RolleT rolle;

    // Constructors
    public Benutzer() {
    }

    public Benutzer(String benutzername, String passwort) {
        this.benutzername = benutzername;
        this.passwort = passwort;
    }

    public Benutzer(String benutzername, String name, String passwort, String telefonnummer, RolleT rolle) {
        this.benutzername = benutzername;
        this.name = name;
        this.passwort = passwort;
        this.telefonnummer = telefonnummer;
        this.rolle = rolle;
    }

    // Getters and Setters
    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public RolleT getRolle() {
        return rolle;
    }

    public void setRolle(RolleT rolle) {
        this.rolle = rolle;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    @Override
    public String toString() {
        return "Benutzer{" +
                "benutzername='" + benutzername + '\'' +
                ", name='" + name + '\'' +
                ", rolle=" + rolle +
                '}';
    }
}
