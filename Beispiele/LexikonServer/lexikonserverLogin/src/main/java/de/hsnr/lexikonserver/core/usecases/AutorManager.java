/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonserver.core.usecases;

import de.hsnr.lexikonserver.core.entities.Autor;
import de.hsnr.lexikonserver.dataaccess.AutorDAO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;

/**
 *
 * @author aschekelmann
 */
@Stateless
public class AutorManager {
	
    @EJB AutorDAO autorDAO;
	
    public Autor autorHinzufuegen(Autor autor) {
        return autorDAO.anlegen(autor);
    }
    
    public List<Autor> autorlisteLiefern() {
        return autorDAO.alleLesen();
    }
    
    public Autor autorSuchenPerPK(int autorid) {

        return autorDAO.suchen(autorid);
        
    }
    
     
}
