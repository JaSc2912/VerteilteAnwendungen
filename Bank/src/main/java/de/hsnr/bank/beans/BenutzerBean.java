package de.hsnr.bank.beans;

import de.hsnr.bank.entities.Benutzer;
import de.hsnr.bank.usecases.BenutzerManager;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("benutzerBean")
@ViewScoped
public class BenutzerBean implements Serializable {

    @EJB
    private BenutzerManager benutzerManager;

    private List<Benutzer> alleBenutzer;
    private Benutzer selectedBenutzer;

    @PostConstruct
    public void init() {
        alleBenutzer = benutzerManager.alleLesen();
        selectedBenutzer = new Benutzer(); // For the 'new user' dialog
    }

    public void speichern() {
        if (benutzerManager.suchen(selectedBenutzer.getBenutzername()) != null) {
            benutzerManager.editBenutzer(selectedBenutzer);
        } else {
            benutzerManager.addBenutzer(selectedBenutzer);
        }
        // Refresh list and reset selection
        alleBenutzer = benutzerManager.alleLesen();
        selectedBenutzer = new Benutzer();
    }

    public void loeschen(String benutzername) {
        benutzerManager.deleteBenutzer(benutzername);
        alleBenutzer = benutzerManager.alleLesen(); // Refresh list
    }

    // Getters and Setters
    public List<Benutzer> getAlleBenutzer() {
        return alleBenutzer;
    }

    public void setAlleBenutzer(List<Benutzer> alleBenutzer) {
        this.alleBenutzer = alleBenutzer;
    }

    public Benutzer getSelectedBenutzer() {
        return selectedBenutzer;
    }

    public void setSelectedBenutzer(Benutzer selectedBenutzer) {
        this.selectedBenutzer = selectedBenutzer;
    }
}