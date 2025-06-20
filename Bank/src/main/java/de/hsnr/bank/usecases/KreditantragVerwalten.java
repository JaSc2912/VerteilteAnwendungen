/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import de.hsnr.bank.dataaccess.KreditantragDAO;
import de.hsnr.bank.entities.Kreditantrag;
import de.hsnr.bank.entities.Kunde;
import de.hsnr.bank.usecases.Interfaces.IKreditantragVerwalten;

/**
 *
 * @author jannn
 */
public class KreditantragVerwalten implements IKreditantragVerwalten {

    private KreditantragDAO kreditantragDAO = new KreditantragDAO();

    @Override
    public void addKreditantrag(Double kreditsumme, Kunde antragssteller, String status, String laufzeit, Double zins) {
        Kreditantrag kreditantrag = new Kreditantrag(null, kreditsumme, laufzeit, zins, status, antragssteller);
        kreditantragDAO.addKreditantrag(kreditantrag);
    }

    @Override
    public void editKreditantrag(Long kreditantragsNummer, Double kreditsumme, Kunde antragssteller, String status,
            String laufzeit, Double zins) {
        Kreditantrag kreditantrag = new Kreditantrag(kreditantragsNummer, kreditsumme, laufzeit, zins, status,
                antragssteller);
        kreditantragDAO.editKreditantrag(kreditantrag);
    }

    @Override
    public void acceptKreditantrag(Long kreditantragsNummer) {
        Kreditantrag kreditantrag = kreditantragDAO.suchen(kreditantragsNummer);
        if (kreditantrag != null) {
            kreditantrag.setStatus("Akzeptiert");
            kreditantragDAO.editKreditantrag(kreditantrag);
        }
    }

    @Override
    public void denyKreditantrag(Long kreditantragsNummer) {
        Kreditantrag kreditantrag = kreditantragDAO.suchen(kreditantragsNummer);
        if (kreditantrag != null) {
            kreditantrag.setStatus("Abgelehnt");
            kreditantragDAO.editKreditantrag(kreditantrag);
        }
    }
}
