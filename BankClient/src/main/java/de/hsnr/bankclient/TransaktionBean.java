package de.hsnr.bankclient;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Managed Bean für Transaktionen verwalten
 */
@Named
@SessionScoped
public class TransaktionBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private TransaktionTO transaktion = new TransaktionTO();
    private List<TransaktionTO> transaktionenListe = new ArrayList<>();
    private String serverUrl = "http://localhost:8080/BankServer/api";
    private String message;

    // Getters and Setters
    public TransaktionTO getTransaktion() {
        return transaktion;
    }

    public void setTransaktion(TransaktionTO transaktion) {
        this.transaktion = transaktion;
    }

    public List<TransaktionTO> getTransaktionenListe() {
        return transaktionenListe;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Fügt eine neue Transaktion hinzu
     */
    public String addTransaktion() {
        try {
            System.out.println("Adding new transaction: " + transaktion.getTransaktionsnummer());

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/transaktionen");

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(transaktion, MediaType.APPLICATION_JSON));

            if (response.getStatus() == 200 || response.getStatus() == 201) {
                message = "Transaktion erfolgreich erstellt.";
                transaktion = new TransaktionTO(); // Reset form
                loadAllTransaktionen(); // Refresh list
            } else {
                message = "Fehler beim Erstellen der Transaktion: " + response.getStatus();
                System.out
                        .println("Error response: " + response.getStatus() + " - " + response.readEntity(String.class));
            }

            client.close();

        } catch (Exception e) {
            message = "Fehler beim Hinzufügen der Transaktion: " + e.getMessage();
            System.out.println("Exception during transaction creation: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Stay on same page
    }

    /**
     * Bearbeitet eine existierende Transaktion
     */
    public String editTransaktion() {
        try {
            System.out.println("Editing transaction: " + transaktion.getTransaktionsnummer());

            if (transaktion.getTransaktionsnummer() == null || transaktion.getTransaktionsnummer().trim().isEmpty()) {
                message = "Bitte geben Sie eine Transaktionsnummer für die Bearbeitung an.";
                return null;
            }

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/transaktionen")
                    .path(transaktion.getTransaktionsnummer());

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .put(Entity.entity(transaktion, MediaType.APPLICATION_JSON));

            if (response.getStatus() == 200) {
                message = "Transaktion erfolgreich aktualisiert.";
                loadAllTransaktionen(); // Refresh list
            } else {
                message = "Fehler beim Bearbeiten der Transaktion: " + response.getStatus();
                System.out
                        .println("Error response: " + response.getStatus() + " - " + response.readEntity(String.class));
            }

            client.close();

        } catch (Exception e) {
            message = "Fehler beim Bearbeiten der Transaktion: " + e.getMessage();
            System.out.println("Exception during transaction editing: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Stay on same page
    }

    /**
     * Löscht eine Transaktion
     */
    public String deleteTransaktion() {
        try {
            System.out.println("Deleting transaction: " + transaktion.getTransaktionsnummer());

            if (transaktion.getTransaktionsnummer() == null || transaktion.getTransaktionsnummer().trim().isEmpty()) {
                message = "Bitte geben Sie eine Transaktionsnummer für das Löschen an.";
                return null;
            }

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/transaktionen")
                    .path(transaktion.getTransaktionsnummer());

            Response response = target.request(MediaType.APPLICATION_JSON).delete();

            if (response.getStatus() == 200 || response.getStatus() == 204) {
                message = "Transaktion erfolgreich gelöscht.";
                transaktion = new TransaktionTO(); // Reset form
                loadAllTransaktionen(); // Refresh list
            } else {
                message = "Fehler beim Löschen der Transaktion: " + response.getStatus();
                System.out
                        .println("Error response: " + response.getStatus() + " - " + response.readEntity(String.class));
            }

            client.close();

        } catch (Exception e) {
            message = "Fehler beim Löschen der Transaktion: " + e.getMessage();
            System.out.println("Exception during transaction deletion: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Stay on same page
    }

    /**
     * Lädt alle Transaktionen vom Server
     */
    public String loadAllTransaktionen() {
        try {
            System.out.println("Loading all transactions");
            transaktionenListe.clear(); // Clear existing data
            message = "Lade Transaktionsdaten...";

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/transaktionen");

            Response response = target.request(MediaType.APPLICATION_JSON).get();
            System.out.println("Response status: " + response.getStatus());

            if (response.getStatus() == 200) {
                transaktionenListe = response.readEntity(new GenericType<List<TransaktionTO>>() {
                });
                message = "Alle Transaktionen geladen: " + transaktionenListe.size() + " Transaktionen gefunden.";
                System.out.println("Retrieved " + transaktionenListe.size() + " transactions from server");

                // Debug: Print first transaction if available
                if (!transaktionenListe.isEmpty()) {
                    TransaktionTO firstTx = transaktionenListe.get(0);
                    System.out.println(
                            "First transaction: " + firstTx.getTransaktionsnummer() + " - " + firstTx.getBetrag());
                }

            } else {
                String errorMsg = response.readEntity(String.class);
                message = "Fehler beim Abrufen der Transaktionen: " + response.getStatus();
                System.out.println("Error response: " + response.getStatus() + " - " + errorMsg);
            }

            client.close();

        } catch (Exception e) {
            message = "Fehler beim Laden der Transaktionen: " + e.getMessage();
            System.out.println("Exception during transaction loading: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Stay on same page
    }

    /**
     * Lädt ein neues leeres Transaktions-Formular
     */
    public String neueTransaktion() {
        transaktion = new TransaktionTO();
        message = "Neues Transaktions-Formular geladen.";
        return null; // Stay on same page
    }

    /**
     * Storniert eine Transaktion (setzt Status auf "storniert")
     */
    public String cancelTransaktion() {
        try {
            if (transaktion.getTransaktionsnummer() == null || transaktion.getTransaktionsnummer().trim().isEmpty()) {
                message = "Bitte geben Sie eine Transaktionsnummer für die Stornierung an.";
                return null;
            }

            // Set status to "storniert"
            transaktion.setTransaktionsstatus("storniert");

            // Update the transaction
            return editTransaktion();

        } catch (Exception e) {
            message = "Fehler beim Stornieren der Transaktion: " + e.getMessage();
            System.out.println("Exception during transaction cancellation: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Stay on same page
    }

    /**
     * Setzt das Formular zurück
     */
    public String resetForm() {
        transaktion = new TransaktionTO();
        message = "Formular zurückgesetzt.";
        return null; // Stay on same page
    }

    /**
     * Zeigt Details für eine bestimmte Transaktion - lädt die Daten in das Formular
     */
    public String showDetails(String transaktionsnummer) {
        System.out.println("=== SHOW DETAILS CALLED ===");
        System.out.println("Transaction number: " + transaktionsnummer);
        System.out.println("Current list size: " + (transaktionenListe != null ? transaktionenListe.size() : "null"));
        
        if (transaktionenListe != null && !transaktionenListe.isEmpty()) {
            System.out.println("Available transaction numbers in list:");
            for (TransaktionTO tx : transaktionenListe) {
                System.out.println("  - " + tx.getTransaktionsnummer());
            }
        }

        try {
            // Find the transaction in our current list
            TransaktionTO selectedTransaction = transaktionenListe.stream()
                    .filter(tx -> transaktionsnummer.equals(tx.getTransaktionsnummer()))
                    .findFirst()
                    .orElse(null);

            if (selectedTransaction != null) {
                System.out.println("FOUND TRANSACTION! Details:");
                System.out.println("  Number: " + selectedTransaction.getTransaktionsnummer());
                System.out.println("  Account: " + selectedTransaction.getKonto());
                System.out.println("  Amount: " + selectedTransaction.getBetrag());
                System.out.println("  Type: " + selectedTransaction.getTransaktionsart());
                System.out.println("  Status: " + selectedTransaction.getTransaktionsstatus());
                System.out.println("  Recipient: " + selectedTransaction.getEmpfaenger());
                System.out.println("  Date: " + selectedTransaction.getTransaktionsdatum());
                
                // Create a copy of the selected transaction to avoid binding issues
                transaktion = new TransaktionTO();
                transaktion.setTransaktionsnummer(selectedTransaction.getTransaktionsnummer());
                transaktion.setKonto(selectedTransaction.getKonto());
                transaktion.setTransaktionsdatum(selectedTransaction.getTransaktionsdatum());
                transaktion.setBetrag(selectedTransaction.getBetrag());
                transaktion.setTransaktionsart(selectedTransaction.getTransaktionsart());
                transaktion.setEmpfaenger(selectedTransaction.getEmpfaenger());
                transaktion.setTransaktionsstatus(selectedTransaction.getTransaktionsstatus());
                
                System.out.println("AFTER COPYING TO FORM:");
                System.out.println("  Form Number: " + transaktion.getTransaktionsnummer());
                System.out.println("  Form Account: " + transaktion.getKonto());
                System.out.println("  Form Amount: " + transaktion.getBetrag());
                System.out.println("  Form Type: " + transaktion.getTransaktionsart());
                System.out.println("  Form Status: " + transaktion.getTransaktionsstatus());
                System.out.println("  Form Recipient: " + transaktion.getEmpfaenger());
                System.out.println("  Form Date: " + transaktion.getTransaktionsdatum());
                
                message = "Transaktionsdaten für " + transaktionsnummer
                        + " geladen. Sie können die Daten bearbeiten und speichern.";

            } else {
                message = "Transaktion mit Nummer " + transaktionsnummer + " nicht gefunden.";
                System.out.println("TRANSACTION NOT FOUND in current list: " + transaktionsnummer);
            }

        } catch (Exception e) {
            message = "Fehler beim Laden der Transaktionsdaten: " + e.getMessage();
            System.out.println("Error loading transaction details: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Stay on same page
    }
}
