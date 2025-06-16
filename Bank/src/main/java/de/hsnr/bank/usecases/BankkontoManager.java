/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

/**
 *
 * @author jannn
 */
public class BankkontoManager {
    private BankkontoDAO bankkontoDAO;
    public BankkontoManager(BankkontoDAO bankkontoDAO) {
        this.bankkontoDAO = bankkontoDAO;
    }
    public void addBankkonto(Bankkonto bankkonto) {
        bankkontoDAO.addBankkonto(bankkonto);
    }
    public void deleteBankkonto(String iban) {
        bankkontoDAO.deleteBankkonto(iban);
    }
}
