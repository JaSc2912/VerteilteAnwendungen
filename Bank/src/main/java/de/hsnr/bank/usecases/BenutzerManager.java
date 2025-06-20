/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;
import de.hsnr.bank.entities.Benutzer;
import de.hsnr.bank.dataaccess.BenutzerDAO;

/**
 *
 * @author jannn
 */
public class BenutzerManager {
    private BenutzerDAO benutzerDAO;
    public BenutzerManager(BenutzerDAO benutzerDAO) {
        this.benutzerDAO = benutzerDAO;
    }
    public void addBenutzer(Benutzer benutzer) {
        benutzerDAO.addBenutzer(benutzer);
    }
    public void deleteBenutzer(String benutzername) {
        benutzerDAO.deleteBenutzer(benutzername);
    }
    public Benutzer suchen(String benutzername) {
        return benutzerDAO.suchen(benutzername);
    }
    public void editBenutzer(Benutzer benutzer) {
        benutzerDAO.editBenutzer(benutzer);
    }
    
}
