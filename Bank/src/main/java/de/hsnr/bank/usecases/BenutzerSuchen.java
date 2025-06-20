package de.hsnr.bank.usecases;

import de.hsnr.bank.entities.Benutzer;
import de.hsnr.bank.usecases.Interfaces.IBenutzerSuchen;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class BenutzerSuchen implements IBenutzerSuchen {

    @EJB
    private BenutzerManager benutzerManager;

    @Override
    public List<Benutzer> searchBenutzer(String suchParameter) {
        // Optional: Füge eine Suchmethode in BenutzerManager hinzu, falls benötigt
        // return benutzerManager.searchBenutzer(suchParameter);
        // Oder, falls nicht vorhanden:
        return benutzerManager.alleLesen().stream()
                .filter(b -> b.getBenutzername().toLowerCase().contains(suchParameter.toLowerCase())
                        || (b.getName() != null && b.getName().toLowerCase().contains(suchParameter.toLowerCase()))
                        || (b.getTelefonnummer() != null && b.getTelefonnummer().contains(suchParameter)))
                .toList();
    }
}
