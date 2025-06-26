package de.hsnr.bankclient;

import jakarta.enterprise.context.RequestScoped;
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
@RequestScoped
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
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(serverUrl).path("/kunden/search").path(suchName);

            Response response = target.request(MediaType.APPLICATION_JSON).get();

            if (response.getStatus() == 200) {
                suchergebnis = response.readEntity(new GenericType<List<KundeTO>>() {});
                message = suchergebnis.size() + " Kunde(n) gefunden";
                return "/kundenSuchenResult.xhtml?faces-redirect=true";
            } else {
                message = "Fehler bei der Suche";
                return "/kundenSuchenResult.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            message = "Fehler: " + e.getMessage();
            return "/kundenSuchenResult.xhtml?faces-redirect=true";
        }
    }
}
