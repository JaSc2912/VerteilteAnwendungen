package de.hsnr.bankserver.dataaccess.dao;

import de.hsnr.bankserver.dataaccess.entities.BenutzerEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

/**
 * DAO für Benutzer
 */
@Transactional
public class BenutzerDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    public void save(BenutzerEntity benutzer) {
        em.persist(benutzer);
    }
    
    public BenutzerEntity findByBenutzername(String benutzername) {
        try {
            return em.createQuery("SELECT b FROM BenutzerEntity b WHERE b.benutzername = :benutzername", BenutzerEntity.class)
                    .setParameter("benutzername", benutzername)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<BenutzerEntity> findAll() {
        return em.createQuery("SELECT b FROM BenutzerEntity b", BenutzerEntity.class).getResultList();
    }
    
    public void update(BenutzerEntity benutzer) {
        em.merge(benutzer);
    }
    
    public void delete(String benutzername) {
        BenutzerEntity benutzer = findByBenutzername(benutzername);
        if (benutzer != null) {
            em.remove(benutzer);
        }
    }
}
