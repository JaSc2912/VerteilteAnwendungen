/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonserver.dataaccess;


import de.hsnr.lexikonserver.core.entities.Admin;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author aschekelmann
 */
@Singleton
public class AdminDAO {
	
    @PersistenceContext
    private EntityManager em;

    public Admin suchen(String kennung) {
        
        AdminEntity adminEntity = em.find(AdminEntity.class,kennung);
        
        if (adminEntity != null)
            return adminEntity.toAdmin();
        
        return null;
    }
     
}
