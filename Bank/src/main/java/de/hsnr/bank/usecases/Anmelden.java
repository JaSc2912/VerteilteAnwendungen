/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import de.hsnr.bank.dataaccess.BenutzerDAO;
import de.hsnr.bank.entities.Benutzer;
import de.hsnr.bank.usecases.Interfaces.IAnmelden;

/**
 *
 * @author jannn
 */
public class Anmelden implements IAnmelden {

    private BenutzerDAO benutzerDAO = new BenutzerDAO();

    @Override
    public void login(String benutzername, String passwort) {
        if (benutzername == null || benutzername.isEmpty()) {
            throw new IllegalArgumentException("Benutzername darf nicht leer sein.");
        }
        if (passwort == null || passwort.isEmpty()) {
            throw new IllegalArgumentException("Passwort darf nicht leer sein.");
        }

        Benutzer benutzer = benutzerDAO.suchen(benutzername);

        if (benutzer == null) {
            throw new SecurityException("Benutzer existiert nicht.");
        }

        if (!passwort.equals(benutzer.getPasswort())) {
            throw new SecurityException("Ungültiger Benutzername oder Passwort.");
        }

        // Erfolgreich eingeloggt
        System.out.println("Login erfolgreich!");
    }

}
