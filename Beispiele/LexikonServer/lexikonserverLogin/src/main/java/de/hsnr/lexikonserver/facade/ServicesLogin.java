/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonserver.facade;

import de.hsnr.lexikonserver.core.usecases.IAdminEinloggen;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


/**
 *
 * @author aschekelmann
 */
@Path("/")
public class ServicesLogin {
    
   
    @EJB private IAdminEinloggen adminEinloggen;
    
    @GET
    @Path("/login/{kennung}/{secret}")
    @Consumes ("text/plain")
    @Produces({MediaType.TEXT_PLAIN})
    public String login(@PathParam("kennung") String kennung,@PathParam("secret") String secret ) {
        
        /* Kennung und Secret werden im Klartext übertragen.
        Das ist eine Vereinachung, beides sollte in der Übertragung verschlüsselt werden
        */
        
        if (adminEinloggen.login(kennung, secret))
            return "true";
        else
            return "false";

       
    }
}
            

            
