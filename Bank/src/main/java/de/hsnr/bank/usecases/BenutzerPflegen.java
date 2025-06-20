/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import de.hsnr.bank.entities.Benutzer;
import de.hsnr.bank.usecases.Interfaces.IBenutzerPflegen;
import de.hsnr.bank.usecases.RolleT;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

/**
 *
 * @author jannn
 */
@Stateless
public class BenutzerPflegen implements IBenutzerPflegen {

    @EJB
    private BenutzerManager benutzerManager;

    @Override
    public boolean addBenutzer(String benutzername, String name, String passwort, String telefonnummer, RolleT rolle) {
        try {
            Benutzer benutzer = new Benutzer(benutzername, name, passwort, telefonnummer, rolle);
            benutzerManager.addBenutzer(benutzer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteBenutzer(String benutzername) {
        try {
            benutzerManager.deleteBenutzer(benutzername);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean editBenutzer(String benutzername, String name, String passwort, String telefonnummer, RolleT rolle) {
        try {
            Benutzer benutzer = benutzerManager.suchen(benutzername);
            if (benutzer == null) {
                return false;
            }
            benutzer.setName(name);
            benutzer.setPasswort(passwort);
            benutzer.setTelefonnummer(telefonnummer);
            benutzer.setRolle(rolle);
            benutzerManager.editBenutzer(benutzer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
