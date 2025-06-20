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
public interface IKundenübersichtAnzeigen {
    /**
     * Gibt die Daten des Kunden mit der übergebenen Kundennummer zurück.
     * 
     * @param kundennummer Die Kundennummer des gesuchten Kunden.
     * @return Das Kunde-Objekt oder null, falls nicht gefunden.
     */
    public Kunde showKunde(String kundennummer);
}
