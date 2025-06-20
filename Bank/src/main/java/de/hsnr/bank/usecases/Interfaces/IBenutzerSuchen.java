/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package de.hsnr.bank.usecases.Interfaces;

import de.hsnr.bank.entities.Benutzer;
import jakarta.ejb.Local;
import java.util.List;

/**
 *
 * @author jannn
 */
public interface IBenutzerSuchen {
    public List<Benutzer> searchBenutzer(String suchParameter);
}
