/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.dataaccess;

import de.hsnr.bank.entities.Bankkonto;
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
        if (bankkonto.getBesitzer() != null) {
            KundeEntity besitzerEntity = em.find(KundeEntity.class, bankkonto.getBesitzer().getKundennummer());
            bankkontoEntity.besitzer = besitzerEntity;
        }
        em.persist(bankkontoEntity);
    }

    public void deleteBankkonto(String iban) {
        BankkontoEntity bankkontoEntity = em.find(BankkontoEntity.class, iban);
        if (bankkontoEntity != null) {
            em.remove(bankkontoEntity);
        }
    }

    public void editBankkonto(Bankkonto bankkonto) {
        BankkontoEntity bankkontoEntity = em.find(BankkontoEntity.class, bankkonto.getIban());
        if (bankkontoEntity != null) {
            bankkontoEntity.kontoart = bankkonto.getKontoart();
            bankkontoEntity.kontostand = bankkonto.getKontostand();
            bankkontoEntity.kontoeroeffnungsdatum = bankkonto.getKontoeroeffnungsdatum();
            bankkontoEntity.kontostatus = bankkonto.getKontostatus();
            if (bankkonto.getBesitzer() != null) {
                // Find the managed KundeEntity instead of creating a new one
                KundeEntity besitzerEntity = em.find(KundeEntity.class, bankkonto.getBesitzer().getKundennummer());
                bankkontoEntity.besitzer = besitzerEntity;
            } else {
                bankkontoEntity.besitzer = null;
            }
            em.merge(bankkontoEntity);
        }
    }

    public List<Bankkonto> alleLesen() {
        TypedQuery<BankkontoEntity> query = em.createQuery("SELECT b FROM BankkontoEntity b", BankkontoEntity.class);
        return query.getResultList().stream()
                .map(BankkontoEntity::toBankkonto)
                .collect(Collectors.toList());
    }

    public List<Bankkonto> findByKundennummer(String kundennummer) {
        TypedQuery<BankkontoEntity> query = em.createQuery(
                "SELECT b FROM BankkontoEntity b WHERE b.besitzer.kundennummer = :kundennummer",
                BankkontoEntity.class);
        query.setParameter("kundennummer", kundennummer);
        return query.getResultList().stream()
                .map(BankkontoEntity::toBankkonto)
                .collect(Collectors.toList());
    }
}
