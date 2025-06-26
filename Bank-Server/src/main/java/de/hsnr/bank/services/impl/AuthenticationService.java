package de.hsnr.bank.services.impl;

import de.hsnr.bank.entities.Benutzer;
import de.hsnr.bank.entities.RolleT;
import de.hsnr.bank.services.interfaces.IAuthenticationService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.logging.Logger;

/**
 * Authentication service EJB implementation
 */
@Stateless
public class AuthenticationService implements IAuthenticationService {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationService.class.getName());

    @PersistenceContext(unitName = "BankPU")
    private EntityManager em;

    @Override
    public Benutzer login(String benutzername, String passwort) throws SecurityException {
        LOGGER.info("Attempting login for user: " + benutzername);

        try {
            TypedQuery<Benutzer> query = em.createNamedQuery("Benutzer.findByUsername", Benutzer.class);
            query.setParameter("benutzername", benutzername);
            Benutzer benutzer = query.getSingleResult();

            // Simple password check (in production, use proper hashing)
            if (benutzer.getPasswort().equals(passwort)) {
                LOGGER.info("Login successful for user: " + benutzername);
                return benutzer;
            } else {
                LOGGER.warning("Invalid password for user: " + benutzername);
                throw new SecurityException("Ungültiger Benutzername oder Passwort");
            }
        } catch (NoResultException e) {
            LOGGER.warning("User not found: " + benutzername);
            throw new SecurityException("Ungültiger Benutzername oder Passwort");
        }
    }

    @Override
    public Benutzer validateToken(String token) throws SecurityException {
        // For simplicity, we'll use username as token
        // In production, implement proper JWT or session token validation
        try {
            TypedQuery<Benutzer> query = em.createNamedQuery("Benutzer.findByUsername", Benutzer.class);
            query.setParameter("benutzername", token);
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new SecurityException("Invalid token");
        }
    }

    @Override
    public boolean hasRole(Benutzer benutzer, String requiredRole) {
        if (benutzer == null || benutzer.getRolle() == null) {
            return false;
        }

        RolleT userRole = benutzer.getRolle();

        // Admin has access to everything
        if (userRole == RolleT.ADMIN) {
            return true;
        }

        // Check specific role
        try {
            RolleT required = RolleT.valueOf(requiredRole.toUpperCase());
            return userRole == required;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
