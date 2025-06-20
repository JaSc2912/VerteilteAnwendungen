/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import de.hsnr.bank.dataaccess.KundeDAO;
import de.hsnr.bank.entities.Kunde;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class KundenManager {
    @EJB
    private KundeDAO kundeDAO;

    public KundenManager() {
    }

    public void addKunde(Kunde kunde) {
        kundeDAO.addKunde(kunde);
    }

    public void deleteKunde(String kundennummer) {
        kundeDAO.deleteKunde(kundennummer);
    }

    public void editKunde(Kunde kunde) {
        kundeDAO.editKunde(kunde);
    }

    public Kunde suchen(String kundennummer) {
        return kundeDAO.suchen(kundennummer);
    }

    public List<Kunde> alleLesen() {
        return kundeDAO.alleLesen();
    }
}
