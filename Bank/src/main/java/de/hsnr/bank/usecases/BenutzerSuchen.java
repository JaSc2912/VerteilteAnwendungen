package de.hsnr.bank.usecases;

import de.hsnr.bank.dataaccess.BenutzerDAO;
import de.hsnr.bank.entities.Benutzer;
import de.hsnr.bank.usecases.Interfaces.IBenutzerSuchen;
import java.util.List;

/**
 *
 * @author jannn
 */
public class BenutzerSuchen implements IBenutzerSuchen {

    private BenutzerDAO benutzerDAO = new BenutzerDAO();

    @Override
    public List<Benutzer> searchBenutzer(String suchParameter) {
        return benutzerDAO.searchBenutzer(suchParameter);
    }
}
