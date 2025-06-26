/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonserver.core.usecases;

import de.hsnr.lexikonserver.core.entities.Autor;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;


/**
 *
 * @author aschekelmann
 */

@Stateless
public class AutorSuchen implements IAutorSuchen {

    @EJB AutorManager autorManager;
    
    @Override
    public Autor autorSuchenPerPK(int id) {
        
        return autorManager.autorSuchenPerPK(id);
        
    }
    
    @Override
    public List<Autor> allAutor() {
        
        return autorManager.autorlisteLiefern();
        
    }
    
    
}
