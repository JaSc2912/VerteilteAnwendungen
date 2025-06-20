/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package de.hsnr.bank.usecases.Interfaces;

/**
 *
 * @author jannn
 */
public interface IBankkontoPflegen {
    boolean addBankkonto(String kontonummer, String iban, String bic, String bankname, String kontoinhaber, double saldo);
    boolean deleteBankkonto(String kontonummer);
    boolean editBankkonto(String kontonummer, String iban, String bic, String bankname, String kontoinhaber, double saldo);
}
