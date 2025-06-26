/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonserver.core.usecases;

import de.hsnr.lexikonserver.core.entities.Eintrag;
import de.hsnr.lexikonserver.dataaccess.EintragDAO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;

/**
 *
 * @author aschekelmann
 */

@Stateless
public class EintragManager {

    @EJB EintragDAO eintragDAO;
    
    public Eintrag eintragSuchenPerPK(int eintragid) {

        return eintragDAO.suchen(eintragid);
         
    }
    
    
    public Eintrag eintragHinzufuegen(Eintrag eintrag) {
        return eintragDAO.anlegen(eintrag);
            
	}
      

    public boolean eintragLoeschen (Eintrag eintrag) {
            
        return eintragDAO.loeschen(eintrag);
            
    }
    
    public boolean eintragAendern (Eintrag eintrag) {
            
        return eintragDAO.aendern(eintrag);
            
    }


    
        
    public List <Eintrag> eintragSuchen(Eintrag suchEintrag) {
            
        return eintragDAO.suchen(suchEintrag);
           
    }
           
}
	