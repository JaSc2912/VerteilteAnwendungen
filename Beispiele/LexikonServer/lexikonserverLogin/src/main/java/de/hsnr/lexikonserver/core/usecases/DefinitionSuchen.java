/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonserver.core.usecases;

import de.hsnr.lexikonserver.core.entities.Eintrag;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;

/**
 *
 * @author aschekelmann
 */

    
@Stateless
public class DefinitionSuchen implements IDefinitionSuchen {

    @EJB EintragManager eintragManager;
    
    @Override
    public List<Eintrag> suchen(String begriff) {
        
        Eintrag suchEintrag = new Eintrag (-1,begriff,null,null);
       
        return eintragManager.eintragSuchen(suchEintrag); 
        
    }  
}
