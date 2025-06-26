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
 * Managed Bean für Kreditanträge verwalten
 */
@Named
@SessionScoped
public class KreditantragBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private KreditantragTO kreditantrag = new KreditantragTO();
    private List<KreditantragTO> kreditantraegeListe = new ArrayList<>();
    private String serverUrl = "http://localhost:8080/BankServer/api";
    private String message;

    public KreditantragTO getKreditantrag() {
        return kreditantrag;
    }

    public void setKreditantrag(KreditantragTO kreditantrag) {
        this.kreditantrag = kreditantrag;
    }

    public List<KreditantragTO> getKreditantraegeListe() {
        return kreditantraegeListe;
    }

    public String getMessage() {
        return message;
    }

    public String addKreditantrag() {
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/kreditantraege");

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(kreditantrag));

            if (response.getStatus() == 201) {
                message = "Kreditantrag erfolgreich erstellt";
                kreditantrag = new KreditantragTO(); // Reset form
                return "/kreditantraegeResult.xhtml?faces-redirect=true";
            } else {
                message = "Fehler beim Erstellen des Kreditantrags";
                return "/kreditantraegeResult.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            message = "Fehler: " + e.getMessage();
            return "/kreditantraegeResult.xhtml?faces-redirect=true";
        }
    }

    public String approveKreditantrag() {
        try {
            // Set status to approved
            kreditantrag.setKreditstatus("GENEHMIGT");

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/kreditantraege");

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .put(Entity.json(kreditantrag));

            if (response.getStatus() == 200) {
                message = "Kreditantrag erfolgreich genehmigt";
                return "/kreditantraegeResult.xhtml?faces-redirect=true";
            } else {
                message = "Fehler beim Genehmigen des Kreditantrags";
                return "/kreditantraegeResult.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            message = "Fehler: " + e.getMessage();
            return "/kreditantraegeResult.xhtml?faces-redirect=true";
        }
    }

    public String rejectKreditantrag() {
        try {
            // Set status to rejected
            kreditantrag.setKreditstatus("ABGELEHNT");

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/kreditantraege");

            Response response = target.request(MediaType.APPLICATION_JSON)
                    .put(Entity.json(kreditantrag));

            if (response.getStatus() == 200) {
                message = "Kreditantrag erfolgreich abgelehnt";
                return "/kreditantraegeResult.xhtml?faces-redirect=true";
            } else {
                message = "Fehler beim Ablehnen des Kreditantrags";
                return "/kreditantraegeResult.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            message = "Fehler: " + e.getMessage();
            return "/kreditantraegeResult.xhtml?faces-redirect=true";
        }
    }

    public String loadAllKreditantraege() {
        try {
            System.out.println("Loading all credit applications...");
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/kreditantraege");
            System.out.println("Target URL: " + target.getUri());

            Response response = target.request(MediaType.APPLICATION_JSON).get();
            System.out.println("Response status: " + response.getStatus());

            if (response.getStatus() == 200) {
                kreditantraegeListe = response.readEntity(new GenericType<List<KreditantragTO>>() {
                });
                System.out.println("Credit applications loaded: " + kreditantraegeListe.size());
                message = kreditantraegeListe.size() + " Kreditantrag/Kreditanträge gefunden";
                return "/kreditantraegeUebersicht.xhtml?faces-redirect=true";
            } else {
                String errorBody = response.readEntity(String.class);
                message = "Fehler beim Laden der Kreditanträge - Status: " + response.getStatus();
                System.out.println("Failed to load credit applications. Status: " + response.getStatus());
                System.out.println("Error response: " + errorBody);
                return "/kreditantraegeResult.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            message = "Fehler: " + e.getMessage();
            System.out.println("Exception loading credit applications: " + e.getMessage());
            e.printStackTrace();
            return "/kreditantraegeResult.xhtml?faces-redirect=true";
        }
    }
}
