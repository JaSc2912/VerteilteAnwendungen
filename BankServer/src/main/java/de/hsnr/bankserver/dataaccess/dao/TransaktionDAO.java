package de.hsnr.bankserver.dataaccess.dao;

import de.hsnr.bankserver.dataaccess.entities.TransaktionEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * DAO für Transaktion
 */
@Transactional
public class TransaktionDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    public void save(TransaktionEntity transaktion) {
        em.persist(transaktion);
    }
    
    public TransaktionEntity findByTransaktionsnummer(String transaktionsnummer) {
        try {
            return em.createQuery("SELECT t FROM TransaktionEntity t WHERE t.transaktionsnummer = :transaktionsnummer", TransaktionEntity.class)
                    .setParameter("transaktionsnummer", transaktionsnummer)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<TransaktionEntity> findAll() {
        return em.createQuery("SELECT t FROM TransaktionEntity t", TransaktionEntity.class).getResultList();
    }
    
    public List<TransaktionEntity> findByKonto(String konto) {
        return em.createQuery("SELECT t FROM TransaktionEntity t WHERE t.konto = :konto", TransaktionEntity.class)
                .setParameter("konto", konto)
                .getResultList();
    }
    
    public Double calculateMonthlyIncome(String konto, int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1, 0, 0, 0);
        Date startDate = cal.getTime();
        cal.add(Calendar.MONTH, 1);
        Date endDate = cal.getTime();
        
        Double result = em.createQuery("SELECT SUM(t.betrag) FROM TransaktionEntity t WHERE t.konto = :konto AND t.betrag > 0 AND t.transaktionsdatum >= :startDate AND t.transaktionsdatum < :endDate", Double.class)
                .setParameter("konto", konto)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getSingleResult();
        
        return result != null ? result : 0.0;
    }
    
    public Double calculateMonthlyOutcome(String konto, int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1, 0, 0, 0);
        Date startDate = cal.getTime();
        cal.add(Calendar.MONTH, 1);
        Date endDate = cal.getTime();
        
        Double result = em.createQuery("SELECT SUM(t.betrag) FROM TransaktionEntity t WHERE t.konto = :konto AND t.betrag < 0 AND t.transaktionsdatum >= :startDate AND t.transaktionsdatum < :endDate", Double.class)
                .setParameter("konto", konto)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getSingleResult();
        
        return result != null ? Math.abs(result) : 0.0;
    }
    
    public void update(TransaktionEntity transaktion) {
        em.merge(transaktion);
    }
    
    public void delete(String transaktionsnummer) {
        TransaktionEntity transaktion = findByTransaktionsnummer(transaktionsnummer);
        if (transaktion != null) {
            em.remove(transaktion);
        }
    }
}
