/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonserver.dataaccess;

import de.hsnr.lexikonserver.core.entities.Autor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author aschekelmann
 */
@Entity
public class AutorEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "autorid")
    @Column(name ="autornr")
    private int autorid;
    @NotNull
    private String name;

    public AutorEntity () {
    }

    public AutorEntity (Autor autor) {
        this.autorid = autor.getAutorid();
        this.name = autor.getName();
    }
    
    
    public int getAutorid() {
        return this.autorid;
    }

    public String getName() {
        return this.name;
    }

    public Autor toAutor() {
        return new Autor(this.autorid, this.name);

    }

    public void setName(String name) {
        this.name = name;
    }
	
}
