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
public interface IKundePflegen {
    void addKunde(Kunde kunde);

    void deleteKunde(String kundennummer);

    void updateKunde(Kunde kunde);
}
