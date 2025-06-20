/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import de.hsnr.bank.entities.Kunde;
import de.hsnr.bank.usecases.Interfaces.IKundePflegen;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

/**
 *
 * @author jannn
 */
@Stateless
public class KundePflegen implements IKundePflegen {

    @EJB
    private KundenManager kundenManager;

    @Override
    public void addKunde(Kunde kunde) {
        kundenManager.addKunde(kunde);
    }

    @Override
    public void deleteKunde(String kundennummer) {
        kundenManager.deleteKunde(kundennummer);
    }

    @Override
    public void updateKunde(Kunde kunde) {
        kundenManager.editKunde(kunde);
    }
}
