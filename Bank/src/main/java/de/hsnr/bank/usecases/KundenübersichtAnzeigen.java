/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import de.hsnr.bank.dataaccess.KundeDAO;
import de.hsnr.bank.entities.Kunde;
import de.hsnr.bank.usecases.Interfaces.IKundenübersichtAnzeigen;
import jakarta.ejb.Stateless;

/**
 *
 * @author jannn
 */
@Stateless
public class KundenübersichtAnzeigen implements IKundenübersichtAnzeigen {

    private KundeDAO kundeDAO = new KundeDAO();

    @Override
    public Kunde showKunde(String kundennummer) {
        return kundeDAO.suchen(kundennummer);
    }

}
