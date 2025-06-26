package de.hsnr.bankserver.core.usecases;

import de.hsnr.bankserver.dataaccess.dao.TransaktionDAO;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.Calendar;

/**
 * Use Case für Kunden auswerten
 */
@Stateless
public class KundenAuswerten {
    
    @Inject
    private TransaktionDAO transaktionDAO;
    
    public Double calculateMonthlyIncome(String konto, int year, int month) {
        return transaktionDAO.calculateMonthlyIncome(konto, year, month);
    }
    
    public Double calculateMonthlyOutcome(String konto, int year, int month) {
        return transaktionDAO.calculateMonthlyOutcome(konto, year, month);
    }
    
    public int countNegativeBalanceMonths(String konto) {
        // Vereinfachte Implementierung - in der Realität würde man historische Kontostände auswerten
        // Hier nehmen wir an, dass wir die letzten 60 Monate (5 Jahre) betrachten
        int negativeMonths = 0;
        Calendar cal = Calendar.getInstance();
        
        for (int i = 0; i < 60; i++) {
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            
            Double income = calculateMonthlyIncome(konto, year, month);
            Double outcome = calculateMonthlyOutcome(konto, year, month);
            
            if (income - outcome < 0) {
                negativeMonths++;
            }
            
            cal.add(Calendar.MONTH, -1);
        }
        
        return negativeMonths;
    }
}
