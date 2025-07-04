/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import de.hsnr.bank.dataaccess.KreditantragDAO;
import de.hsnr.bank.entities.Kreditantrag;
import de.hsnr.bank.entities.Kunde;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class KreditantragManager {
    @EJB
    private KreditantragDAO kreditantragDAO;

    public KreditantragManager() {
    }

    public void addKreditantrag(Kreditantrag kreditantrag) {
        kreditantragDAO.addKreditantrag(kreditantrag);
    }

    public void deleteKreditantrag(Long kreditantragsNummer) {
        kreditantragDAO.deleteKreditantrag(kreditantragsNummer);
    }

    public void editKreditantrag(Kreditantrag kreditantrag) {
        kreditantragDAO.editKreditantrag(kreditantrag);
    }

    public Kreditantrag suchen(Long kreditantragsNummer) {
        return kreditantragDAO.suchen(kreditantragsNummer);
    }

    public List<Kreditantrag> alleLesen() {
        return kreditantragDAO.alleLesen();
    }

    public List<Kreditantrag> kreditantragSuchenNachKunde(Kunde kunde) {
        return kreditantragDAO.kreditantragSuchenNachKunde(kunde);
    }

    public List<Kreditantrag> kreditantragSuchenNachIban(String iban) {
        return kreditantragDAO.kreditantragSuchenNachIban(iban);
    }

    public void antragAnnehmen(Long kreditantragsNummer) {
        Kreditantrag antrag = kreditantragDAO.suchen(kreditantragsNummer);
        if (antrag != null) {
            antrag = new Kreditantrag(antrag.getKreditantragsNummer(), antrag.getKreditsumme(),
                    antrag.getLaufzeit(), antrag.getZins(), "AKZEPTIERT", antrag.getAntragssteller());
            kreditantragDAO.editKreditantrag(antrag);
        }
    }

    public void antragAblehnen(Long kreditantragsNummer) {
        Kreditantrag antrag = kreditantragDAO.suchen(kreditantragsNummer);
        if (antrag != null) {
            antrag = new Kreditantrag(antrag.getKreditantragsNummer(), antrag.getKreditsumme(),
                    antrag.getLaufzeit(), antrag.getZins(), "ABGELEHNT", antrag.getAntragssteller());
            kreditantragDAO.editKreditantrag(antrag);
        }
    }
}
