package de.hsnr.bankserver.core.usecases;

import de.hsnr.bankserver.core.entities.Bankkonto;
import de.hsnr.bankserver.dataaccess.dao.BankkontoDAO;
import de.hsnr.bankserver.dataaccess.entities.BankkontoEntity;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Use Case für Bankkonten verwalten
 */
@Stateless
public class BankkontenVerwalten {
    
    @Inject
    private BankkontoDAO bankkontoDAO;
    
    public void addBankkonto(Bankkonto bankkonto) {
        BankkontoEntity entity = mapToEntity(bankkonto);
        bankkontoDAO.save(entity);
    }
    
    public void deleteBankkonto(String iban) throws Exception {
        // Prüfung auf aktive Transaktionen
        if (bankkontoDAO.hasActiveTransactions(iban)) {
            throw new Exception("Konto kann nicht gelöscht werden - aktive Transaktionen vorhanden");
        }
        bankkontoDAO.delete(iban);
    }
    
    public void editBankkonto(Bankkonto bankkonto) {
        BankkontoEntity entity = mapToEntity(bankkonto);
        bankkontoDAO.update(entity);
    }
    
    public List<Bankkonto> getAllBankkonten() {
        List<BankkontoEntity> entities = bankkontoDAO.findAll();
        List<Bankkonto> bankkonten = new ArrayList<>();
        for (BankkontoEntity entity : entities) {
            bankkonten.add(mapToBusinessObject(entity));
        }
        return bankkonten;
    }
    
    public List<Bankkonto> getActiveAccountsWithPositiveBalance() {
        List<BankkontoEntity> entities = bankkontoDAO.findActiveAccountsWithPositiveBalance();
        List<Bankkonto> bankkonten = new ArrayList<>();
        for (BankkontoEntity entity : entities) {
            bankkonten.add(mapToBusinessObject(entity));
        }
        return bankkonten;
    }
    
    public Bankkonto getBankkonto(String iban) {
        BankkontoEntity entity = bankkontoDAO.findByIban(iban);
        return entity != null ? mapToBusinessObject(entity) : null;
    }
    
    private Bankkonto mapToBusinessObject(BankkontoEntity entity) {
        return new Bankkonto(entity.getIban(), entity.getKontoinhaber(), entity.getKontoart(),
                             entity.getKontostand(), entity.getKontoeröffnungsdatum(), entity.getKontostatus());
    }
    
    private BankkontoEntity mapToEntity(Bankkonto bankkonto) {
        return new BankkontoEntity(bankkonto.getIban(), bankkonto.getKontoinhaber(), bankkonto.getKontoart(),
                                   bankkonto.getKontostand(), bankkonto.getKontoeröffnungsdatum(), bankkonto.getKontostatus());
    }
}
