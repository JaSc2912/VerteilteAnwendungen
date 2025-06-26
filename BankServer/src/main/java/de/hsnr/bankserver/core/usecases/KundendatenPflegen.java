package de.hsnr.bankserver.core.usecases;

import de.hsnr.bankserver.core.entities.Kunde;
import de.hsnr.bankserver.dataaccess.dao.KundeDAO;
import de.hsnr.bankserver.dataaccess.entities.KundeEntity;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Use Case für Kundendaten pflegen
 */
@Stateless
public class KundendatenPflegen {
    
    @Inject
    private KundeDAO kundeDAO;
    
    public void addKunde(Kunde kunde) {
        KundeEntity entity = mapToEntity(kunde);
        kundeDAO.save(entity);
    }
    
    public void deleteKunde(String kundennummer) {
        kundeDAO.delete(kundennummer);
    }
    
    public void editKunde(Kunde kunde) {
        KundeEntity entity = mapToEntity(kunde);
        kundeDAO.update(entity);
    }
    
    public List<Kunde> getAllKunden() {
        List<KundeEntity> entities = kundeDAO.findAll();
        List<Kunde> kunden = new ArrayList<>();
        for (KundeEntity entity : entities) {
            kunden.add(mapToBusinessObject(entity));
        }
        return kunden;
    }
    
    public Kunde getKunde(String kundennummer) {
        KundeEntity entity = kundeDAO.findByKundennummer(kundennummer);
        return entity != null ? mapToBusinessObject(entity) : null;
    }
    
    private Kunde mapToBusinessObject(KundeEntity entity) {
        return new Kunde(entity.getKundennummer(), entity.getName(), entity.getAdresse(),
                        entity.getTelefonnummer(), entity.getEmail(), entity.getGeburtsdatum(),
                        entity.getKundenstatus());
    }
    
    private KundeEntity mapToEntity(Kunde kunde) {
        return new KundeEntity(kunde.getKundennummer(), kunde.getName(), kunde.getAdresse(),
                              kunde.getTelefonnummer(), kunde.getEmail(), kunde.getGeburtsdatum(),
                              kunde.getKundenstatus());
    }
}
