/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import de.hsnr.bank.dataaccess.KreditantragDAO;
import de.hsnr.bank.entities.Kreditantrag;
import jakarta.ejb.Stateless;

import java.util.List;

/**
 *
 * @author jannn
 */
@Stateless
public class KreditantragManager {
    private KreditantragDAO kreditantragDAO;

    public KreditantragManager(KreditantragDAO kreditantragDAO) {
        this.kreditantragDAO = kreditantragDAO;
    }

    public void addKreditantrag(Kreditantrag kreditantrag) {
        kreditantragDAO.addKreditantrag(kreditantrag);
    }

    public void deleteKreditantrag(Long kreditantragsNummer) {
        kreditantragDAO.deleteKreditantrag(kreditantragsNummer);
    }

    public Kreditantrag suchen(Long kreditantragsNummer) {
        return kreditantragDAO.suchen(kreditantragsNummer);
    }

    public void editKreditantrag(Kreditantrag kreditantrag) {
        kreditantragDAO.editKreditantrag(kreditantrag);
    }

    public List<Kreditantrag> alleLesen() {
        return kreditantragDAO.alleLesen();
    }
}
