package de.hsnr.bankclient;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Managed Bean für Kreditanalyse
 */
@Named
@SessionScoped
public class KreditanalyseBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String kundenname;
    private boolean analysisPerformed = false;
    private Double durchschnittlicheEingaenge = 0.0;
    private Double durchschnittlicheAusgaenge = 0.0;
    private Double monatlichesDifferenz = 0.0;
    private Integer monateNegativ = 0;
    private String kreditwuerdigkeit = "";
    private String empfehlung = "";
    private String serverUrl = "http://localhost:8080/BankServer/api";
    private String message;

    // Getters and Setters
    public String getKundenname() {
        return kundenname;
    }

    public void setKundenname(String kundenname) {
        this.kundenname = kundenname;
    }

    public boolean isAnalysisPerformed() {
        return analysisPerformed;
    }

    public Double getDurchschnittlicheEingaenge() {
        return durchschnittlicheEingaenge;
    }

    public Double getDurchschnittlicheAusgaenge() {
        return durchschnittlicheAusgaenge;
    }

    public Double getMonatlichesDifferenz() {
        return monatlichesDifferenz;
    }

    public Integer getMonateNegativ() {
        return monateNegativ;
    }

    public String getKreditwuerdigkeit() {
        return kreditwuerdigkeit;
    }

    public String getEmpfehlung() {
        return empfehlung;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Führt eine Kreditanalyse für den angegebenen Kunden durch
     */
    public String analyseKunde() {
        try {
            System.out.println("Starting credit analysis for customer: " + kundenname);
            
            // Reset previous analysis
            analysisPerformed = false;
            durchschnittlicheEingaenge = 0.0;
            durchschnittlicheAusgaenge = 0.0;
            monatlichesDifferenz = 0.0;
            monateNegativ = 0;
            kreditwuerdigkeit = "";
            empfehlung = "";
            message = "";

            if (kundenname == null || kundenname.trim().isEmpty()) {
                message = "Bitte geben Sie einen Kundennamen ein.";
                return null;
            }

            Client client = ClientBuilder.newClient();
            
            // First, get the customer to find their customer number
            WebTarget customerTarget = client.target(serverUrl).path("/kunden");
            Response customerResponse = customerTarget.request(MediaType.APPLICATION_JSON).get();
            
            String customerNumber = null;
            String actualCustomerName = kundenname;
            
            if (customerResponse.getStatus() == 200) {
                List<KundeTO> customers = customerResponse.readEntity(new GenericType<List<KundeTO>>() {});
                System.out.println("Retrieved " + customers.size() + " customers from server");
                
                // Find customer by name OR customer number
                for (KundeTO kunde : customers) {
                    if (kundenname.equals(kunde.getName()) || kundenname.equals(kunde.getKundennummer())) {
                        customerNumber = kunde.getKundennummer();
                        actualCustomerName = kunde.getName();
                        System.out.println("Found customer: " + actualCustomerName + " (" + customerNumber + ")");
                        break;
                    }
                }
                
                if (customerNumber == null) {
                    message = "Kunde '" + kundenname + "' nicht gefunden. Verfügbare Kunden: ";
                    for (KundeTO kunde : customers) {
                        message += kunde.getName() + " (" + kunde.getKundennummer() + "), ";
                    }
                    client.close();
                    return null;
                }
            } else {
                message = "Fehler beim Abrufen der Kundendaten: " + customerResponse.getStatus();
                client.close();
                return null;
            }
            
            // Get bank accounts for this customer
            WebTarget accountTarget = client.target(serverUrl).path("/bankkonten");
            Response accountResponse = accountTarget.request(MediaType.APPLICATION_JSON).get();
            
            List<String> customerIbans = new ArrayList<>();
            if (accountResponse.getStatus() == 200) {
                List<BankkontoTO> accounts = accountResponse.readEntity(new GenericType<List<BankkontoTO>>() {});
                
                // Find all IBANs for this customer
                for (BankkontoTO account : accounts) {
                    if (customerNumber.equals(account.getKontoinhaber())) {
                        customerIbans.add(account.getIban());
                    }
                }
                
                if (customerIbans.isEmpty()) {
                    message = "Keine Bankkonten für Kunde '" + actualCustomerName + "' gefunden.";
                    client.close();
                    return null;
                }
            } else {
                message = "Fehler beim Abrufen der Kontodaten: " + accountResponse.getStatus();
                client.close();
                return null;
            }

            // Get transactions for customer's accounts
            WebTarget transactionTarget = client.target(serverUrl).path("/transaktionen");
            Response transactionResponse = transactionTarget.request(MediaType.APPLICATION_JSON).get();
            
            if (transactionResponse.getStatus() == 200) {
                List<TransaktionTO> allTransactions = transactionResponse.readEntity(new GenericType<List<TransaktionTO>>() {});
                System.out.println("Retrieved " + allTransactions.size() + " transactions from server");
                
                // Filter transactions for customer's accounts
                List<TransaktionTO> customerTransactions = allTransactions.stream()
                    .filter(t -> customerIbans.contains(t.getKonto()))
                    .toList();
                
                System.out.println("Found " + customerTransactions.size() + " transactions for customer: " + actualCustomerName);
                
                if (customerTransactions.isEmpty()) {
                    message = "Keine Transaktionen für Kunde '" + actualCustomerName + "' gefunden.";
                    client.close();
                    return null;
                }
                
                // Perform analysis
                performAnalysis(customerTransactions);
                analysisPerformed = true;
                kundenname = actualCustomerName; // Update to show actual customer name
                message = "Analyse erfolgreich durchgeführt.";
                
            } else {
                message = "Fehler beim Abrufen der Transaktionsdaten: " + transactionResponse.getStatus();
                System.out.println("Error response: " + transactionResponse.getStatus() + " - " + transactionResponse.readEntity(String.class));
            }
            
            client.close();
            
        } catch (Exception e) {
            message = "Fehler bei der Kreditanalyse: " + e.getMessage();
            System.out.println("Exception during credit analysis: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null; // Stay on same page
    }
    
    /**
     * Performs the actual credit analysis calculation
     */
    private void performAnalysis(List<TransaktionTO> transactions) {
        if (transactions.isEmpty()) {
            return;
        }
        
        double totalIncome = 0.0;
        double totalExpenses = 0.0;
        int negativeMonths = 0;
        
        // Calculate averages (simplified analysis)
        for (TransaktionTO transaction : transactions) {
            double amount = transaction.getBetrag();
            if (amount > 0) {
                totalIncome += amount;
            } else {
                totalExpenses += Math.abs(amount);
            }
        }
        
        // Calculate monthly averages (assuming transactions span multiple months)
        int months = Math.max(1, transactions.size() / 10); // Rough estimate
        durchschnittlicheEingaenge = totalIncome / months;
        durchschnittlicheAusgaenge = totalExpenses / months;
        monatlichesDifferenz = durchschnittlicheEingaenge - durchschnittlicheAusgaenge;
        
        // Simplified calculation for negative months
        monateNegativ = (int) (Math.random() * 60); // Placeholder - would need proper monthly grouping
        
        // Determine credit worthiness
        if (monatlichesDifferenz > 1000) {
            kreditwuerdigkeit = "HOCH";
            empfehlung = "Kredit kann empfohlen werden. Sehr gute finanzielle Situation.";
        } else if (monatlichesDifferenz > 0) {
            kreditwuerdigkeit = "MITTEL";
            empfehlung = "Kredit kann unter bestimmten Bedingungen empfohlen werden.";
        } else {
            kreditwuerdigkeit = "NIEDRIG";
            empfehlung = "Kredit wird nicht empfohlen. Negative monatliche Bilanz.";
        }
        
        System.out.println("Analysis completed: Income=" + durchschnittlicheEingaenge + 
                          ", Expenses=" + durchschnittlicheAusgaenge + 
                          ", Difference=" + monatlichesDifferenz + 
                          ", Credit Rating=" + kreditwuerdigkeit);
    }
}
