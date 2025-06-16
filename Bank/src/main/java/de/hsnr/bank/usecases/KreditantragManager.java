/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import de.hsnr.bank.dataaccess.KreditantragDAO;
import de.hsnr.bank.entities.Kreditantrag;
import java.util.List;

/**
 *
 * @author jannn
 */
public class KreditantragManager {
    private KreditantragDAO kreditantragDAO;

    public KreditantragManager(KreditantragDAO kreditantragDAO) {
        this.kreditantragDAO = kreditantragDAO;
    }

    public void addKreditantrag(Kreditantrag kreditantrag) {
        kreditantragDAO.addKreditantrag(kreditantrag);
    }

    public void deleteKreditantrag(String konto) {
        kreditantragDAO.deleteKreditantrag(konto);
    }

    public Kreditantrag suchen(String konto) {
        return kreditantragDAO.suchen(konto);
    }

    public void editKreditantrag(Kreditantrag kreditantrag) {
        kreditantragDAO.editKreditantrag(kreditantrag);
    }

    public List<Kreditantrag> alleLesen() {
        return kreditantragDAO.alleLesen();
    }
}
