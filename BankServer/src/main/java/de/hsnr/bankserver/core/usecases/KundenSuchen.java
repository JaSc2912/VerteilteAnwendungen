package de.hsnr.bankserver.core.usecases;

import de.hsnr.bankserver.core.entities.Kunde;
import de.hsnr.bankserver.dataaccess.dao.KundeDAO;
import de.hsnr.bankserver.dataaccess.entities.KundeEntity;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Use Case für Kunden suchen
 */
@Stateless
public class KundenSuchen {
    
    @Inject
    private KundeDAO kundeDAO;
    
    public List<Kunde> searchKunde(String name) {
        List<KundeEntity> entities = kundeDAO.searchByName(name);
        List<Kunde> kunden = new ArrayList<>();
        for (KundeEntity entity : entities) {
            kunden.add(mapToBusinessObject(entity));
        }
        return kunden;
    }
    
    private Kunde mapToBusinessObject(KundeEntity entity) {
        return new Kunde(entity.getKundennummer(), entity.getName(), entity.getAdresse(),
                        entity.getTelefonnummer(), entity.getEmail(), entity.getGeburtsdatum(),
                        entity.getKundenstatus());
    }
}
