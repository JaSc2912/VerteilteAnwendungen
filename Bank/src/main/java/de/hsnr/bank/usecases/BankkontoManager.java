/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import de.hsnr.bank.dataaccess.BankkontoDAO;
import de.hsnr.bank.entities.Bankkonto;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;

/**
 *
 * @author jannn
 */
@Stateless
public class BankkontoManager {

    @EJB
    private BankkontoDAO bankkontoDAO;

    public BankkontoManager() {
    }

    public void addBankkonto(Bankkonto bankkonto) {
        bankkontoDAO.addBankkonto(bankkonto);
    }

    public void deleteBankkonto(String iban) {
        bankkontoDAO.deleteBankkonto(iban);
    }

    public void editBankkonto(Bankkonto bankkonto) {
        bankkontoDAO.editBankkonto(bankkonto);
    }

    public Bankkonto suchen(String iban) {
        return bankkontoDAO.suchen(iban);
    }

    public List<Bankkonto> alleLesen() {
        return bankkontoDAO.alleLesen();
    }
}
