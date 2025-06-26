package de.hsnr.bankserver.dataaccess.dao;

import de.hsnr.bankserver.dataaccess.entities.KreditantragEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

/**
 * DAO für Kreditantrag
 */
@Transactional
public class KreditantragDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    public void save(KreditantragEntity kreditantrag) {
        em.persist(kreditantrag);
    }
    
    public KreditantragEntity findByKreditantragsnummer(String kreditantragsnummer) {
        try {
            return em.createQuery("SELECT k FROM KreditantragEntity k WHERE k.kreditantragsnummer = :kreditantragsnummer", KreditantragEntity.class)
                    .setParameter("kreditantragsnummer", kreditantragsnummer)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<KreditantragEntity> findAll() {
        return em.createQuery("SELECT k FROM KreditantragEntity k", KreditantragEntity.class).getResultList();
    }
    
    public List<KreditantragEntity> findByAntragsteller(String antragsteller) {
        return em.createQuery("SELECT k FROM KreditantragEntity k WHERE k.antragsteller = :antragsteller", KreditantragEntity.class)
                .setParameter("antragsteller", antragsteller)
                .getResultList();
    }
    
    public List<KreditantragEntity> findByStatus(String status) {
        return em.createQuery("SELECT k FROM KreditantragEntity k WHERE k.kreditstatus = :status", KreditantragEntity.class)
                .setParameter("status", status)
                .getResultList();
    }
    
    public void update(KreditantragEntity kreditantrag) {
        em.merge(kreditantrag);
    }
    
    public void delete(String kreditantragsnummer) {
        KreditantragEntity kreditantrag = findByKreditantragsnummer(kreditantragsnummer);
        if (kreditantrag != null) {
            em.remove(kreditantrag);
        }
    }
}
