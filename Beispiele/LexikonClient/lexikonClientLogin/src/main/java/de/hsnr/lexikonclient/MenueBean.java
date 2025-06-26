/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonclient;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author aschekelmann
 */
@Named
@Dependent
public class MenueBean implements Serializable{
    private static final long serialVersionUID = -211073631543658209L;

    
    public String suchen() {
        return "/eintragSuchenReq.xhtml";
    }
    
    public String hinzufuegen() {
        
        String kennung;
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        kennung = (String) session.getAttribute("kennung");
        
        if (kennung == null)
            return "/loginReq.xhtml";
        else 
            return "/eintragHinzufuegenReq.xhtml";
            
    }
         
}