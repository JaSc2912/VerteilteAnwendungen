package de.hsnr.bank.client.beans;

import de.hsnr.bank.client.model.Bankkonto;
import de.hsnr.bank.client.model.Transaktion;
import de.hsnr.bank.client.services.AccountRestClient;
import de.hsnr.bank.client.services.SessionService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * JSF managed bean for account operations
 */
@Named
@ViewScoped
public class AccountBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(AccountBean.class.getName());

    @Inject
    @RestClient
    private AccountRestClient accountRestClient;

    @Inject
    private SessionService sessionService;

    // Account management
    private List<Bankkonto> accounts = new ArrayList<>();
    private Bankkonto selectedAccount;
    private String newAccountType = "GIROKONTO";

    // Transaction operations
    private List<Transaktion> transactions = new ArrayList<>();
    private BigDecimal transactionAmount;
    private String transactionType = "EINZAHLUNG";

    // Transfer operations
    private String targetAccountNumber;
    private BigDecimal transferAmount;
    private String transferDescription;

    // UI state
    private boolean showCreateAccountDialog = false;
    private boolean showTransactionDialog = false;
    private boolean showTransferDialog = false;
    private boolean showTransactionHistory = false;

    @PostConstruct
    public void init() {
        loadAccounts();
    }

    /**
     * Load all accounts for current customer
     */
    public void loadAccounts() {
        try {
            if (sessionService.isLoggedIn()) {
                String currentCustomerId = sessionService.getCurrentUserId();
                Response response = accountRestClient.getAccountsByCustomer(
                        Long.parseLong(currentCustomerId),
                        sessionService.getAuthorizationHeader());

                if (response.getStatus() == 200) {
                    // In a real implementation, you would parse the response
                    // For now, we'll simulate with empty list
                    accounts = new ArrayList<>();
                    LOGGER.info("Accounts loaded successfully");
                } else {
                    showError("Fehler beim Laden der Konten");
                }
            }
        } catch (Exception e) {
            LOGGER.warning("Error loading accounts: " + e.getMessage());
            showError("Verbindungsfehler beim Laden der Konten");
        }
    }

    /**
     * Create a new account
     */
    public void createAccount() {
        try {
            if (sessionService.isLoggedIn()) {
                String currentCustomerId = sessionService.getCurrentUserId();
                AccountRestClient.CreateAccountRequest request = new AccountRestClient.CreateAccountRequest(
                        Long.parseLong(currentCustomerId), newAccountType);

                Response response = accountRestClient.createAccount(request, sessionService.getAuthorizationHeader());

                if (response.getStatus() == 201) {
                    showSuccess("Konto erfolgreich erstellt");
                    loadAccounts();
                    showCreateAccountDialog = false;
                    newAccountType = "GIROKONTO";
                } else {
                    showError("Fehler beim Erstellen des Kontos");
                }
            }
        } catch (Exception e) {
            LOGGER.warning("Error creating account: " + e.getMessage());
            showError("Verbindungsfehler beim Erstellen des Kontos");
        }
    }

    /**
     * Perform a transaction (deposit/withdrawal)
     */
    public void performTransaction() {
        try {
            if (selectedAccount != null && transactionAmount != null
                    && transactionAmount.compareTo(BigDecimal.ZERO) > 0) {
                AccountRestClient.TransactionRequest request = new AccountRestClient.TransactionRequest(
                        transactionAmount, transactionType);

                Response response = accountRestClient.performTransaction(
                        selectedAccount.getKontonummer(),
                        request,
                        sessionService.getAuthorizationHeader());

                if (response.getStatus() == 200) {
                    showSuccess("Transaktion erfolgreich durchgeführt");
                    loadAccounts();
                    loadTransactions(selectedAccount.getKontonummer());
                    showTransactionDialog = false;
                    transactionAmount = null;
                } else {
                    showError("Fehler bei der Transaktion");
                }
            } else {
                showError("Bitte geben Sie einen gültigen Betrag ein");
            }
        } catch (Exception e) {
            LOGGER.warning("Error performing transaction: " + e.getMessage());
            showError("Verbindungsfehler bei der Transaktion");
        }
    }

    /**
     * Transfer money between accounts
     */
    public void transferMoney() {
        try {
            if (selectedAccount != null && targetAccountNumber != null &&
                    transferAmount != null && transferAmount.compareTo(BigDecimal.ZERO) > 0) {

                AccountRestClient.TransferRequest request = new AccountRestClient.TransferRequest(targetAccountNumber,
                        transferAmount, transferDescription);

                Response response = accountRestClient.transferMoney(
                        selectedAccount.getKontonummer(),
                        request,
                        sessionService.getAuthorizationHeader());

                if (response.getStatus() == 200) {
                    showSuccess("Überweisung erfolgreich durchgeführt");
                    loadAccounts();
                    showTransferDialog = false;
                    targetAccountNumber = null;
                    transferAmount = null;
                    transferDescription = null;
                } else {
                    showError("Fehler bei der Überweisung");
                }
            } else {
                showError("Bitte füllen Sie alle Felder korrekt aus");
            }
        } catch (Exception e) {
            LOGGER.warning("Error transferring money: " + e.getMessage());
            showError("Verbindungsfehler bei der Überweisung");
        }
    }

    /**
     * Load transaction history for an account
     */
    public void loadTransactions(String accountNumber) {
        try {
            Response response = accountRestClient.getTransactions(accountNumber,
                    sessionService.getAuthorizationHeader());

            if (response.getStatus() == 200) {
                // In a real implementation, you would parse the response
                // For now, we'll simulate with empty list
                transactions = new ArrayList<>();
                showTransactionHistory = true;
                LOGGER.info("Transactions loaded successfully");
            } else {
                showError("Fehler beim Laden der Transaktionen");
            }
        } catch (Exception e) {
            LOGGER.warning("Error loading transactions: " + e.getMessage());
            showError("Verbindungsfehler beim Laden der Transaktionen");
        }
    }

    /**
     * Open create account dialog
     */
    public void openCreateAccountDialog() {
        showCreateAccountDialog = true;
    }

    /**
     * Open transaction dialog
     */
    public void openTransactionDialog() {
        if (selectedAccount != null) {
            showTransactionDialog = true;
        } else {
            showError("Bitte wählen Sie ein Konto aus");
        }
    }

    /**
     * Open transfer dialog
     */
    public void openTransferDialog() {
        if (selectedAccount != null) {
            showTransferDialog = true;
        } else {
            showError("Bitte wählen Sie ein Konto aus");
        }
    }

    /**
     * Close all dialogs
     */
    public void closeDialogs() {
        showCreateAccountDialog = false;
        showTransactionDialog = false;
        showTransferDialog = false;
        showTransactionHistory = false;
    }

    private void showSuccess(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg", message));
    }

    private void showError(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", message));
    }

    // Getters and setters
    public List<Bankkonto> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Bankkonto> accounts) {
        this.accounts = accounts;
    }

    public Bankkonto getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(Bankkonto selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

    public String getNewAccountType() {
        return newAccountType;
    }

    public void setNewAccountType(String newAccountType) {
        this.newAccountType = newAccountType;
    }

    public List<Transaktion> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaktion> transactions) {
        this.transactions = transactions;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTargetAccountNumber() {
        return targetAccountNumber;
    }

    public void setTargetAccountNumber(String targetAccountNumber) {
        this.targetAccountNumber = targetAccountNumber;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getTransferDescription() {
        return transferDescription;
    }

    public void setTransferDescription(String transferDescription) {
        this.transferDescription = transferDescription;
    }

    public boolean isShowCreateAccountDialog() {
        return showCreateAccountDialog;
    }

    public void setShowCreateAccountDialog(boolean showCreateAccountDialog) {
        this.showCreateAccountDialog = showCreateAccountDialog;
    }

    public boolean isShowTransactionDialog() {
        return showTransactionDialog;
    }

    public void setShowTransactionDialog(boolean showTransactionDialog) {
        this.showTransactionDialog = showTransactionDialog;
    }

    public boolean isShowTransferDialog() {
        return showTransferDialog;
    }

    public void setShowTransferDialog(boolean showTransferDialog) {
        this.showTransferDialog = showTransferDialog;
    }

    public boolean isShowTransactionHistory() {
        return showTransactionHistory;
    }

    public void setShowTransactionHistory(boolean showTransactionHistory) {
        this.showTransactionHistory = showTransactionHistory;
    }
}
