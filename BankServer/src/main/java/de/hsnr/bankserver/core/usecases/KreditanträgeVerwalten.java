package de.hsnr.bankserver.core.usecases;

import de.hsnr.bankserver.core.entities.Kreditantrag;
import de.hsnr.bankserver.dataaccess.dao.KreditantragDAO;
import de.hsnr.bankserver.dataaccess.entities.KreditantragEntity;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Use Case für Kreditanträge verwalten
 */
@Stateless
public class KreditanträgeVerwalten {
    
    @Inject
    private KreditantragDAO kreditantragDAO;
    
    public void addKreditantrag(Kreditantrag kreditantrag) {
        KreditantragEntity entity = mapToEntity(kreditantrag);
        kreditantragDAO.save(entity);
    }
    
    public void editKreditantrag(Kreditantrag kreditantrag) {
        KreditantragEntity entity = mapToEntity(kreditantrag);
        kreditantragDAO.update(entity);
    }
    
    public void acceptKreditantrag(String kreditantragsnummer, String genehmigenderMitarbeiter) {
        KreditantragEntity entity = kreditantragDAO.findByKreditantragsnummer(kreditantragsnummer);
        if (entity != null) {
            entity.setKreditstatus("genehmigt");
            entity.setGenehmigenderMitarbeiter(genehmigenderMitarbeiter);
            kreditantragDAO.update(entity);
        }
    }
    
    public void denyKreditantrag(String kreditantragsnummer) {
        KreditantragEntity entity = kreditantragDAO.findByKreditantragsnummer(kreditantragsnummer);
        if (entity != null) {
            entity.setKreditstatus("abgelehnt");
            kreditantragDAO.update(entity);
        }
    }
    
    public List<Kreditantrag> getAllKreditanträge() {
        List<KreditantragEntity> entities = kreditantragDAO.findAll();
        List<Kreditantrag> kreditanträge = new ArrayList<>();
        for (KreditantragEntity entity : entities) {
            kreditanträge.add(mapToBusinessObject(entity));
        }
        return kreditanträge;
    }
    
    public List<Kreditantrag> getKreditanträgeByStatus(String status) {
        List<KreditantragEntity> entities = kreditantragDAO.findByStatus(status);
        List<Kreditantrag> kreditanträge = new ArrayList<>();
        for (KreditantragEntity entity : entities) {
            kreditanträge.add(mapToBusinessObject(entity));
        }
        return kreditanträge;
    }
    
    private Kreditantrag mapToBusinessObject(KreditantragEntity entity) {
        return new Kreditantrag(entity.getKreditantragsnummer(), entity.getAntragsteller(), entity.getKreditsumme(),
                               entity.getKreditlaufzeit(), entity.getKreditstatus(), entity.getGenehmigenderMitarbeiter());
    }
    
    private KreditantragEntity mapToEntity(Kreditantrag kreditantrag) {
        return new KreditantragEntity(kreditantrag.getKreditantragsnummer(), kreditantrag.getAntragsteller(), kreditantrag.getKreditsumme(),
                                      kreditantrag.getKreditlaufzeit(), kreditantrag.getKreditstatus(), kreditantrag.getGenehmigenderMitarbeiter());
    }
}
