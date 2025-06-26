/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonserver.core.usecases;

import de.hsnr.lexikonserver.core.entities.Autor;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class AutorPflegen implements IAutorPflegen {

    @EJB AutorManager autorManager;
    @Override
    public Autor autorHinzufuegen (String name) {
        
        // diverse fachliche Prüfungen hätten hier Platz :-)
                
        Autor autor = new Autor();
        autor.setName(name);
        
        return autorManager.autorHinzufuegen(autor);

        
                
    }
    
}
