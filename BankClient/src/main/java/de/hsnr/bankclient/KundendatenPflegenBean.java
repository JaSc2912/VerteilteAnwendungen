package de.hsnr.bankclient;

import jakarta.enterprise.context.RequestScoped;
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
 * Managed Bean für Kundendaten pflegen
 */
@Named
@RequestScoped
public class KundendatenPflegenBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private KundeTO kunde = new KundeTO();
    private List<KundeTO> kundenListe = new ArrayList<>();
    private String serverUrl = "http://localhost:8080/BankServer/api";
    private String message;

    public KundeTO getKunde() {
        return kunde;
    }

    public void setKunde(KundeTO kunde) {
        this.kunde = kunde;
    }

    public List<KundeTO> getKundenListe() {
        return kundenListe;
    }

    public String getMessage() {
        return message;
    }

    public String addKunde() {
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/kunden");

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(kunde));

            if (response.getStatus() == 201) {
                message = "Kunde erfolgreich hinzugefügt";
                kunde = new KundeTO(); // Reset form
                return "/kundendatenPflegenResult.xhtml?faces-redirect=true";
            } else {
                message = "Fehler beim Hinzufügen des Kunden";
                return "/kundendatenPflegenResult.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            message = "Fehler: " + e.getMessage();
            return "/kundendatenPflegenResult.xhtml?faces-redirect=true";
        }
    }

    public String editKunde() {
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/kunden");

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .put(Entity.json(kunde));

            if (response.getStatus() == 200) {
                message = "Kunde erfolgreich bearbeitet";
                return "/kundendatenPflegenResult.xhtml?faces-redirect=true";
            } else {
                message = "Fehler beim Bearbeiten des Kunden";
                return "/kundendatenPflegenResult.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            message = "Fehler: " + e.getMessage();
            return "/kundendatenPflegenResult.xhtml?faces-redirect=true";
        }
    }

    public String deleteKunde() {
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/kunden").path(kunde.getKundennummer());

            Response response = target.request().delete();

            if (response.getStatus() == 200) {
                message = "Kunde erfolgreich gelöscht";
                kunde = new KundeTO(); // Reset form
                return "/kundendatenPflegenResult.xhtml?faces-redirect=true";
            } else {
                message = "Fehler beim Löschen des Kunden";
                return "/kundendatenPflegenResult.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            message = "Fehler: " + e.getMessage();
            return "/kundendatenPflegenResult.xhtml?faces-redirect=true";
        }
    }

    public String loadAllKunden() {
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/kunden");

            Response response = target.request(MediaType.APPLICATION_JSON).get();

            if (response.getStatus() == 200) {
                kundenListe = response.readEntity(new GenericType<List<KundeTO>>() {});
                return "/kundenuebersicht.xhtml?faces-redirect=true";
            } else {
                message = "Fehler beim Laden der Kunden";
                return "/kundendatenPflegenResult.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            message = "Fehler: " + e.getMessage();
            return "/kundendatenPflegenResult.xhtml?faces-redirect=true";
        }
    }
}
