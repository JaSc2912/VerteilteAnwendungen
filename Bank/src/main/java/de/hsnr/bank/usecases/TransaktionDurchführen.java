/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import de.hsnr.bank.dataaccess.TransaktionDAO;
import de.hsnr.bank.entities.Bankkonto;
import de.hsnr.bank.entities.Transaktion;
import de.hsnr.bank.usecases.Interfaces.ITransaktionDurchführen;
import jakarta.ejb.Stateless;

/**
 *
 * @author jannn
 */
@Stateless
public class TransaktionDurchführen implements ITransaktionDurchführen {

    private TransaktionDAO transaktionDAO = new TransaktionDAO();

    @Override
    public void doTransaktion(Bankkonto konto, String transaktionsnummer, java.util.Date transaktionsdatum,
            double betrag, String transaktionsart, String empfänger, String transaktionsstatus) {
        Transaktion transaktion = new Transaktion(
                konto,
                transaktionsnummer,
                transaktionsdatum,
                betrag,
                transaktionsart,
                empfänger,
                transaktionsstatus);
        transaktionDAO.addTransaktion(transaktion);
    }
}
