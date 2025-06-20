/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import java.util.Date;

import de.hsnr.bank.dataaccess.BankkontoDAO;
import de.hsnr.bank.entities.Bankkonto;
import de.hsnr.bank.entities.Kunde;
import de.hsnr.bank.usecases.Interfaces.IBankkontoPflegen;
import jakarta.ejb.Stateless;

/**
 *
 * @author jannn
 */
@Stateless
public class BankkontoPflegen implements IBankkontoPflegen {

    private BankkontoDAO bankkontoDAO = new BankkontoDAO();

    @Override
    public boolean addBankkonto(String iban, String kontoArt, double kontostand, Date kontoEroeffnung,
            String kontoStatus, Kunde besitzer) {
        try {
            Bankkonto bankkonto = new Bankkonto(iban, kontoArt, kontostand, kontoEroeffnung, kontoStatus, besitzer);
            bankkontoDAO.addBankkonto(bankkonto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean editBankkonto(String iban, String kontoArt, double kontostand, Date kontoEroeffnung,
            String kontoStatus, Kunde besitzer) {
        try {
            Bankkonto bankkonto = new Bankkonto(iban, kontoArt, kontostand, kontoEroeffnung, kontoStatus, besitzer);
            bankkontoDAO.editBankkonto(bankkonto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteBankkonto(String iban) {
        try {
            bankkontoDAO.deleteBankkonto(iban);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Bankkonto search(String iban) {
        return bankkontoDAO.suchen(iban);
    }
}
