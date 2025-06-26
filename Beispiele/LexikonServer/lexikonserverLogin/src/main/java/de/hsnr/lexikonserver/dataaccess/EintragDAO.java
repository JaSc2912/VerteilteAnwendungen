/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonserver.dataaccess;

import de.hsnr.lexikonserver.core.entities.Eintrag;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author aschekelmann
 */

@Singleton
public class EintragDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    public Eintrag suchen(int eintragid) {
        EintragEntity eintragEntity = em.find(EintragEntity.class,eintragid);
        
        if ( eintragEntity == null)
            return null;
        else
            return eintragEntity.toEintrag();
        
    }
    
    
    
    
    public Eintrag anlegen(Eintrag eintrag) {
        EintragEntity eintragEntity = new EintragEntity(eintrag);
        em.persist(eintragEntity);
        
        return eintragEntity.toEintrag();
        
    }
    
    public boolean loeschen(Eintrag eintrag) {
        EintragEntity eintragEntity = em.find(EintragEntity.class,eintrag.getEintragid());       
        
        if (eintragEntity == null)
            return false;
        else {
            em.remove(eintragEntity);
            return true;
        }
    }

    public boolean aendern(Eintrag eintrag) {
        
        EintragEntity eintragEntity = em.find(EintragEntity.class,eintrag.getEintragid());       
        if (eintragEntity == null)
            return false;
        
        AutorEntity autorEntity = em.find(AutorEntity.class, eintrag.getAutor().getAutorid());
        
        if (autorEntity == null)
            return false;
       
        eintragEntity.setBegriff(eintrag.getBegriff());
        eintragEntity.setDefinition(eintrag.getDefinition());
        eintragEntity.setAutorentity(autorEntity);

        em.persist(eintragEntity);
        
        return true;
            
    }
        
     
    
    
    public List <Eintrag> suchen(Eintrag suchEintrag) {
        
        String queryString;
        
        if (suchEintrag.getAutor()==null && suchEintrag.getBegriff()==null && suchEintrag.getDefinition()==null)
            queryString = "SELECT e FROM EintragEntity e";
        else {
            queryString = "SELECT e FROM EintragEntity e WHERE ";    
        
            if (suchEintrag.getAutor() != null)
                    queryString += "e.autor = " + suchEintrag.getAutor();
            if (suchEintrag.getBegriff() != null)
                    queryString += "e.begriff = " + "'" + suchEintrag.getBegriff() + "'";
             if (suchEintrag.getDefinition() != null)
                    queryString += "e.definition = " + "'" + suchEintrag.getDefinition() + "'";
        }
            
        TypedQuery<EintragEntity> query
                = em.createQuery (queryString, EintragEntity.class);
                
        return 
            query.getResultList().stream() 
                .map(eintragEntity -> eintragEntity.toEintrag()) 
                .collect(Collectors.toList()); 
                
    }
}