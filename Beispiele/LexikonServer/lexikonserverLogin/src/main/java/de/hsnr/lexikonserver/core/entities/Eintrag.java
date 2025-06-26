/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonserver.core.entities;

/**
 *
 * @author aschekelmann
 */

public class Eintrag {

    private int eintragid;
    private String begriff;
    private String definition;
    private Autor autor;

    public Eintrag() {
    }
	
    public Eintrag (int id, String begriff, String definition, Autor autor) {
        this.eintragid = id;
        this.begriff = begriff;
        this.definition = definition;
        this.autor = autor;
    }
        
    public int getEintragid() {
        return this.eintragid;
    }
    
    public String getBegriff() {
        return this.begriff;
    }
    
    public String getDefinition() {
        return this.definition;
    }
    
    public Autor getAutor() {
    return this.autor;
    }

    public void setBegriff(String begriff) {
        this.begriff = begriff;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    
    

}
