package de.hsnr.bank.dataaccess;

import de.hsnr.bank.entities.Kreditantrag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

public class KreditantragDAO {

    @PersistenceContext
    private EntityManager em;

    public Kreditantrag suchen(String konto) {
        KreditantragEntity entity = em.find(KreditantragEntity.class, konto);
        return entity != null ? entity.toKreditantrag() : null;
    }

    public void addKreditantrag(Kreditantrag kreditantrag) {
        KreditantragEntity entity = new KreditantragEntity(kreditantrag);
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    public void deleteKreditantrag(String konto) {
        KreditantragEntity entity = em.find(KreditantragEntity.class, konto);
        if (entity != null) {
            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
        }
    }

    public void editKreditantrag(Kreditantrag kreditantrag) {
        KreditantragEntity entity = em.find(KreditantragEntity.class, kreditantrag.getKonto());
        if (entity != null) {
            em.getTransaction().begin();
            entity.kreditsumme = kreditantrag.getKreditsumme();
            entity.laufzeit = kreditantrag.getLaufzeit();
            entity.zins = kreditantrag.getZins();
            em.merge(entity);
            em.getTransaction().commit();
        }
    }

    public List<Kreditantrag> alleLesen() {
        TypedQuery<KreditantragEntity> query = em.createQuery("SELECT k FROM KreditantragEntity k", KreditantragEntity.class);
        return query.getResultList().stream()
                .map(KreditantragEntity::toKreditantrag)
                .collect(Collectors.toList());
    }
}