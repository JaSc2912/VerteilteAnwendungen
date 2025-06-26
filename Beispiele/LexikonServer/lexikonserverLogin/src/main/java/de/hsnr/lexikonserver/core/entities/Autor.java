/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonserver.core.entities;

/**
 *
 * @author aschekelmann
 */

public class Autor {
	
    private int autorid;
    private String name;
    
    public Autor() {
    }
	
    public Autor (int autorid, String name) {
        this.autorid = autorid;
        this.name = name;
    }

    public int getAutorid() {
            return autorid;
    }

    public String getName() {
            return name;
    }
    public void setName(String name) {
            this.name = name;
    }
	
	
	

}
