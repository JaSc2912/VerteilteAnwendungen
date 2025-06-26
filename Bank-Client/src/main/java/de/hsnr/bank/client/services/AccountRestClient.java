package de.hsnr.bank.client.services;

import de.hsnr.bank.client.model.Bankkonto;
import de.hsnr.bank.client.model.Transaktion;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.math.BigDecimal;
import java.util.List;

/**
 * REST client interface for account operations
 */
@RegisterRestClient(baseUri = "http://localhost:8080/Bank-Server/api")
@Path("/accounts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AccountRestClient {

    /**
     * Get account by IBAN
     */
    @GET
    @Path("/{iban}")
    Bankkonto getAccount(@PathParam("iban") String iban, @HeaderParam("Authorization") String token);

    /**
     * Get accounts by customer ID
     */
    @GET
    @Path("/customer/{customerId}")
    Response getAccountsByCustomer(@PathParam("customerId") Long kundennummer,
            @HeaderParam("Authorization") String token);

    /**
     * Create new account
     */
    @POST
    Response createAccount(CreateAccountRequest request, @HeaderParam("Authorization") String token);

    /**
     * Update account
     */
    @PUT
    @Path("/{iban}")
    Response updateAccount(@PathParam("iban") String iban, Bankkonto bankkonto,
            @HeaderParam("Authorization") String token);

    /**
     * Get account balance
     */
    @GET
    @Path("/{iban}/balance")
    Response getBalance(@PathParam("iban") String iban, @HeaderParam("Authorization") String token);

    /**
     * Get account transactions
     */
    @GET
    @Path("/{iban}/transactions")
    Response getTransactions(@PathParam("iban") String iban, @HeaderParam("Authorization") String token);

    /**
     * Perform transaction (deposit/withdrawal)
     */
    @POST
    @Path("/{iban}/transaction")
    Response performTransaction(@PathParam("iban") String iban, TransactionRequest request,
            @HeaderParam("Authorization") String token);

    /**
     * Transfer money between accounts
     */
    @POST
    @Path("/{iban}/transfer")
    Response transferMoney(@PathParam("iban") String fromIban, TransferRequest request,
            @HeaderParam("Authorization") String token);

    // Request DTOs
    public static class CreateAccountRequest {
        private Long customerId;
        private String accountType;

        public CreateAccountRequest() {
        }

        public CreateAccountRequest(Long customerId, String accountType) {
            this.customerId = customerId;
            this.accountType = accountType;
        }

        public Long getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Long customerId) {
            this.customerId = customerId;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }
    }

    public static class TransferRequest {
        private String toIban;
        private BigDecimal amount;
        private String verwendungszweck;

        public TransferRequest() {
        }

        public TransferRequest(String toIban, BigDecimal amount, String verwendungszweck) {
            this.toIban = toIban;
            this.amount = amount;
            this.verwendungszweck = verwendungszweck;
        }

        // Getters and setters
        public String getToIban() {
            return toIban;
        }

        public void setToIban(String toIban) {
            this.toIban = toIban;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getVerwendungszweck() {
            return verwendungszweck;
        }

        public void setVerwendungszweck(String verwendungszweck) {
            this.verwendungszweck = verwendungszweck;
        }
    }

    public static class TransactionRequest {
        private BigDecimal amount;
        private String type;

        public TransactionRequest() {
        }

        public TransactionRequest(BigDecimal amount, String type) {
            this.amount = amount;
            this.type = type;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
