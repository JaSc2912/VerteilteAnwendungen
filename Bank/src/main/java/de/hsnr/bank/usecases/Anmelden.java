/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import de.hsnr.bank.dataaccess.BenutzerDAO;
import de.hsnr.bank.entities.Benutzer;
import de.hsnr.bank.usecases.Interfaces.IAnmelden;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

/**
 *
 * @author jannn
 */
@Stateless
public class Anmelden implements IAnmelden {

    @EJB
    private BenutzerDAO benutzerDAO;

    @Override
    public Benutzer login(String benutzername, String passwort) {
        Benutzer benutzer = benutzerDAO.suchen(benutzername);

        if (benutzer == null) {
            throw new SecurityException("Benutzer existiert nicht.");
        }

        if (!passwort.equals(benutzer.getPasswort())) {
            throw new SecurityException("Ungültiger Benutzername oder Passwort.");
        }

        // Erfolgreich eingeloggt
        System.out.println("Login erfolgreich für: " + benutzer.getBenutzername());
        return benutzer;
    }

}
