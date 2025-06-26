/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonclient;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aschekelmann
 */
@Named
@SessionScoped
public class EintragSuchenBean implements Serializable{
    private static final long serialVersionUID = -211073631543658209L;
    
    

    public class Tabelleneintrag {
		
        private String begriff;
        private String definition;
        private String autor;


        public Tabelleneintrag() {
        }
        public Tabelleneintrag(String begriff, String definition,String autor) {
                this.begriff = begriff;
                this.definition = definition;	
                this.autor= autor;	
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
        public String getAutor(){
            return autor;
        }
        public void setAutor (String autor) {
            this.autor = autor;
        }
    }
    
    private String begriff;
    private String definition;
    private List<Tabelleneintrag> tabelle;
   

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
    public List<Tabelleneintrag> getTabelle() {
        return tabelle;
    }
   
    String url = "http://localhost:8080/lexikonserverLogin/restapi";
    public String suchen () {
  
        Client client = ClientBuilder.newClient();       
        WebTarget target = client.target(url).path("/leser/eintrag/").path("{begriff}");
        
        EintragTOList eintragTOList = target.resolveTemplate("begriff", begriff)
        .request(MediaType.APPLICATION_JSON)
        .get(EintragTOList.class);

        List<EintragTO> ergebnisList = eintragTOList.inhalt();
        
        tabelle = new ArrayList<>();
        if (ergebnisList == null) {
            tabelle.add(new Tabelleneintrag (begriff, "nicht gefunden", "./."));
        }
        else {
            for (EintragTO eintragTO:ergebnisList)
                tabelle.add(new Tabelleneintrag(eintragTO.begriff(),eintragTO.definition(),eintragTO.autorname()));
        }
       
       return "/eintragSuchenRes.xhtml";
			
	}
	
}