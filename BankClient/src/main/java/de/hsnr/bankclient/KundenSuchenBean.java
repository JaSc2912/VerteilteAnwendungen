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
 * Managed Bean für Kunden suchen
 */
@Named
@SessionScoped
public class KundenSuchenBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String suchName;
    private List<KundeTO> suchergebnis = new ArrayList<>();
    private String serverUrl = "http://localhost:8080/BankServer/api";
    private String message;

    public String getSuchName() {
        return suchName;
    }

    public void setSuchName(String suchName) {
        this.suchName = suchName;
    }

    public List<KundeTO> getSuchergebnis() {
        return suchergebnis;
    }

    public String getMessage() {
        return message;
    }

    public String suchenKunde() {
        try {
            System.out.println("Starting search for: " + suchName);
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/kunden/search").path(suchName);
            System.out.println("Target URL: " + target.getUri());

            Response response = target.request(MediaType.APPLICATION_JSON).get();
            System.out.println("Response status: " + response.getStatus());
            System.out.println("Response headers: " + response.getHeaders());

            if (response.getStatus() == 200) {
                // First read as string to see the raw response
                String rawResponse = response.readEntity(String.class);
                System.out.println("Raw response: " + rawResponse);
                
                // Now we need to make a new request since we consumed the response
                response = target.request(MediaType.APPLICATION_JSON).get();
                suchergebnis = response.readEntity(new GenericType<List<KundeTO>>() {
                });
                System.out.println("Search results count: " + suchergebnis.size());
                for (KundeTO kunde : suchergebnis) {
                    System.out.println("Found customer: " + kunde.getName());
                }
                message = suchergebnis.size() + " Kunde(n) gefunden";
                return "/kundenSuchenResult.xhtml?faces-redirect=true";
            } else {
                String errorBody = response.readEntity(String.class);
                message = "Fehler bei der Suche - Status: " + response.getStatus();
                System.out.println("Search failed with status: " + response.getStatus());
                System.out.println("Error response body: " + errorBody);
                return "/kundenSuchenResult.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            message = "Fehler: " + e.getMessage();
            System.out.println("Search exception: " + e.getMessage());
            e.printStackTrace();
            return "/kundenSuchenResult.xhtml?faces-redirect=true";
        }
    }
}
