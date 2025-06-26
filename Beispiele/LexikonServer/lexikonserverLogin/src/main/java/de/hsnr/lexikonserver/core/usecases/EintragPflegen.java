/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonserver.core.usecases;

import de.hsnr.lexikonserver.core.entities.Autor;
import de.hsnr.lexikonserver.core.entities.Eintrag;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

/**
 *
 * @author aschekelmann
 */
@Stateless
public class EintragPflegen implements IEintragPflegen {

    @EJB private EintragManager eintragManager;
    @EJB private AutorManager autorManager;
    
    @Override
    public Eintrag eintragHinzufuegen (String begriff, String definition, int autorid) {
	
        // diverse fachliche Prüfungen hätten hier Platz :-)

        Autor autor = autorManager.autorSuchenPerPK(autorid);
        if (autor.getAutorid() == -1)
            return null;
        
        Eintrag eintrag = new Eintrag();
        eintrag.setAutor(autor); eintrag.setBegriff(begriff);eintrag.setDefinition(definition);
          
        return eintragManager.eintragHinzufuegen(eintrag);
		
    }
    
    
    @Override
    public boolean eintragEntfernen (int eintragid) {
        
        Eintrag eintrag = eintragManager.eintragSuchenPerPK(eintragid); 

        if ( eintrag == null)
            return false;

        return eintragManager.eintragLoeschen(eintrag);
                    
    }
    
    @Override
    public boolean eintragAendern (int eintragid, String begriff, String definition, int autorid) {
        
        Eintrag eintrag = eintragManager.eintragSuchenPerPK(eintragid); 

        if ( eintrag == null)
            return false;
        
        Autor autor = autorManager.autorSuchenPerPK(autorid);
        if (autor == null)
            return false;
        
        eintrag.setBegriff(begriff);
        eintrag.setDefinition(definition);
        eintrag.setAutor(autor);
       

        return eintragManager.eintragAendern(eintrag);
                    
    }


   
}
