/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.dataaccess;

import de.hsnr.bank.entities.Benutzer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author muell
 */
public class BenutzerDAO {

    @PersistenceContext
    private EntityManager em;

    public Benutzer suchen(String Benutzername) {

        BenutzerEntity benutzerEntity = em.find(BenutzerEntity.class, Benutzername);

        if (benutzerEntity == null) {
            return null;
        } else {
            return benutzerEntity.toBenutzer();
        }
    }

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

    public List<Benutzer> alleLesen() {

        TypedQuery<BenutzerEntity> query = em.createQuery("SELECT a FROM BenutzerEntity a", BenutzerEntity.class);

        return query.getResultList().stream()
                .map(autorEntity -> autorEntity.toBenutzer())
                .collect(Collectors.toList());

    }
}
