package de.hsnr.bankclient;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
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
 * Managed Bean für Konten-Übersicht
 */
@Named
@SessionScoped
public class KontenuebersichtBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private BankkontoBean bankkontoBean;

    private List<BankkontoTO> kontenListe = new ArrayList<>();
    private String serverUrl = "http://localhost:8080/BankServer/api";
    private String message;
    private boolean dataLoaded = false;

    // Getters and Setters
    public List<BankkontoTO> getKontenListe() {
        return kontenListe;
    }

    public String getMessage() {
        return message;
    }

    public boolean isDataLoaded() {
        return dataLoaded;
    }

    public int getAnzahlKonten() {
        return kontenListe != null ? kontenListe.size() : 0;
    }

    public Double getGesamtsaldo() {
        if (kontenListe == null || kontenListe.isEmpty()) {
            return 0.0;
        }
        return kontenListe.stream()
                .mapToDouble(konto -> konto.getKontostand() != null ? konto.getKontostand() : 0.0)
                .sum();
    }

    public Double getDurchschnittssaldo() {
        if (kontenListe == null || kontenListe.isEmpty()) {
            return 0.0;
        }
        return getGesamtsaldo() / kontenListe.size();
    }

    /**
     * Lädt alle Konten mit positivem Saldo
     */
    public String loadPositiveAccounts() {
        try {
            System.out.println("Loading accounts with positive balance");

            loadAllAccounts();

            if (!kontenListe.isEmpty()) {
                // Filter for positive balances only
                kontenListe = kontenListe.stream()
                        .filter(konto -> konto.getKontostand() != null && konto.getKontostand() > 0)
                        .toList();

                message = "Konten mit positivem Saldo geladen: " + kontenListe.size() + " Konten gefunden.";
                System.out.println("Filtered to " + kontenListe.size() + " accounts with positive balance");
            }

        } catch (Exception e) {
            message = "Fehler beim Laden der Konten: " + e.getMessage();
            System.out.println("Error loading positive accounts: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Stay on same page
    }

    /**
     * Lädt alle aktiven Konten
     */
    public String loadActiveAccounts() {
        try {
            System.out.println("Loading active accounts");

            loadAllAccounts();

            if (!kontenListe.isEmpty()) {
                // Filter for active accounts only
                kontenListe = kontenListe.stream()
                        .filter(konto -> "aktiv".equalsIgnoreCase(konto.getKontostatus()))
                        .toList();

                message = "Aktive Konten geladen: " + kontenListe.size() + " Konten gefunden.";
                System.out.println("Filtered to " + kontenListe.size() + " active accounts");
            }

        } catch (Exception e) {
            message = "Fehler beim Laden der aktiven Konten: " + e.getMessage();
            System.out.println("Error loading active accounts: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Stay on same page
    }

    /**
     * Aktualisiert die Kontenliste (lädt alle Konten)
     */
    public String refresh() {
        try {
            System.out.println("Refreshing all accounts");
            loadAllAccounts();
            message = "Alle Konten geladen: " + kontenListe.size() + " Konten gefunden.";

        } catch (Exception e) {
            message = "Fehler beim Aktualisieren der Konten: " + e.getMessage();
            System.out.println("Error refreshing accounts: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Stay on same page
    }

    /**
     * Zeigt Details für ein bestimmtes Konto (IBAN) - lädt die Daten in den
     * BankkontoBean und navigiert zur Verwaltungsseite
     */
    public String showDetails(String iban) {
        System.out.println("Showing details for account: " + iban);

        try {
            // Find the account in our current list
            BankkontoTO selectedAccount = kontenListe.stream()
                    .filter(konto -> iban.equals(konto.getIban()))
                    .findFirst()
                    .orElse(null);

            if (selectedAccount != null) {
                // Load the selected account data into the BankkontoBean
                bankkontoBean.setBankkonto(selectedAccount);
                bankkontoBean.setAccountLoadedMessage(iban);
                message = "Kontodaten für " + iban + " geladen.";
                System.out.println("Account data loaded into BankkontoBean for IBAN: " + iban);

                // Navigate to account management page
                return "/bankkonten.xhtml?faces-redirect=true";

            } else {
                message = "Konto mit IBAN " + iban + " nicht gefunden.";
                System.out.println("Account not found in current list: " + iban);
            }

        } catch (Exception e) {
            message = "Fehler beim Laden der Kontodaten: " + e.getMessage();
            System.out.println("Error loading account details: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Stay on same page if error
    }

    /**
     * Private helper method to load all accounts from REST API
     */
    private void loadAllAccounts() {
        kontenListe.clear();
        dataLoaded = false;
        message = "Lade Kontendaten...";

        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/bankkonten");

            Response response = target.request(MediaType.APPLICATION_JSON).get();

            if (response.getStatus() == 200) {
                kontenListe = response.readEntity(new GenericType<List<BankkontoTO>>() {
                });
                System.out.println("Retrieved " + kontenListe.size() + " accounts from server");
                dataLoaded = true;

            } else {
                message = "Fehler beim Abrufen der Kontendaten: " + response.getStatus();
                System.out
                        .println("Error response: " + response.getStatus() + " - " + response.readEntity(String.class));
            }

            client.close();

        } catch (Exception e) {
            message = "Fehler beim Laden der Konten: " + e.getMessage();
            System.out.println("Exception during account loading: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
