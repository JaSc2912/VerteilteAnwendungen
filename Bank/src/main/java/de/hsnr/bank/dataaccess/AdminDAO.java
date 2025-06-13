/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.dataaccess;

import de.hsnr.bank.entities.Benutzer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author muell
 */
public class AdminDAO extends BenutzerDAO {

    @PersistenceContext
    private EntityManager em;

    public Benutzer anlegen(Benutzer benutzer) {

        BenutzerEntity benutzerEntity = new BenutzerEntity(benutzer);
        em.persist(benutzerEntity);

        return benutzerEntity.toBenutzer();

    }

    public boolean loeschen(Benutzer benutzer) {
        BenutzerEntity benutzerEntity = em.find(BenutzerEntity.class, benutzer.getBenutzername());
        if (benutzerEntity == null) {
            return false;
        }

        em.remove(benutzerEntity);
        return true;
    }
}
