package de.hsnr.bankserver.core.usecases;

import de.hsnr.bankserver.core.entities.Transaktion;
import de.hsnr.bankserver.dataaccess.dao.TransaktionDAO;
import de.hsnr.bankserver.dataaccess.entities.TransaktionEntity;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Use Case für Kontotransaktionen verwalten
 */
@Stateless
public class KontotransaktionenVerwalten {
    
    @Inject
    private TransaktionDAO transaktionDAO;
    
    public void addTransaktion(Transaktion transaktion) {
        TransaktionEntity entity = mapToEntity(transaktion);
        transaktionDAO.save(entity);
    }
    
    public void storniereTransaktion(String transaktionsnummer) {
        TransaktionEntity entity = transaktionDAO.findByTransaktionsnummer(transaktionsnummer);
        if (entity != null) {
            entity.setTransaktionsstatus("storniert");
            transaktionDAO.update(entity);
        }
    }
    
    public List<Transaktion> getTransaktionenByKonto(String konto) {
        List<TransaktionEntity> entities = transaktionDAO.findByKonto(konto);
        List<Transaktion> transaktionen = new ArrayList<>();
        for (TransaktionEntity entity : entities) {
            transaktionen.add(mapToBusinessObject(entity));
        }
        return transaktionen;
    }
    
    public List<Transaktion> getAllTransaktionen() {
        List<TransaktionEntity> entities = transaktionDAO.findAll();
        List<Transaktion> transaktionen = new ArrayList<>();
        for (TransaktionEntity entity : entities) {
            transaktionen.add(mapToBusinessObject(entity));
        }
        return transaktionen;
    }
    
    private Transaktion mapToBusinessObject(TransaktionEntity entity) {
        return new Transaktion(entity.getTransaktionsnummer(), entity.getKonto(), entity.getTransaktionsdatum(),
                              entity.getBetrag(), entity.getTransaktionsart(), entity.getEmpfänger(), entity.getTransaktionsstatus());
    }
    
    private TransaktionEntity mapToEntity(Transaktion transaktion) {
        return new TransaktionEntity(transaktion.getTransaktionsnummer(), transaktion.getKonto(), transaktion.getTransaktionsdatum(),
                                     transaktion.getBetrag(), transaktion.getTransaktionsart(), transaktion.getEmpfänger(), transaktion.getTransaktionsstatus());
    }
}
