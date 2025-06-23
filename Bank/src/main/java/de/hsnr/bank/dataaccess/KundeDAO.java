/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.dataaccess;

import de.hsnr.bank.entities.Kunde;
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
public class KundeDAO {

    @PersistenceContext
    private EntityManager em;

    public Kunde suchen(String kundennummer) {
        KundeEntity kundeEntity = em.find(KundeEntity.class, kundennummer);
        return kundeEntity != null ? kundeEntity.toKunde() : null;
    }

    public void addKunde(Kunde kunde) {
        KundeEntity kundeEntity = new KundeEntity(kunde);
        em.persist(kundeEntity);
    }

    public void deleteKunde(String kundennummer) {
        KundeEntity kundeEntity = em.find(KundeEntity.class, kundennummer);
        if (kundeEntity != null) {
            em.remove(kundeEntity);
        }
    }

    public void editKunde(Kunde kunde) {
        KundeEntity kundeEntity = em.find(KundeEntity.class, kunde.getKundennummer());
        if (kundeEntity != null) {
            kundeEntity.name = kunde.getName();
            kundeEntity.adresse = kunde.getAdresse();
            kundeEntity.kundenstatus = kunde.getKundenstatus();
            kundeEntity.geburtsdatum = kunde.getGeburtsdatum();
            kundeEntity.telefonnummer = kunde.getTelefonnummer();
            kundeEntity.email = kunde.getEmail();
            em.merge(kundeEntity);
        }
    }

    public List<Kunde> alleLesen() {
        TypedQuery<KundeEntity> query = em.createQuery("SELECT k FROM KundeEntity k", KundeEntity.class);
        return query.getResultList().stream()
                .map(KundeEntity::toKunde)
                .collect(Collectors.toList());
    }

    public List<Kunde> searchKunde(String suchbegriff) {
        TypedQuery<KundeEntity> query = em.createQuery(
                "SELECT k FROM KundeEntity k WHERE k.kundennummer LIKE :suchbegriff OR LOWER(k.name) LIKE LOWER(:suchbegriff)",
                KundeEntity.class);
        query.setParameter("suchbegriff", "%" + suchbegriff + "%");
        return query.getResultList().stream()
                .map(KundeEntity::toKunde)
                .collect(Collectors.toList());
    }
}
