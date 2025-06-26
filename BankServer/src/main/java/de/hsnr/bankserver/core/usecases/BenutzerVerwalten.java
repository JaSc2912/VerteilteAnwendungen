package de.hsnr.bankserver.core.usecases;

import de.hsnr.bankserver.core.entities.Benutzer;
import de.hsnr.bankserver.core.entities.RolleT;
import de.hsnr.bankserver.dataaccess.dao.BenutzerDAO;
import de.hsnr.bankserver.dataaccess.entities.BenutzerEntity;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Use Case für Benutzer verwalten
 */
@Stateless
public class BenutzerVerwalten {
    
    @Inject
    private BenutzerDAO benutzerDAO;
    
    public Benutzer login(String benutzername, String passwort) {
        BenutzerEntity entity = benutzerDAO.findByBenutzername(benutzername);
        if (entity != null && entity.getPasswort().equals(passwort)) {
            return mapToBusinessObject(entity);
        }
        return null;
    }
    
    public void addBenutzer(Benutzer benutzer) {
        BenutzerEntity entity = mapToEntity(benutzer);
        benutzerDAO.save(entity);
    }
    
    public void deleteBenutzer(String benutzername) {
        benutzerDAO.delete(benutzername);
    }
    
    public void editBenutzer(Benutzer benutzer) {
        BenutzerEntity entity = mapToEntity(benutzer);
        benutzerDAO.update(entity);
    }
    
    public List<Benutzer> getAllBenutzer() {
        List<BenutzerEntity> entities = benutzerDAO.findAll();
        List<Benutzer> benutzer = new ArrayList<>();
        for (BenutzerEntity entity : entities) {
            benutzer.add(mapToBusinessObject(entity));
        }
        return benutzer;
    }
    
    private Benutzer mapToBusinessObject(BenutzerEntity entity) {
        return new Benutzer(entity.getBenutzername(), entity.getPasswort(), 
                           entity.getName(), entity.getTelefonnummer(), entity.getRolle());
    }
    
    private BenutzerEntity mapToEntity(Benutzer benutzer) {
        return new BenutzerEntity(benutzer.getBenutzername(), benutzer.getPasswort(), 
                                 benutzer.getName(), benutzer.getTelefonnummer(), benutzer.getRolle());
    }
}
