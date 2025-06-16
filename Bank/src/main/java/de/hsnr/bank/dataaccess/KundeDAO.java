/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.dataaccess;

import de.hsnr.bank.entities.Kunde;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author muell
 */
public class KundeDAO {

    @PersistenceContext
    private EntityManager em;

    public Kunde suchen(String kundennummer) {
        KundeEntity kundeEntity = em.find(KundeEntity.class, kundennummer);
        return kundeEntity != null ? kundeEntity.toKunde() : null;
    }

    public void addKunde(Kunde kunde) {
        KundeEntity kundeEntity = new KundeEntity(kunde);
        em.getTransaction().begin();
        em.persist(kundeEntity);
        em.getTransaction().commit();
    }

    public void deleteKunde(String kundennummer) {
        KundeEntity kundeEntity = em.find(KundeEntity.class, kundennummer);
        if (kundeEntity != null) {
            em.getTransaction().begin();
            em.remove(kundeEntity);
            em.getTransaction().commit();
        }
    }

    public void editKunde(Kunde kunde) {
        KundeEntity kundeEntity = em.find(KundeEntity.class, kunde.getKundennummer());
        if (kundeEntity != null) {
            em.getTransaction().begin();
            kundeEntity.name = kunde.getName();
            kundeEntity.adresse = kunde.getAdresse();
            kundeEntity.kundenstatus = kunde.getKundenstatus();
            kundeEntity.geburtsdatum = kunde.getGeburtsdatum();
            kundeEntity.telefonnummer = kunde.getTelefonnummer();
            kundeEntity.email = kunde.getEmail();
            em.merge(kundeEntity);
            em.getTransaction().commit();
        }
    }

    public List<Kunde> alleLesen() {
        TypedQuery<KundeEntity> query = em.createQuery("SELECT k FROM KundeEntity k", KundeEntity.class);
        return query.getResultList().stream()
                .map(KundeEntity::toKunde)
                .collect(Collectors.toList());
    }
}
