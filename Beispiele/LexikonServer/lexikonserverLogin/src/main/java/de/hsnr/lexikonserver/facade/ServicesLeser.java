/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonserver.facade;

import de.hsnr.lexikonserver.core.entities.Eintrag;
import de.hsnr.lexikonserver.core.usecases.IDefinitionSuchen;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author aschekelmann
 */
@Path("/")
public class ServicesLeser {
    
   
    
    @GET
    @Path("/leser")
    @Produces({MediaType.TEXT_PLAIN})
    public String explain() {
        return "API fuer die Nutzer-Schnittstelle eines kleines Lexikons. "
                + "Zugriff besteht auf die Ressource lexikon/leser/eintrag";
    }
        
    @EJB private IDefinitionSuchen definitionSuchen;
    
    @GET
    @Path("/leser/eintrag/{begriff}")
    @Consumes ("text/plain")
    @Produces({MediaType.APPLICATION_JSON})
    public EintragTOList readEintrag(@PathParam("begriff") String begriff) {

        List <Eintrag> liste = definitionSuchen.suchen(begriff);

        EintragTOList listeTO;

        if (!liste.isEmpty()) {
            listeTO = new EintragTOList( 
                liste.stream()
                .map(eintrag -> new EintragTO(eintrag.getEintragid(), eintrag.getBegriff()
                        , eintrag.getDefinition(),eintrag.getAutor().getAutorid(), eintrag.getAutor().getName()))
                .collect(Collectors.toList())
            );
        }
        else {
            listeTO = new EintragTOList(null);
        }

        return listeTO;
    }
}
            

            
