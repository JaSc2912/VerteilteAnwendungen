/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package de.hsnr.lexikonserver.core.usecases;

import de.hsnr.lexikonserver.core.entities.Autor;
import jakarta.ejb.Local;
import java.util.List;


/**
 *
 * @author aschekelmann
 */
@Local
public interface IAutorSuchen {
    public Autor autorSuchenPerPK (int id);
    public List<Autor> allAutor();
    
}
    
