/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import de.hsnr.bank.dataaccess.KundeDAO;
import de.hsnr.bank.entities.Kunde;
import de.hsnr.bank.usecases.Interfaces.IKundePflegen;
import jakarta.ejb.Stateless;

/**
 *
 * @author jannn
 */
@Stateless
public class KundePflegen implements IKundePflegen {

    private KundeDAO kundeDAO = new KundeDAO();

    @Override
    public void addKunde(Kunde kunde) {
        kundeDAO.addKunde(kunde);
    }

    @Override
    public void deleteKunde(String kundennummer) {
        kundeDAO.deleteKunde(kundennummer);
    }

    @Override
    public void updateKunde(Kunde kunde) {
        kundeDAO.editKunde(kunde);
    }

}
