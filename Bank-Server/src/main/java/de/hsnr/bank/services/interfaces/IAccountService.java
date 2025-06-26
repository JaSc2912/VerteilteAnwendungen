package de.hsnr.bank.services.interfaces;

import de.hsnr.bank.entities.Bankkonto;
import de.hsnr.bank.entities.Transaktion;
import jakarta.ejb.Local;
import java.math.BigDecimal;
import java.util.List;

/**
 * Account service interface
 */
@Local
public interface IAccountService {

    /**
     * Find account by IBAN
     * 
     * @param iban account IBAN
     * @return account or null if not found
     */
    Bankkonto findByIban(String iban);

    /**
     * Get all accounts for customer
     * 
     * @param kundennummer customer number
     * @return list of customer accounts
     */
    List<Bankkonto> getAccountsByCustomer(Long kundennummer);

    /**
     * Create new account
     * 
     * @param bankkonto account to create
     * @return created account
     */
    Bankkonto createAccount(Bankkonto bankkonto);

    /**
     * Update account
     * 
     * @param bankkonto account to update
     * @return updated account
     */
    Bankkonto updateAccount(Bankkonto bankkonto);

    /**
     * Get account balance
     * 
     * @param iban account IBAN
     * @return current balance
     */
    BigDecimal getBalance(String iban);

    /**
     * Get transactions for account
     * 
     * @param iban account IBAN
     * @return list of transactions
     */
    List<Transaktion> getTransactions(String iban);

    /**
     * Transfer money between accounts
     * 
     * @param fromIban         source account IBAN
     * @param toIban           destination account IBAN
     * @param amount           transfer amount
     * @param verwendungszweck purpose of transfer
     * @return transfer transaction
     */
    Transaktion transferMoney(String fromIban, String toIban, BigDecimal amount, String verwendungszweck);

    /**
     * Deposit money to account
     * 
     * @param iban   account IBAN
     * @param amount deposit amount
     * @return deposit transaction
     */
    Transaktion depositMoney(String iban, BigDecimal amount);

    /**
     * Withdraw money from account
     * 
     * @param iban   account IBAN
     * @param amount withdrawal amount
     * @return withdrawal transaction
     */
    Transaktion withdrawMoney(String iban, BigDecimal amount);
}
