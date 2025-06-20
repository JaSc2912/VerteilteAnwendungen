/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package de.hsnr.bank.usecases.Interfaces;

import de.hsnr.bank.entities.Kreditantrag;
import jakarta.ejb.Local;
import java.util.List;

/**
 *
 * @author jannn
 */
public interface IKreditantragSuchen {

    public List<Kreditantrag> sucheKreditantraege(String suchbegriff);
}
