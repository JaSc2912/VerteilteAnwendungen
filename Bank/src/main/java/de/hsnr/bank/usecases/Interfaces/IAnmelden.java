package de.hsnr.bank.usecases.Interfaces;

import de.hsnr.bank.entities.Benutzer;
import jakarta.ejb.Local;

/**
 *
 * @author jannn
 */
@Local
public interface IAnmelden {
    Benutzer login(String benutzername, String passwort);
}
