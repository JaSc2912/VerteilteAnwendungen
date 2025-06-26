package de.hsnr.bank.services.interfaces;

import de.hsnr.bank.entities.Benutzer;
import jakarta.ejb.Local;

/**
 * Authentication service interface
 */
@Local
public interface IAuthenticationService {

    /**
     * Authenticate user with username and password
     * 
     * @param benutzername username
     * @param passwort     password
     * @return authenticated user
     * @throws SecurityException if authentication fails
     */
    Benutzer login(String benutzername, String passwort) throws SecurityException;

    /**
     * Validate user session token
     * 
     * @param token session token
     * @return user if token is valid
     * @throws SecurityException if token is invalid
     */
    Benutzer validateToken(String token) throws SecurityException;

    /**
     * Check if user has required role
     * 
     * @param benutzer     user to check
     * @param requiredRole required role
     * @return true if user has role
     */
    boolean hasRole(Benutzer benutzer, String requiredRole);
}
