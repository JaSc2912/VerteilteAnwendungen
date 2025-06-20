/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import de.hsnr.bank.dataaccess.BenutzerDAO;
import de.hsnr.bank.entities.Benutzer;
import de.hsnr.bank.usecases.Interfaces.IBenutzerPflegen;
import jakarta.ejb.Stateless;
import de.hsnr.bank.usecases.RolleT;

/**
 *
 * @author jannn
 */
@Stateless
public class BenutzerPflegen implements IBenutzerPflegen {

    private BenutzerDAO benutzerDAO = new BenutzerDAO();

    @Override
    public boolean addBenutzer(String benutzername, String name, String passwort, String telefonnummer, RolleT rolle) {
        try {
            Benutzer benutzer = new Benutzer(benutzername, name, passwort, telefonnummer, rolle);
            benutzerDAO.addBenutzer(benutzer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteBenutzer(String benutzername) {
        try {
            benutzerDAO.deleteBenutzer(benutzername);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean editBenutzer(String benutzername, String name, String passwort, String telefonnummer, RolleT rolle) {
        try {
            Benutzer benutzer = benutzerDAO.suchen(benutzername);
            if (benutzer == null) {
                return false;
            }
            benutzer.setName(name);
            benutzer.setPasswort(passwort);
            benutzer.setTelefonnummer(telefonnummer);
            benutzer.setRolle(rolle);
            benutzerDAO.editBenutzer(benutzer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
