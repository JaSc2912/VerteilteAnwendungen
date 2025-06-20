package de.hsnr.bank.usecases;

import de.hsnr.bank.dataaccess.BenutzerDAO;
import de.hsnr.bank.entities.Benutzer;
import de.hsnr.bank.usecases.Interfaces.IBenutzerSuchen;
import jakarta.ejb.Stateless;

import java.util.List;

/**
 *
 * @author jannn
 */
@Stateless
public class BenutzerSuchen implements IBenutzerSuchen {

    private BenutzerDAO benutzerDAO = new BenutzerDAO();

    @Override
    public List<Benutzer> searchBenutzer(String suchParameter) {
        return benutzerDAO.searchBenutzer(suchParameter);
    }
}
