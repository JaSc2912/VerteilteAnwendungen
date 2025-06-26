/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonserver.facade;

import de.hsnr.lexikonserver.core.usecases.IAdminEinloggen;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import jakarta.security.enterprise.identitystore.IdentityStore;
import java.util.Set;

/**
 *
 * @author aschekelmann
 */
@Stateless
public class LexikonIdentityStore implements IdentityStore {
    /*
    Dies ist eine vereinfachte Implementierung eines Identity Stores mit einer Datenbank!
    
    In Jakarta EE gibt es dafür eigentlich den DatabaseIdentityStore
    
    Um die Komplexität zu reduzieren, verzichten wir hier darauf.
    Stattdessen verwalten wir Benutzer (hier nur den Admin) wie fachliche Daten
    
    */
    
    @EJB IAdminEinloggen adminEinloggen;
    
     public CredentialValidationResult validate(UsernamePasswordCredential usernamePasswordCredential) {
        
         String kennung = usernamePasswordCredential.getCaller();
         String secret = usernamePasswordCredential.getPasswordAsString();
         
         Boolean loginOK =  adminEinloggen.login(kennung, secret);
         
         if (loginOK)
             return new CredentialValidationResult(kennung, Set.of("admin"));
         else
             return INVALID_RESULT;
         
         
        
    }
}
