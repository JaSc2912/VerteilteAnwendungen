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
 * Managed Bean für Benutzer verwalten
 */
@Named
@RequestScoped
public class BenutzerBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private BenutzerTO benutzer = new BenutzerTO();
    private List<BenutzerTO> benutzerListe = new ArrayList<>();
    private String serverUrl = "http://localhost:8080/BankServer/api";
    private String message;

    public BenutzerTO getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(BenutzerTO benutzer) {
        this.benutzer = benutzer;
    }

    public List<BenutzerTO> getBenutzerListe() {
        return benutzerListe;
    }

    public String getMessage() {
        return message;
    }

    public String addBenutzer() {
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/benutzer");

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(benutzer));

            if (response.getStatus() == 201) {
                message = "Benutzer erfolgreich hinzugefügt";
                benutzer = new BenutzerTO(); // Reset form
                return "/benutzerResult.xhtml?faces-redirect=true";
            } else {
                message = "Fehler beim Hinzufügen des Benutzers";
                return "/benutzerResult.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            message = "Fehler: " + e.getMessage();
            return "/benutzerResult.xhtml?faces-redirect=true";
        }
    }

    public String editBenutzer() {
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/benutzer");

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .put(Entity.json(benutzer));

            if (response.getStatus() == 200) {
                message = "Benutzer erfolgreich bearbeitet";
                return "/benutzerResult.xhtml?faces-redirect=true";
            } else {
                message = "Fehler beim Bearbeiten des Benutzers";
                return "/benutzerResult.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            message = "Fehler: " + e.getMessage();
            return "/benutzerResult.xhtml?faces-redirect=true";
        }
    }

    public String deleteBenutzer() {
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/benutzer").path(benutzer.getBenutzername());

            Response response = target.request().delete();

            if (response.getStatus() == 200) {
                message = "Benutzer erfolgreich gelöscht";
                benutzer = new BenutzerTO(); // Reset form
                return "/benutzerResult.xhtml?faces-redirect=true";
            } else {
                message = "Fehler beim Löschen des Benutzers";
                return "/benutzerResult.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            message = "Fehler: " + e.getMessage();
            return "/benutzerResult.xhtml?faces-redirect=true";
        }
    }

    public String loadAllBenutzer() {
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/benutzer");

            Response response = target.request(MediaType.APPLICATION_JSON).get();

            if (response.getStatus() == 200) {
                benutzerListe = response.readEntity(new GenericType<List<BenutzerTO>>() {
                });
                return "/benutzeruebersicht.xhtml?faces-redirect=true";
            } else {
                message = "Fehler beim Laden der Benutzer";
                return "/benutzerResult.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            message = "Fehler: " + e.getMessage();
            return "/benutzerResult.xhtml?faces-redirect=true";
        }
    }
}
