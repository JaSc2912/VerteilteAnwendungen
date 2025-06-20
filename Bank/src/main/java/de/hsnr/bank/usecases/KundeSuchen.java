/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import de.hsnr.bank.dataaccess.KundeDAO;
import de.hsnr.bank.entities.Kunde;
import de.hsnr.bank.usecases.Interfaces.IKundeSuchen;
import jakarta.ejb.Stateless;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author jannn
 */
@Stateless
public class KundeSuchen implements IKundeSuchen {

    private KundeDAO kundeDAO = new KundeDAO();

    @Override
    public List<Kunde> sucheKunde(String suchbegriff) {
        String suchbegriffLower = suchbegriff.toLowerCase();
        return kundeDAO.alleLesen().stream()
                .filter(k -> (k.getKundennummer() != null
                        && k.getKundennummer().toLowerCase().contains(suchbegriffLower)) ||
                        (k.getName() != null && k.getName().toLowerCase().contains(suchbegriffLower)) ||
                        (k.getAdresse() != null && k.getAdresse().toLowerCase().contains(suchbegriffLower)) ||
                        (k.getTelefonnummer() != null && k.getTelefonnummer().toLowerCase().contains(suchbegriffLower))
                        ||
                        (k.getEmail() != null && k.getEmail().toLowerCase().contains(suchbegriffLower)) ||
                        (k.getGeburtsdatum() != null && k.getGeburtsdatum().toLowerCase().contains(suchbegriffLower)) ||
                        (k.getKundenstatus() != null && k.getKundenstatus().toLowerCase().contains(suchbegriffLower)))
                .collect(Collectors.toList());
    }
}
