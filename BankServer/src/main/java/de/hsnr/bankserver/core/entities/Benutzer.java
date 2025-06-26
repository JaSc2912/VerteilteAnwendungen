package de.hsnr.bankserver.core.entities;

/**
 * Business Object für Benutzer
 */
public class Benutzer {
    
    private String benutzername;
    private String passwort;
    private String name;
    private String telefonnummer;
    private RolleT rolle;

    public Benutzer() {
    }

    public Benutzer(String benutzername, String passwort, String name, String telefonnummer, RolleT rolle) {
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
