package de.hsnr.bankserver.dataaccess.dao;

import de.hsnr.bankserver.dataaccess.entities.KundeEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

/**
 * DAO für Kunde
 */
@Transactional
public class KundeDAO {

    @PersistenceContext
    private EntityManager em;

    public void save(KundeEntity kunde) {
        em.persist(kunde);
    }

    public KundeEntity findByKundennummer(String kundennummer) {
        try {
            return em.createQuery("SELECT k FROM KundeEntity k WHERE k.kundennummer = :kundennummer", KundeEntity.class)
                    .setParameter("kundennummer", kundennummer)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<KundeEntity> findAll() {
        return em.createQuery("SELECT k FROM KundeEntity k", KundeEntity.class).getResultList();
    }

    public List<KundeEntity> searchByName(String name) {
        return em.createQuery("SELECT k FROM KundeEntity k WHERE UPPER(k.name) LIKE UPPER(:name)", KundeEntity.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    public void update(KundeEntity kunde) {
        em.merge(kunde);
    }

    public void delete(String kundennummer) {
        KundeEntity kunde = findByKundennummer(kundennummer);
        if (kunde != null) {
            em.remove(kunde);
        }
    }
}
