/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.dataaccess;

import de.hsnr.bank.entities.MitarbeiterKreditvergabe;
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
public class MitarbeiterKreditvergabeDAO {

    @PersistenceContext
    private EntityManager em;

    public MitarbeiterKreditvergabe suchen(String benutzername) {
        MitarbeiterKreditvergabeEntity entity = em.find(MitarbeiterKreditvergabeEntity.class, benutzername);
        return entity != null ? entity.toMitarbeiterKreditvergabe() : null;
    }

    public void addMitarbeiterKreditvergabe(MitarbeiterKreditvergabe mitarbeiter) {
        MitarbeiterKreditvergabeEntity entity = new MitarbeiterKreditvergabeEntity(mitarbeiter);
        em.persist(entity);
    }

    public void deleteMitarbeiterKreditvergabe(String benutzername) {
        MitarbeiterKreditvergabeEntity entity = em.find(MitarbeiterKreditvergabeEntity.class, benutzername);
        if (entity != null) {
            em.remove(entity);
        }
    }

    public void editMitarbeiterKreditvergabe(MitarbeiterKreditvergabe mitarbeiter) {
        MitarbeiterKreditvergabeEntity entity = em.find(MitarbeiterKreditvergabeEntity.class,
                mitarbeiter.getBenutzername());
        if (entity != null) {
            entity.setMitarbeiterID(mitarbeiter.getMitarbeiterID());
            entity.setAbteilung(mitarbeiter.getAbteilung());
            // Felder von BenutzerEntity aktualisieren
            entity.passwort = mitarbeiter.getPasswort();
            entity.name = mitarbeiter.getName();
            entity.telefonnummer = mitarbeiter.getTelefonnummer();
            entity.rolle = mitarbeiter.getRolle();
            em.merge(entity);
        }
    }

    public List<MitarbeiterKreditvergabe> alleLesen() {
        TypedQuery<MitarbeiterKreditvergabeEntity> query = em.createQuery(
                "SELECT m FROM MitarbeiterKreditvergabeEntity m",
                MitarbeiterKreditvergabeEntity.class);
        return query.getResultList().stream()
                .map(MitarbeiterKreditvergabeEntity::toMitarbeiterKreditvergabe)
                .collect(Collectors.toList());
    }
}
