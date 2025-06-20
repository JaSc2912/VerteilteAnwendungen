/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import de.hsnr.bank.entities.Benutzer;
import de.hsnr.bank.dataaccess.BenutzerDAO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;

/**
 *
 * @author jannn
 */
@Stateless
public class BenutzerManager {
    @EJB
    private BenutzerDAO benutzerDAO;

    public BenutzerManager() {
    }

    public void addBenutzer(Benutzer benutzer) {
        benutzerDAO.addBenutzer(benutzer);
    }

    public void deleteBenutzer(String benutzername) {
        benutzerDAO.deleteBenutzer(benutzername);
    }

    public void editBenutzer(Benutzer benutzer) {
        benutzerDAO.editBenutzer(benutzer);
    }

    public Benutzer suchen(String benutzername) {
        return benutzerDAO.suchen(benutzername);
    }

    public List<Benutzer> alleLesen() {
        return benutzerDAO.alleLesen();
    }

    public List<Benutzer> searchBenutzer(String suchParameter) {
        return benutzerDAO.searchBenutzer(suchParameter);
    }
}
