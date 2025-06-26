/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonserver.core.usecases;

import de.hsnr.lexikonserver.core.entities.Autor;
import jakarta.ejb.Local;

/**
 *
 * @author aschekelmann
 */
@Local
public interface IAutorPflegen {
    public Autor autorHinzufuegen(String name);
}
