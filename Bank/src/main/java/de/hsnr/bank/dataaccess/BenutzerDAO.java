/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.dataaccess;

import de.hsnr.bank.entities.Benutzer;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author muell
 */
@Stateless
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

    public void addBenutzer(Benutzer benutzer) {

        BenutzerEntity benutzerEntity = new BenutzerEntity(benutzer);

        em.persist(benutzerEntity);

    }

    public void deleteBenutzer(String benutzername) {

        BenutzerEntity benutzerEntity = em.find(BenutzerEntity.class, benutzername);

        if (benutzerEntity != null) {
            em.remove(benutzerEntity);
        }

    }

    public void editBenutzer(Benutzer benutzer) {

        BenutzerEntity benutzerEntity = em.find(BenutzerEntity.class, benutzer.getBenutzername());

        if (benutzerEntity != null) {
            em.getTransaction().begin();
            benutzerEntity.passwort = benutzer.getPasswort();
            benutzerEntity.name = benutzer.getName();
            benutzerEntity.telefonnummer = benutzer.getTelefonnummer();
            benutzerEntity.rolle = benutzer.getRolle();
            em.merge(benutzerEntity);
            em.getTransaction().commit();
        }

    }

    public List<Benutzer> alleLesen() {

        TypedQuery<BenutzerEntity> query = em.createQuery("SELECT a FROM BenutzerEntity a", BenutzerEntity.class);

        return query.getResultList().stream()
                .map(autorEntity -> autorEntity.toBenutzer())
                .collect(Collectors.toList());

    }

    public List<Benutzer> searchBenutzer(String suchParameter) {
        return alleLesen().stream()
                .filter(b -> b.getBenutzername().toLowerCase().contains(suchParameter.toLowerCase())
                        || (b.getName() != null && b.getName().toLowerCase().contains(suchParameter.toLowerCase()))
                        || (b.getTelefonnummer() != null && b.getTelefonnummer().contains(suchParameter)))
                .collect(Collectors.toList());
    }

}
