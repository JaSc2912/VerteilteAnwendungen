package de.hsnr.bank.client.services;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 * Session management service for user authentication
 */
@Named
@SessionScoped
public class SessionService implements Serializable {
    private static final long serialVersionUID = 1L;

    private String currentUser;
    private String authToken;
    private boolean loggedIn = false;

    /**
     * Login user
     */
    public void login(String username, String token) {
        this.currentUser = username;
        this.authToken = token;
        this.loggedIn = true;
    }

    /**
     * Logout user
     */
    public void logout() {
        this.currentUser = null;
        this.authToken = null;
        this.loggedIn = false;
    }

    /**
     * Get authorization header value
     */
    public String getAuthorizationHeader() {
        return loggedIn && authToken != null ? "Bearer " + authToken : null;
    }

    /**
     * Check if user is logged in
     */
    public boolean isLoggedIn() {
        return loggedIn && authToken != null;
    }

    /**
     * Get current user
     */
    public String getCurrentUser() {
        return currentUser;
    }

    /**
     * Get current user ID (same as username for this implementation)
     */
    public String getCurrentUserId() {
        return currentUser;
    }

    /**
     * Get auth token
     */
    public String getAuthToken() {
        return authToken;
    }
}
