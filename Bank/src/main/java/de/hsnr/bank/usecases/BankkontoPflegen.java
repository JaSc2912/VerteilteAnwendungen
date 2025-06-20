/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import de.hsnr.bank.entities.Bankkonto;
import de.hsnr.bank.entities.Kunde;
import de.hsnr.bank.usecases.Interfaces.IBankkontoPflegen;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.Date;

/**
 *
 * @author jannn
 */
@Stateless
public class BankkontoPflegen implements IBankkontoPflegen {

    @EJB
    private BankkontoManager bankkontoManager;

    @Override
    public boolean addBankkonto(String iban, String kontoArt, double kontostand, Date kontoEroeffnung,
            String kontoStatus, Kunde besitzer) {
        try {
            Bankkonto bankkonto = new Bankkonto(iban, kontoArt, kontostand, kontoEroeffnung, kontoStatus, besitzer);
            bankkontoManager.addBankkonto(bankkonto);
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
            bankkontoManager.editBankkonto(bankkonto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteBankkonto(String iban) {
        try {
            bankkontoManager.deleteBankkonto(iban);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Bankkonto search(String iban) {
        return bankkontoManager.suchen(iban);
    }
}
