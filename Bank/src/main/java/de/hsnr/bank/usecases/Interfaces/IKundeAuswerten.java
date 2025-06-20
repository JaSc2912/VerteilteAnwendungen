/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package de.hsnr.bank.usecases.Interfaces;

import de.hsnr.bank.entities.Kunde;

/**
 *
 * @author jannn
 */
public interface IKundeAuswerten {

    public boolean checkKreditwürdigkeit(Kunde kunde);

    public Double checkSaldo(Kunde kunde);
}
