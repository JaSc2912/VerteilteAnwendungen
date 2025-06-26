/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonserver.dataaccess;


import de.hsnr.lexikonserver.core.entities.Admin;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author aschekelmann
 */
@Entity
public class AdminEntity {
    
    @Id
    private String kennung;
    @NotNull
    private String secret;

    public AdminEntity () {
    }
    
    public AdminEntity (Admin admin) {
        this.kennung = admin.getKennung();
        this.secret = admin.getSecret();
    }
    
    public Admin toAdmin() {
        return new Admin(this.kennung, this.secret);

    }
      
    public String getKennung() {
        return this.kennung;
    }
    
    public String getSecret() {
        return this.secret;
    }

    
    public void setKennung(String kennung) {
        this.kennung = kennung;
    }
    
    public void setSecret(String secret) {
        this.secret = secret;
    }
	
}
