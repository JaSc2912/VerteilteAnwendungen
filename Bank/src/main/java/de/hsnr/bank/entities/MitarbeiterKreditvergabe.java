/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.entities;

/**
 *
 * @author muell
 */
public class MitarbeiterKreditvergabe extends Benutzer {
    public MitarbeiterKreditvergabe(String benutzername, String passwort, String mitarbeiterID, String abteilung) {
        super(benutzername, passwort);
    }
   
    @Override
    public String toString() {
        return "MitarbeiterKreditvergabe{" + "benutzername=" + getBenutzername() + '}';
    }
}
