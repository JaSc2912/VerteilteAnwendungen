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
public interface IKreditantragVerwalten {
    public void addKreditantrag(Double kreditsumme, Kunde antragssteller, String status, String laufzeit, Double zins);

    public void editKreditantrag(Long kreditantragsNummer, Double kreditsumme, Kunde antragssteller, String status,
            String laufzeit, Double zins);

    public void acceptKreditantrag(Long kreditantragsNummer);

    public void denyKreditantrag(Long kreditantragsNummer);
}
