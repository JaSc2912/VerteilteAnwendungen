/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonserver.facade;

import de.hsnr.lexikonserver.core.entities.Autor;
import de.hsnr.lexikonserver.core.entities.Eintrag;
import de.hsnr.lexikonserver.core.usecases.IAutorPflegen;
import de.hsnr.lexikonserver.core.usecases.IAutorSuchen;
import de.hsnr.lexikonserver.core.usecases.IEintragPflegen;
import de.hsnr.lexikonserver.core.usecases.IEintragSuchen;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author aschekelmann
 */
@Path("/")
public class ServicesAdmin {
    
    @EJB private IEintragSuchen eintragSuchen;
    @EJB private IEintragPflegen eintragPflegen;
    @EJB private IAutorSuchen autorSuchen;
    @EJB private IAutorPflegen autorPflegen;
  
    
    /*
    Gemäß Einstellungen in der Web.xml ist der Zugriff auf alle URI unter restapi/admin
    geschützt.
    Beim Aufruf der REST-Services müssen Kennung und Secret mitgegeben werden.
    Ehe die REST-Services ausgeführt werden, wird geprüft, 
    ob Kennung und Secret korrekt sind (Authentifizierung).
    Sind Sie korrekt, so wird der Kennung die Rolle "Admin" zugeorndet.
    Damit kann der Aufrufer die REST-Services dann ausführen (Autosierung).
    
    Hinweis: In dieser Lösung werden Kennung und Secret jedes Mal, 
    also bei jedem Zugriff übertragen.
    Eine Alterntaive wäre es, bei erfolgreicher Authentifizierung eine Token an
    dem Aufrufer zurückzuliefern, der dieses dann für weitere Aufrufe verwendet.
    Der IdentityStore (siehe dort) ist ebenfalls eine einfache Lösung.
    
    Diese Implementierung ist keine, die man in der Realität so einsetzen würde.
    Sie soll das Problem bewusst machen und grundsätzliche Aspekte jeder Lösung,
    insb. Authentifizierung und Autorisierung, aufzeigen.
    
    */
   
    @GET
    @Path("/admin")
    @Produces({MediaType.TEXT_PLAIN})
    public String explain() {
        return "API fuer die Admin-Schnittstelle eines kleines Lexikons. "
            + "Zugriff besteht auf die Ressource lexikon/admin/eintrag und lexikon/admin/autor";
    }
        
    @POST
    @Path("/admin/eintrag")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createEintrag(EintragTO eintragTO, @Context UriInfo uriInfo ) {

        Eintrag eintrag 
                = eintragPflegen.eintragHinzufuegen(
                        eintragTO.begriff() 
                        , eintragTO.definition()
                        , eintragTO.autorid()
                        );
        
        if (eintrag == null) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        else {
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            uriBuilder.path(Integer.toString(eintrag.getEintragid()));
            return Response.created(uriBuilder.build()).build();
        }
        
    }
        
    @PUT
    @Path("/admin/eintrag/")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateEintrag(EintragTO eintragTO ) {
        
        if (eintragPflegen.eintragAendern(
                eintragTO.eintragid()
                ,eintragTO.begriff()
                ,eintragTO.definition()
                ,eintragTO.autorid()
                ))
            return Response.status(Response.Status.OK).build();
        else {
            return Response.status(Response.Status.CONFLICT).build(); 
        }
    }
        
    @DELETE
    @Path("/admin/eintrag/{eintragid}")
    public Response deleteEintrag(@PathParam("eintragid") int eintragid ) {
        if (eintragPflegen.eintragEntfernen(eintragid))
            return Response.status(Response.Status.OK).build();
        else
            return Response.status(Response.Status.CONFLICT).build();
    }
   
    @GET
    @Path("/admin/eintrag/{eintragid}")
    @Produces({MediaType.APPLICATION_JSON})
    public EintragTO readEintrag(@PathParam("eintragid") int eintragid) {  
           
        Eintrag suchEintrag;
        suchEintrag = eintragSuchen.eintragSuchenPerPK(eintragid);
        Optional<Eintrag> optEintrag = Optional.ofNullable(suchEintrag);

        EintragTO eintragTO;
        eintragTO = optEintrag.map(eintrag -> new EintragTO(eintrag.getEintragid()
                , eintrag.getBegriff(), eintrag.getDefinition()
                ,eintrag.getAutor().getAutorid(),eintrag.getAutor().getName()))
            .orElse(new EintragTO(-1,"","",-1,""));

        return eintragTO;
    }
        
    @GET
    @Path("/admin/eintrag")
    @Produces({MediaType.APPLICATION_JSON}) 
    public EintragTOList allEintrag() {
            
        List <Eintrag> liste = eintragSuchen.allEintrag();

        EintragTOList listeTO;
        if (!liste.isEmpty()) {
            listeTO = new EintragTOList( 
                liste.stream()
                .map(eintrag -> 
                        new EintragTO(eintrag.getEintragid()
                                , eintrag.getBegriff()
                                , eintrag.getDefinition()
                                , eintrag.getAutor().getAutorid()
                                ,""))
                .collect(Collectors.toList())
            );
        }
        
        else {
            listeTO = new EintragTOList(null);
        }

        return listeTO;	
    }
        
    
    @POST
    @Path("/admin/autor")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createAutor(AutorTO autorTO, @Context UriInfo uriInfo ) {
        
        Autor autor = autorPflegen.autorHinzufuegen(autorTO.name());
                
        if (autor == null) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        else {
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            uriBuilder.path(Integer.toString(autor.getAutorid()));
            return Response.created(uriBuilder.build()).build();
        }

    }    
        
    @GET
    @Path("/admin/autor")
    @Produces({MediaType.APPLICATION_JSON}) 
    public AutorTOList allAutor() {
        
        List <Autor> liste = autorSuchen.allAutor();
        
        AutorTOList listeTO;
        if (!liste.isEmpty()) {
            listeTO = new AutorTOList( 
                liste.stream()
                .map(autor -> new AutorTO(autor.getAutorid(), autor.getName()))
                .collect(Collectors.toList())
            );
        }
        else {
            listeTO = new AutorTOList(null);
        }
        
        
        return listeTO;
    }
	
    @GET
    @Path("/admin/autor/{autorid}")
    @Produces({MediaType.APPLICATION_JSON}) 
    public AutorTO readAutor(@PathParam("autorid") int autorid) {	

        Autor suchAutor;
        suchAutor = autorSuchen.autorSuchenPerPK(autorid);
        Optional<Autor> optAutor = Optional.ofNullable(suchAutor);

        AutorTO autorTO;
        autorTO = optAutor.map(autor -> new AutorTO(autor.getAutorid(), autor.getName()))
            .orElse(new AutorTO (-1,""));

    return autorTO;
    }
	
    
}
            
