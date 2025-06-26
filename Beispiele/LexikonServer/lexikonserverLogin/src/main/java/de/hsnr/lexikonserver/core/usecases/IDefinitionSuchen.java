/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package de.hsnr.lexikonserver.core.usecases;

import de.hsnr.lexikonserver.core.entities.Eintrag;

import jakarta.ejb.Local;

import java.util.List;

/**
 *
 * @author aschekelmann
 */

@Local
public interface IDefinitionSuchen {
    public List <Eintrag> suchen(String begriff);
}
