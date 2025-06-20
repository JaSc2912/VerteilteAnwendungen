/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import de.hsnr.bank.usecases.Interfaces.IBankkontoPflegen;

/**
 *
 * @author jannn
 */
public class BankkontoPflegen implements IBankkontoPflegen {

  
    @Override
    public boolean addBankkonto(String kontonummer, String iban, String bic, String bankname, String kontoinhaber, double saldo) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }


    @Override
    public boolean deleteBankkonto(String kontonummer) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    

    @Override
    public boolean editBankkonto(String kontonummer, String iban, String bic, String bankname, String kontoinhaber, double saldo) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
