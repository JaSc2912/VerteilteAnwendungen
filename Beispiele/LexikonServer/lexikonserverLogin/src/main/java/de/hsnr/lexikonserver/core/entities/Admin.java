/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonserver.core.entities;

/**
 *
 * @author aschekelmann
 */
public class Admin {
    
    private String kennung;
    private String secret;
    
     public Admin() {
    }
	
    public Admin (String kennung, String secret) {
        this.kennung = kennung;
        this.secret = secret;
    }

    public String getKennung() {
            return kennung;
    }

    public String getSecret() {
            return secret;
    }
    public void setKennung(String kennung) {
            this.kennung = kennung;
    }
	
}
