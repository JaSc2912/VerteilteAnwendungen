/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.dataaccess;

import de.hsnr.bank.entities.Bankkonto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author muell
 */
public class BankkontoDAO {

    @PersistenceContext
    private EntityManager em;

    public Bankkonto suchen(String iban) {
        BankkontoEntity bankkontoEntity = em.find(BankkontoEntity.class, iban);
        if (bankkontoEntity == null) {
            return null;
        } else {
            return bankkontoEntity.toBankkonto();
        }
    }

    public void addBankkonto(Bankkonto bankkonto) {
        BankkontoEntity bankkontoEntity = new BankkontoEntity(bankkonto);
        em.getTransaction().begin();
        em.persist(bankkontoEntity);
        em.getTransaction().commit();
    }

    public void deleteBankkonto(String iban) {
        BankkontoEntity bankkontoEntity = em.find(BankkontoEntity.class, iban);
        if (bankkontoEntity != null) {
            em.getTransaction().begin();
            em.remove(bankkontoEntity);
            em.getTransaction().commit();
        }
    }

    public void editBankkonto(Bankkonto bankkonto) {
        BankkontoEntity bankkontoEntity = em.find(BankkontoEntity.class, bankkonto.getIban());
        if (bankkontoEntity != null) {
            em.getTransaction().begin();
            bankkontoEntity.kontoart = bankkonto.getKontoart();
            bankkontoEntity.kontostand = bankkonto.getKontostand();
            bankkontoEntity.kontoeroeffnungsdatum = bankkonto.getKontoeroeffnungsdatum();
            bankkontoEntity.kontostatus = bankkonto.getKontostatus();
            if (bankkonto.getBesitzer() != null) {
                bankkontoEntity.besitzer = new KundeEntity(bankkonto.getBesitzer());
            }
            em.merge(bankkontoEntity);
            em.getTransaction().commit();
        }
    }

    public List<Bankkonto> alleLesen() {
        TypedQuery<BankkontoEntity> query = em.createQuery("SELECT b FROM BankkontoEntity b", BankkontoEntity.class);
        return query.getResultList().stream()
                .map(BankkontoEntity::toBankkonto)
                .collect(Collectors.toList());
    }
}
