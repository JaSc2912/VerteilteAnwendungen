/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package de.hsnr.lexikonserver.core.usecases;

import jakarta.ejb.Local;

/**
 *
 * @author aschekelmann
 */
@Local
public interface IAdminEinloggen {
    
    public Boolean login(String kennung, String secret);
    
}
