/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonclient;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 *
 * @author aschekelmann
 */
@Named
@RequestScoped
public class EintragHinzufuegenBean implements Serializable{
    private static final long serialVersionUID = -211073631543658209L;

    private int eintragid;
    private String begriff;
    private String definition;
    
   
    private String encodedAuth;
    
    
    public int getEintragid() {
        return eintragid;
    }
    public void setEintragid(int eintragid) {
        this.eintragid = eintragid;
    }
    public String getBegriff() {
            return begriff;
    }
    public void setBegriff(String begriff) {
            this.begriff = begriff;
    }
    public String getDefinition() {
            return definition;
    }
    public void setDefinition(String definition) {
            this.definition = definition;
    }
    
    
    public class Autor {
        int autorid;
        String name;

        public int getAutorid() {
            return autorid;
        }

        public void setAutorid(int autorid) {
            this.autorid = autorid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Autor(int autorid, String name) {
            this.autorid = autorid;
            this.name = name;
        }
        
    }
    
    private List<Autor> autorlist;
    private int  selectedAutor;
    
    public void setSelectedAutor(int autor) {
        selectedAutor = autor;
    }
    public int getSelectedAutor() {
        return selectedAutor ;
    }
    public List<Autor> getAutorlist() {
        return autorlist;
    }
    
    
    @PostConstruct
    public void init () {
        String kennung;
        String secret;
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        kennung = (String) session.getAttribute("kennung");
        secret = (String) session.getAttribute("secret");
        String auth = kennung + ":" + secret;
        encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
       
        
        Client client = ClientBuilder.newClient();       
        WebTarget target = client.target(url).path("/admin").path("/autor");
            
        AutorTOList autorTOList= target
        .request(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth)
        .get(AutorTOList.class);

        List<AutorTO> ergebnisList = autorTOList.inhalt();
        
        autorlist = new ArrayList<>();
        for (AutorTO autorTO:ergebnisList)
            autorlist.add(new Autor (autorTO.autorid(),autorTO.name()));       
    }
            
    
    String url = "http://localhost:8080/lexikonserverLogin/restapi";
    public String hinzufuegen() {
 
        EintragTO eintragTO = new EintragTO(-1, begriff, definition, selectedAutor, null);

        Client client = ClientBuilder.newClient();       
        WebTarget target = client.target(url).path("/admin").path("/eintrag");
        
        Response response = target
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth)
                .post(Entity.entity(eintragTO, MediaType.APPLICATION_JSON),Response.class);

        MultivaluedMap<String,Object> headers = response.getHeaders();
        String location = (String) headers.getFirst("Location");
        String eintragidString = location.substring(location.lastIndexOf('/') + 1);
        eintragid = Integer.parseInt(eintragidString);

        return "/eintragHinzufuegenRes.xhtml";
    }
    
    
    
            
}