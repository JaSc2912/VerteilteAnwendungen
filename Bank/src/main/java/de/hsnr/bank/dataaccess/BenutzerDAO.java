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

    public List<Benutzer> alleLesen() {

        TypedQuery<BenutzerEntity> query = em.createQuery("SELECT a FROM BenutzerEntity a", BenutzerEntity.class);

        return query.getResultList().stream()
                .map(autorEntity -> autorEntity.toBenutzer())
                .collect(Collectors.toList());

    }
}
