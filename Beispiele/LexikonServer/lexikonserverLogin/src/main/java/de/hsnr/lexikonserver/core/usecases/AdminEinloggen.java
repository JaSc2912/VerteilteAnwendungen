/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonserver.core.usecases;

import de.hsnr.lexikonserver.core.entities.Admin;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

/**
 *
 * @author aschekelmann
 */
@Stateless
public class AdminEinloggen implements IAdminEinloggen{

    @EJB AdminManager adminManager;
    @Override
    public Boolean login(String kennung, String secret) {
        Admin admin = adminManager.adminSuchenPerPK(kennung);
        
        if (admin == null)
            return false;
        
        return admin.getSecret().equals(secret);
        
    }
    
}
