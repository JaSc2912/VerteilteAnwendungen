/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import de.hsnr.bank.entities.Kreditantrag;
import de.hsnr.bank.usecases.Interfaces.IKreditantragSuchen;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author jannn
 */
@Stateless
public class KreditantragSuchen implements IKreditantragSuchen {

    @EJB
    private KreditantragManager kreditantragManager;

    @Override
    public List<Kreditantrag> sucheKreditantraege(String suchbegriff) {
        String suchbegriffLower = suchbegriff.toLowerCase();
        return kreditantragManager.alleLesen().stream()
                .filter(k -> (k.getKreditantragsNummer() != null
                        && k.getKreditantragsNummer().toString().contains(suchbegriff)) ||
                        (k.getKreditsumme() != null && k.getKreditsumme().toString().contains(suchbegriff)) ||
                        (k.getLaufzeit() != null && k.getLaufzeit().toLowerCase().contains(suchbegriffLower)) ||
                        (k.getZins() != null && k.getZins().toString().contains(suchbegriff)) ||
                        (k.getStatus() != null && k.getStatus().toLowerCase().contains(suchbegriffLower)) ||
                        (k.getAntragssteller() != null && ((k.getAntragssteller().getName() != null
                                && k.getAntragssteller().getName().toLowerCase().contains(suchbegriffLower)) ||
                                (k.getAntragssteller().getKundennummer() != null
                                        && k.getAntragssteller().getKundennummer().contains(suchbegriff)))))
                .collect(Collectors.toList());
    }
}
