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
 * Managed Bean für Bankkonten verwalten
 */
@Named
@SessionScoped
public class BankkontoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private BankkontoTO bankkonto = new BankkontoTO();
    private List<BankkontoTO> kontenListe = new ArrayList<>();
    private String serverUrl = "http://localhost:8080/BankServer/api";
    private String message;

    // Getters and Setters
    public BankkontoTO getBankkonto() {
        return bankkonto;
    }

    public void setBankkonto(BankkontoTO bankkonto) {
        this.bankkonto = bankkonto;
    }

    public List<BankkontoTO> getKontenListe() {
        return kontenListe;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Fügt ein neues Bankkonto hinzu
     */
    public String addKonto() {
        try {
            System.out.println("Adding new bank account: " + bankkonto.getIban());

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/bankkonten");

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(bankkonto, MediaType.APPLICATION_JSON));

            if (response.getStatus() == 200 || response.getStatus() == 201) {
                message = "Bankkonto erfolgreich erstellt.";
                bankkonto = new BankkontoTO(); // Reset form
                loadAllKonten(); // Refresh list
            } else {
                message = "Fehler beim Erstellen des Bankkontos: " + response.getStatus();
                System.out
                        .println("Error response: " + response.getStatus() + " - " + response.readEntity(String.class));
            }

            client.close();

        } catch (Exception e) {
            message = "Fehler beim Hinzufügen des Bankkontos: " + e.getMessage();
            System.out.println("Exception during account creation: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Stay on same page
    }

    /**
     * Bearbeitet ein existierendes Bankkonto
     */
    public String editKonto() {
        try {
            System.out.println("Editing bank account: " + bankkonto.getIban());

            if (bankkonto.getIban() == null || bankkonto.getIban().trim().isEmpty()) {
                message = "Bitte geben Sie eine IBAN für die Bearbeitung an.";
                return null;
            }

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/bankkonten").path(bankkonto.getIban());

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .put(Entity.entity(bankkonto, MediaType.APPLICATION_JSON));

            if (response.getStatus() == 200) {
                message = "Bankkonto erfolgreich aktualisiert.";
                loadAllKonten(); // Refresh list
            } else {
                message = "Fehler beim Aktualisieren des Bankkontos: " + response.getStatus();
                System.out
                        .println("Error response: " + response.getStatus() + " - " + response.readEntity(String.class));
            }

            client.close();

        } catch (Exception e) {
            message = "Fehler beim Bearbeiten des Bankkontos: " + e.getMessage();
            System.out.println("Exception during account update: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Stay on same page
    }

    /**
     * Löscht ein Bankkonto
     */
    public String deleteKonto() {
        try {
            System.out.println("Deleting bank account: " + bankkonto.getIban());

            if (bankkonto.getIban() == null || bankkonto.getIban().trim().isEmpty()) {
                message = "Bitte geben Sie eine IBAN für das Löschen an.";
                return null;
            }

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/bankkonten").path(bankkonto.getIban());

            Response response = target.request(MediaType.APPLICATION_JSON).delete();

            if (response.getStatus() == 200 || response.getStatus() == 204) {
                message = "Bankkonto erfolgreich gelöscht.";
                bankkonto = new BankkontoTO(); // Reset form
                loadAllKonten(); // Refresh list
            } else {
                message = "Fehler beim Löschen des Bankkontos: " + response.getStatus();
                System.out
                        .println("Error response: " + response.getStatus() + " - " + response.readEntity(String.class));
            }

            client.close();

        } catch (Exception e) {
            message = "Fehler beim Löschen des Bankkontos: " + e.getMessage();
            System.out.println("Exception during account deletion: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Stay on same page
    }

    /**
     * Lädt alle Bankkonten vom Server
     */
    public String loadAllKonten() {
        try {
            System.out.println("Loading all bank accounts");

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/bankkonten");

            Response response = target.request(MediaType.APPLICATION_JSON).get();

            if (response.getStatus() == 200) {
                kontenListe = response.readEntity(new GenericType<List<BankkontoTO>>() {
                });
                message = "Alle Bankkonten geladen: " + kontenListe.size() + " Konten gefunden.";
                System.out.println("Retrieved " + kontenListe.size() + " accounts from server");

                // Navigate to overview page
                return "/kontenuebersicht.xhtml?faces-redirect=true";

            } else {
                message = "Fehler beim Abrufen der Bankkonten: " + response.getStatus();
                System.out
                        .println("Error response: " + response.getStatus() + " - " + response.readEntity(String.class));
            }

            client.close();

        } catch (Exception e) {
            message = "Fehler beim Laden der Bankkonten: " + e.getMessage();
            System.out.println("Exception during account loading: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Stay on same page if error
    }

    /**
     * Lädt ein neues leeres Bankkonto-Formular
     */
    public String neuesKonto() {
        bankkonto = new BankkontoTO();
        message = "Neues Bankkonto-Formular geladen.";
        return null; // Stay on same page
    }

    /**
     * Setzt das Formular zurück
     */
    public String resetForm() {
        bankkonto = new BankkontoTO();
        message = "Formular zurückgesetzt.";
        return null; // Stay on same page
    }

    /**
     * Setzt eine Nachricht für das geladene Konto
     */
    public void setAccountLoadedMessage(String iban) {
        message = "Kontodaten für IBAN " + iban + " geladen. Sie können die Daten bearbeiten und speichern.";
    }
}
