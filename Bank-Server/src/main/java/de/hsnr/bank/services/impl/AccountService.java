package de.hsnr.bank.services.impl;

import de.hsnr.bank.entities.Bankkonto;
import de.hsnr.bank.entities.Kunde;
import de.hsnr.bank.entities.Transaktion;
import de.hsnr.bank.services.interfaces.IAccountService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

/**
 * Account service EJB implementation
 */
@Stateless
public class AccountService implements IAccountService {

    private static final Logger LOGGER = Logger.getLogger(AccountService.class.getName());

    @PersistenceContext(unitName = "BankPU")
    private EntityManager em;

    @Override
    public Bankkonto findByIban(String iban) {
        LOGGER.info("Finding account by IBAN: " + iban);
        try {
            return em.find(Bankkonto.class, iban);
        } catch (Exception e) {
            LOGGER.warning("Error finding account: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Bankkonto> getAccountsByCustomer(Long kundennummer) {
        LOGGER.info("Getting accounts for customer: " + kundennummer);
        try {
            TypedQuery<Bankkonto> query = em.createNamedQuery("Bankkonto.findByKundennummer", Bankkonto.class);
            query.setParameter("kundennummer", kundennummer);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.warning("Error getting customer accounts: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public Bankkonto createAccount(Bankkonto bankkonto) {
        LOGGER.info("Creating new account: " + bankkonto.getIban());
        try {
            bankkonto.setKontostatus("AKTIV");
            if (bankkonto.getKontostand() == null) {
                bankkonto.setKontostand(BigDecimal.ZERO);
            }
            if (bankkonto.getKreditlimit() == null) {
                bankkonto.setKreditlimit(BigDecimal.ZERO);
            }

            em.persist(bankkonto);
            em.flush();
            LOGGER.info("Account created successfully");
            return bankkonto;
        } catch (Exception e) {
            LOGGER.severe("Error creating account: " + e.getMessage());
            throw new RuntimeException("Failed to create account", e);
        }
    }

    @Override
    public Bankkonto updateAccount(Bankkonto bankkonto) {
        LOGGER.info("Updating account: " + bankkonto.getIban());
        try {
            Bankkonto existingAccount = em.find(Bankkonto.class, bankkonto.getIban());
            if (existingAccount == null) {
                throw new RuntimeException("Account not found: " + bankkonto.getIban());
            }

            // Update fields
            existingAccount.setKontostand(bankkonto.getKontostand());
            existingAccount.setKontostatus(bankkonto.getKontostatus());
            existingAccount.setKreditlimit(bankkonto.getKreditlimit());

            em.merge(existingAccount);
            em.flush();
            LOGGER.info("Account updated successfully");
            return existingAccount;
        } catch (Exception e) {
            LOGGER.severe("Error updating account: " + e.getMessage());
            throw new RuntimeException("Failed to update account", e);
        }
    }

    @Override
    public BigDecimal getBalance(String iban) {
        LOGGER.info("Getting balance for account: " + iban);
        try {
            Bankkonto account = findByIban(iban);
            return account != null ? account.getKontostand() : BigDecimal.ZERO;
        } catch (Exception e) {
            LOGGER.warning("Error getting balance: " + e.getMessage());
            return BigDecimal.ZERO;
        }
    }

    @Override
    public List<Transaktion> getTransactions(String iban) {
        LOGGER.info("Getting transactions for account: " + iban);
        try {
            TypedQuery<Transaktion> query = em.createNamedQuery("Transaktion.findByIban", Transaktion.class);
            query.setParameter("iban", iban);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.warning("Error getting transactions: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public Transaktion transferMoney(String fromIban, String toIban, BigDecimal amount, String verwendungszweck) {
        LOGGER.info("Transferring " + amount + " from " + fromIban + " to " + toIban);
        try {
            // Validate inputs
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Transfer amount must be positive");
            }

            Bankkonto fromAccount = findByIban(fromIban);
            Bankkonto toAccount = findByIban(toIban);

            if (fromAccount == null || toAccount == null) {
                throw new RuntimeException("One or both accounts not found");
            }

            if (!"AKTIV".equals(fromAccount.getKontostatus()) || !"AKTIV".equals(toAccount.getKontostatus())) {
                throw new RuntimeException("One or both accounts are not active");
            }

            // Check sufficient funds (including credit limit)
            BigDecimal availableAmount = fromAccount.getKontostand().add(fromAccount.getKreditlimit());
            if (availableAmount.compareTo(amount) < 0) {
                throw new RuntimeException("Insufficient funds for transfer");
            }

            // Perform transfer
            fromAccount.setKontostand(fromAccount.getKontostand().subtract(amount));
            toAccount.setKontostand(toAccount.getKontostand().add(amount));

            em.merge(fromAccount);
            em.merge(toAccount);

            // Create transaction record for sender
            Transaktion transaction = new Transaktion();
            transaction.setBetrag(amount.negate()); // Negative for outgoing
            transaction.setTransaktionsart("UEBERWEISUNG");
            transaction.setEmpfaenger(toIban);
            transaction.setVerwendungszweck(verwendungszweck);
            transaction.setBankkonto(fromAccount);
            transaction.setTransaktionsdatum(LocalDateTime.now());
            transaction.setTransaktionsstatus("BEARBEITET");

            em.persist(transaction);

            // Create transaction record for receiver
            Transaktion receiverTransaction = new Transaktion();
            receiverTransaction.setBetrag(amount); // Positive for incoming
            receiverTransaction.setTransaktionsart("UEBERWEISUNG_EINGANG");
            receiverTransaction.setEmpfaenger(fromIban);
            receiverTransaction.setVerwendungszweck(verwendungszweck);
            receiverTransaction.setBankkonto(toAccount);
            receiverTransaction.setTransaktionsdatum(LocalDateTime.now());
            receiverTransaction.setTransaktionsstatus("BEARBEITET");

            em.persist(receiverTransaction);
            em.flush();

            LOGGER.info("Transfer completed successfully");
            return transaction;
        } catch (Exception e) {
            LOGGER.severe("Error during transfer: " + e.getMessage());
            throw new RuntimeException("Transfer failed: " + e.getMessage(), e);
        }
    }

    @Override
    public Transaktion depositMoney(String iban, BigDecimal amount) {
        LOGGER.info("Depositing " + amount + " to account: " + iban);
        try {
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Deposit amount must be positive");
            }

            Bankkonto account = findByIban(iban);
            if (account == null) {
                throw new RuntimeException("Account not found: " + iban);
            }

            if (!"AKTIV".equals(account.getKontostatus())) {
                throw new RuntimeException("Account is not active");
            }

            // Update balance
            account.setKontostand(account.getKontostand().add(amount));
            em.merge(account);

            // Create transaction record
            Transaktion transaction = new Transaktion();
            transaction.setBetrag(amount);
            transaction.setTransaktionsart("EINZAHLUNG");
            transaction.setEmpfaenger("Einzahlung");
            transaction.setVerwendungszweck("Bargeldeinzahlung");
            transaction.setBankkonto(account);
            transaction.setTransaktionsdatum(LocalDateTime.now());
            transaction.setTransaktionsstatus("BEARBEITET");

            em.persist(transaction);
            em.flush();

            LOGGER.info("Deposit completed successfully");
            return transaction;
        } catch (Exception e) {
            LOGGER.severe("Error during deposit: " + e.getMessage());
            throw new RuntimeException("Deposit failed: " + e.getMessage(), e);
        }
    }

    @Override
    public Transaktion withdrawMoney(String iban, BigDecimal amount) {
        LOGGER.info("Withdrawing " + amount + " from account: " + iban);
        try {
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Withdrawal amount must be positive");
            }

            Bankkonto account = findByIban(iban);
            if (account == null) {
                throw new RuntimeException("Account not found: " + iban);
            }

            if (!"AKTIV".equals(account.getKontostatus())) {
                throw new RuntimeException("Account is not active");
            }

            // Check sufficient funds (including credit limit)
            BigDecimal availableAmount = account.getKontostand().add(account.getKreditlimit());
            if (availableAmount.compareTo(amount) < 0) {
                throw new RuntimeException("Insufficient funds for withdrawal");
            }

            // Update balance
            account.setKontostand(account.getKontostand().subtract(amount));
            em.merge(account);

            // Create transaction record
            Transaktion transaction = new Transaktion();
            transaction.setBetrag(amount.negate()); // Negative for outgoing
            transaction.setTransaktionsart("ABHEBUNG");
            transaction.setEmpfaenger("Bargeld");
            transaction.setVerwendungszweck("Bargeldabhebung");
            transaction.setBankkonto(account);
            transaction.setTransaktionsdatum(LocalDateTime.now());
            transaction.setTransaktionsstatus("BEARBEITET");

            em.persist(transaction);
            em.flush();

            LOGGER.info("Withdrawal completed successfully");
            return transaction;
        } catch (Exception e) {
            LOGGER.severe("Error during withdrawal: " + e.getMessage());
            throw new RuntimeException("Withdrawal failed: " + e.getMessage(), e);
        }
    }
}
