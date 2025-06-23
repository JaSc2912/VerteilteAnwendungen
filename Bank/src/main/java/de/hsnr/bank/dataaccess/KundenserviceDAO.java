/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.dataaccess;

import de.hsnr.bank.entities.Kundenservice;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author jannn
 */
public class KundenserviceDAO {

    @PersistenceContext
    private EntityManager em;

    public Kundenservice suchen(String benutzername) {
        KundenserviceEntity entity = em.find(KundenserviceEntity.class, benutzername);
        return entity != null ? entity.toKundenservice() : null;
    }

    public void addKundenservice(Kundenservice kundenservice) {
        KundenserviceEntity entity = new KundenserviceEntity(kundenservice);
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    public void deleteKundenservice(String benutzername) {
        KundenserviceEntity entity = em.find(KundenserviceEntity.class, benutzername);
        if (entity != null) {
            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
        }
    }

    public void editKundenservice(Kundenservice kundenservice) {
        KundenserviceEntity entity = em.find(KundenserviceEntity.class, kundenservice.getBenutzername());
        if (entity != null) {
            em.getTransaction().begin();
            entity.passwort = kundenservice.getPasswort();
            entity.name = kundenservice.getName();
            entity.telefonnummer = kundenservice.getTelefonnummer();
            entity.rolle = kundenservice.getRolle();
            em.merge(entity);
            em.getTransaction().commit();
        }
    }

    public List<Kundenservice> alleLesen() {
        TypedQuery<KundenserviceEntity> query = em.createQuery("SELECT k FROM KundenserviceEntity k",
                KundenserviceEntity.class);
        return query.getResultList().stream()
                .map(KundenserviceEntity::toKundenservice)
                .collect(Collectors.toList());
    }
}
