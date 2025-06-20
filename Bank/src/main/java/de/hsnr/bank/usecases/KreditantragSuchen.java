/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import de.hsnr.bank.dataaccess.KreditantragDAO;
import de.hsnr.bank.entities.Kreditantrag;
import de.hsnr.bank.usecases.Interfaces.IKreditantragSuchen;
import jakarta.ejb.Stateless;

import java.util.List;

/**
 *
 * @author jannn
 */
@Stateless
public class KreditantragSuchen implements IKreditantragSuchen {

    private KreditantragDAO kreditantragDAO = new KreditantragDAO();

    @Override
    public List<Kreditantrag> sucheKreditantraege(String suchbegriff) {
        return kreditantragDAO.kreditantragSuchen(suchbegriff);
    }
}
