/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package de.hsnr.bank.usecases.Interfaces;

import de.hsnr.bank.entities.Kunde;
import java.util.List;
import jakarta.ejb.Local;

/**
 *
 * @author jannn
 */
public interface IKundeSuchen {
    List<Kunde> sucheKunde(String suchbegriff);
}
