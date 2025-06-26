package de.hsnr.bank.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * REST Application configuration
 */
@ApplicationPath("/api")
public class RestApplication extends Application {
    // JAX-RS will automatically discover all REST resources
}
