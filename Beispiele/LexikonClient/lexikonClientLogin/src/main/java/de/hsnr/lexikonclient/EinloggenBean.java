/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonclient;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import java.io.Serializable;

/**
 *
 * @author aschekelmann
 */
@Named
@SessionScoped
//@RequestScoped
public class EinloggenBean implements Serializable{
    private static final long serialVersionUID = -211073631543658209L;

   
    private String kennung;
    private String secret;
    String url = "http://localhost:8080/lexikonserverLogin/restapi";
    
    
 
    public String getKennung() {
            return kennung;
    }
    public void setKennung(String kennung) {
            this.kennung = kennung;
    }
    public String getSecret() {
            return secret;
    }
    public void setSecret(String secret) {
            this.secret = secret;
    }
    
   
    public String einloggen() {
 
        Client client = ClientBuilder.newClient();       
        WebTarget target = client.target(url).path("/login/").path("{kennung}/").path("{secret}");
      
        String loginok = 
                target.resolveTemplate("kennung", kennung).resolveTemplate("secret", secret)
                        .request(MediaType.TEXT_PLAIN)
                        .get(String.class);
         
        
        if (loginok.equals("true")) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            session.setAttribute("kennung", kennung);    
            session.setAttribute("secret", secret); 
     
            return "/eintragHinzufuegenReq.xhtml";
        }
        else
            return "/loginErrorRes.xhtml";
            
       
    }
        
}