/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.dataaccess;

import de.hsnr.bank.entities.Transaktion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author muell
 */
public class TransaktionDAO {

    @PersistenceContext
    private EntityManager em;

    public void addTransaktion(Transaktion transaktion) {
        TransaktionEntity entity = new TransaktionEntity(transaktion);
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    public List<Transaktion> alleLesen() {
        TypedQuery<TransaktionEntity> query = em.createQuery("SELECT t FROM TransaktionEntity t",
                TransaktionEntity.class);
        return query.getResultList().stream()
                .map(TransaktionEntity::toTransaktion)
                .collect(Collectors.toList());
    }

    // Optional: Suche nach Transaktionen für ein Konto
    public List<Transaktion> findByKonto(String iban) {
        TypedQuery<TransaktionEntity> query = em.createQuery(
                "SELECT t FROM TransaktionEntity t WHERE t.Bankkonto.iban = :iban", TransaktionEntity.class);
        query.setParameter("iban", iban);
        return query.getResultList().stream()
                .map(TransaktionEntity::toTransaktion)
                .collect(Collectors.toList());
    }
}
