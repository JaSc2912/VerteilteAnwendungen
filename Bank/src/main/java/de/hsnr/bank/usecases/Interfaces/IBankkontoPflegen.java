/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package de.hsnr.bank.usecases.Interfaces;

import de.hsnr.bank.entities.Kunde;
import jakarta.ejb.Local;

/**
 *
 * @author jannn
 */
@Local
public interface IBankkontoPflegen {
        boolean addBankkonto(String iban, String kontoArt, double kontostand, java.util.Date kontoEroeffnung,
                        String kontoStatus, Kunde besitzer);

        boolean editBankkonto(String iban, String kontoArt, double kontostand, java.util.Date kontoEroeffnung,
                        String kontoStatus, Kunde besitzer);

        boolean deleteBankkonto(String iban);

        de.hsnr.bank.entities.Bankkonto search(String iban);
}
