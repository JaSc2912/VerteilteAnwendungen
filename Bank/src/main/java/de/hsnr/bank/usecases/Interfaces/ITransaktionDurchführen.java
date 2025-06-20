/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package de.hsnr.bank.usecases.Interfaces;

import de.hsnr.bank.entities.Bankkonto;

/**
 *
 * @author jannn
 */
public interface ITransaktionDurchführen {

    public void doTransaktion(Bankkonto konto, String transaktionsnummer, java.util.Date transaktionsdatum,
            double betrag, String transaktionsart, String empfänger, String transaktionsstatus);
}
