package de.hsnr.bank.beans;

import de.hsnr.bank.entities.Kreditantrag;
import de.hsnr.bank.usecases.KreditantragManager;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("kreditantragBean")
@ViewScoped
public class KreditantragBean implements Serializable {

    @EJB
    private KreditantragManager kreditantragManager;

    private List<Kreditantrag> alleAntraege;

    @PostConstruct
    public void init() {
        alleAntraege = kreditantragManager.alleLesen();
    }

    public void annehmen(Long antragId) {
        kreditantragManager.antragAnnehmen(antragId);
        alleAntraege = kreditantragManager.alleLesen(); // Refresh list
    }

    public void ablehnen(Long antragId) {
        kreditantragManager.antragAblehnen(antragId);
        alleAntraege = kreditantragManager.alleLesen(); // Refresh list
    }

    // Getters and Setters
    public List<Kreditantrag> getAlleAntraege() {
        return alleAntraege;
    }

    public void setAlleAntraege(List<Kreditantrag> alleAntraege) {
        this.alleAntraege = alleAntraege;
    }
}