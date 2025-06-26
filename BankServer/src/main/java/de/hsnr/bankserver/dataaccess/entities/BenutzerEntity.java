package de.hsnr.bankserver.dataaccess.entities;

import de.hsnr.bankserver.core.entities.RolleT;
import jakarta.persistence.*;

/**
 * JPA Entity für Benutzer
 */
@Entity
@Table(name = "BENUTZER")
public class BenutzerEntity {
    
    @Id
    @Column(name = "BENUTZERNAME")
    private String benutzername;
    
    @Column(name = "PASSWORT")
    private String passwort;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "TELEFONNUMMER")
    private String telefonnummer;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLLE")
    private RolleT rolle;

    public BenutzerEntity() {
    }

    public BenutzerEntity(String benutzername, String passwort, String name, String telefonnummer, RolleT rolle) {
        this.benutzername = benutzername;
        this.passwort = passwort;
        this.name = name;
        this.telefonnummer = telefonnummer;
        this.rolle = rolle;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
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
}
