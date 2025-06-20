/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.usecases;

import de.hsnr.bank.dataaccess.KreditantragDAO;
import de.hsnr.bank.entities.Kunde;
import de.hsnr.bank.usecases.Interfaces.IKundeAuswerten;

/**
 *
 * @author jannn
 */
public class KundeAuswerten implements IKundeAuswerten {

    private KreditantragDAO kreditantragDAO = new KreditantragDAO();

    @Override
    public boolean checkKreditwürdigkeit(Kunde kunde) {
        KreditantragDAO.KundeKontoAuswertung auswertung = kreditantragDAO.auswertungFuerKunde(kunde);
        // Beispiel: Kreditwürdigkeit, wenn weniger als 2 negative Monate und
        // SummeEingehend > SummeAusgehend
        return auswertung.negativeMonate < 2 && auswertung.summeEingehend > auswertung.summeAusgehend;
    }

    @Override
    public Double checkSaldo(Kunde kunde) {
        KreditantragDAO.KundeKontoAuswertung auswertung = kreditantragDAO.auswertungFuerKunde(kunde);
        // Gibt die aktuelle Summe der Eingänge zurück
        return auswertung.summeEingehend - auswertung.summeAusgehend;
    }
}
