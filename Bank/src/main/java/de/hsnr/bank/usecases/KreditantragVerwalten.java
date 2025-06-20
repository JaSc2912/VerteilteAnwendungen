/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import de.hsnr.bank.entities.Kreditantrag;
import de.hsnr.bank.entities.Kunde;
import de.hsnr.bank.usecases.Interfaces.IKreditantragVerwalten;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

/**
 *
 * @author jannn
 */
@Stateless
public class KreditantragVerwalten implements IKreditantragVerwalten {

    @EJB
    private KreditantragManager kreditantragManager;

    @Override
    public void addKreditantrag(Double kreditsumme, Kunde antragssteller, String status, String laufzeit, Double zins) {
        Kreditantrag kreditantrag = new Kreditantrag(null, kreditsumme, laufzeit, zins, status, antragssteller);
        kreditantragManager.addKreditantrag(kreditantrag);
    }

    @Override
    public void editKreditantrag(Long kreditantragsNummer, Double kreditsumme, Kunde antragssteller, String status,
            String laufzeit, Double zins) {
        Kreditantrag kreditantrag = new Kreditantrag(kreditantragsNummer, kreditsumme, laufzeit, zins, status,
                antragssteller);
        kreditantragManager.editKreditantrag(kreditantrag);
    }

    @Override
    public void acceptKreditantrag(Long kreditantragsNummer) {
        Kreditantrag kreditantrag = kreditantragManager.suchen(kreditantragsNummer);
        if (kreditantrag != null) {
            kreditantrag.setStatus("Akzeptiert");
            kreditantragManager.editKreditantrag(kreditantrag);
        }
    }

    @Override
    public void denyKreditantrag(Long kreditantragsNummer) {
        Kreditantrag kreditantrag = kreditantragManager.suchen(kreditantragsNummer);
        if (kreditantrag != null) {
            kreditantrag.setStatus("Abgelehnt");
            kreditantragManager.editKreditantrag(kreditantrag);
        }
    }
}
