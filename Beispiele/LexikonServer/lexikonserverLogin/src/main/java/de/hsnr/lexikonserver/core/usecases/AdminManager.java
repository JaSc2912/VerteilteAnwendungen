/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonserver.core.usecases;

import de.hsnr.lexikonserver.core.entities.Admin;
import de.hsnr.lexikonserver.dataaccess.AdminDAO;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;


/**
 *
 * @author aschekelmann
 */
@Stateless
public class AdminManager {
	
    @EJB AdminDAO adminDAO;
	
     
    public Admin adminSuchenPerPK(String kennung) {

        return adminDAO.suchen(kennung);
        
    }
    
     
}
