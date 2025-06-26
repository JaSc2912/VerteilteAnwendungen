/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonserver.dataaccess;


import de.hsnr.lexikonserver.core.entities.Autor;
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
public class AutorDAO {
	
    @PersistenceContext
    private EntityManager em;

    public Autor suchen(int autorid) {
        
        AutorEntity autorEntity = em.find(AutorEntity.class,autorid);
        
        if (autorEntity == null)
            return null;
        else
            return autorEntity.toAutor();
}

     public Autor anlegen(Autor autor) {
         
        AutorEntity autorEntity = new AutorEntity(autor);
        em.persist(autorEntity);
        
        return autorEntity.toAutor();
        
       }

     public boolean loeschen(Autor autor) {
         AutorEntity autorEntity = em.find(AutorEntity.class,autor.getAutorid());       
         if (autorEntity == null)
             return false;
         
         em.remove(autorEntity);
         return true;
     }

     public List<Autor> alleLesen() {		 

        TypedQuery<AutorEntity> query = em.createQuery("SELECT a FROM AutorEntity a",AutorEntity.class);
        
        return
            query.getResultList().stream() 
                .map(autorEntity -> autorEntity.toAutor()) 
                .collect(Collectors.toList()); 
        
            
        
     }
         
}
