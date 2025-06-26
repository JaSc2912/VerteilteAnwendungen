package de.hsnr.bankserver.dataaccess.dao;

import de.hsnr.bankserver.dataaccess.entities.BankkontoEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

/**
 * DAO für Bankkonto
 */
@Transactional
public class BankkontoDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    public void save(BankkontoEntity bankkonto) {
        em.persist(bankkonto);
    }
    
    public BankkontoEntity findByIban(String iban) {
        try {
            return em.createQuery("SELECT b FROM BankkontoEntity b WHERE b.iban = :iban", BankkontoEntity.class)
                    .setParameter("iban", iban)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<BankkontoEntity> findAll() {
        return em.createQuery("SELECT b FROM BankkontoEntity b", BankkontoEntity.class).getResultList();
    }
    
    public List<BankkontoEntity> findActiveAccountsWithPositiveBalance() {
        return em.createQuery("SELECT b FROM BankkontoEntity b WHERE b.kontostatus = 'aktiv' AND b.kontostand > 0", BankkontoEntity.class)
                .getResultList();
    }
    
    public boolean hasActiveTransactions(String iban) {
        Long count = em.createQuery("SELECT COUNT(t) FROM TransaktionEntity t WHERE t.konto = :iban AND t.transaktionsstatus = 'ausstehend'", Long.class)
                .setParameter("iban", iban)
                .getSingleResult();
        return count > 0;
    }
    
    public void update(BankkontoEntity bankkonto) {
        em.merge(bankkonto);
    }
    
    public void delete(String iban) {
        BankkontoEntity bankkonto = findByIban(iban);
        if (bankkonto != null) {
            em.remove(bankkonto);
        }
    }
}
