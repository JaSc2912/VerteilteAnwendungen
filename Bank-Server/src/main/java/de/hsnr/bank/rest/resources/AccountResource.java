package de.hsnr.bank.rest.resources;

import de.hsnr.bank.entities.Bankkonto;
import de.hsnr.bank.entities.Transaktion;
import de.hsnr.bank.services.interfaces.IAccountService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * REST resource for account operations
 */
@Path("/accounts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    private static final Logger LOGGER = Logger.getLogger(AccountResource.class.getName());

    @EJB
    private IAccountService accountService;

    /**
     * Get account by IBAN
     */
    @GET
    @Path("/{iban}")
    public Response getAccount(@PathParam("iban") String iban) {
        try {
            Bankkonto account = accountService.findByIban(iban);
            if (account == null) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Account not found");
                error.put("iban", iban);
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }
            return Response.ok(account).build();
        } catch (Exception e) {
            LOGGER.warning("Error getting account: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to retrieve account");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    /**
     * Get accounts by customer ID
     */
    @GET
    @Path("/customer/{customerId}")
    public Response getAccountsByCustomer(@PathParam("customerId") Long kundennummer) {
        try {
            List<Bankkonto> accounts = accountService.getAccountsByCustomer(kundennummer);
            return Response.ok(accounts).build();
        } catch (Exception e) {
            LOGGER.warning("Error getting customer accounts: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to retrieve customer accounts");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    /**
     * Create new account
     */
    @POST
    public Response createAccount(Bankkonto bankkonto) {
        try {
            // Validate required fields
            if (bankkonto.getIban() == null || bankkonto.getIban().trim().isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "IBAN is required");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            if (bankkonto.getKunde() == null || bankkonto.getKunde().getKundennummer() == null) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Customer information is required");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            Bankkonto createdAccount = accountService.createAccount(bankkonto);
            return Response.status(Response.Status.CREATED).entity(createdAccount).build();
        } catch (Exception e) {
            LOGGER.warning("Error creating account: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to create account");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    /**
     * Update account
     */
    @PUT
    @Path("/{iban}")
    public Response updateAccount(@PathParam("iban") String iban, Bankkonto bankkonto) {
        try {
            // Ensure the IBAN in the path matches the entity
            bankkonto.setIban(iban);

            Bankkonto updatedAccount = accountService.updateAccount(bankkonto);
            return Response.ok(updatedAccount).build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Account not found");
                error.put("iban", iban);
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }

            LOGGER.warning("Error updating account: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to update account");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    /**
     * Get account balance
     */
    @GET
    @Path("/{iban}/balance")
    public Response getBalance(@PathParam("iban") String iban) {
        try {
            BigDecimal balance = accountService.getBalance(iban);

            Map<String, Object> response = new HashMap<>();
            response.put("iban", iban);
            response.put("balance", balance);

            return Response.ok(response).build();
        } catch (Exception e) {
            LOGGER.warning("Error getting balance: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to get balance");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    /**
     * Get account transactions
     */
    @GET
    @Path("/{iban}/transactions")
    public Response getTransactions(@PathParam("iban") String iban) {
        try {
            List<Transaktion> transactions = accountService.getTransactions(iban);
            return Response.ok(transactions).build();
        } catch (Exception e) {
            LOGGER.warning("Error getting transactions: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to retrieve transactions");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    /**
     * Transfer money between accounts
     */
    @POST
    @Path("/transfer")
    public Response transferMoney(TransferRequest request) {
        try {
            // Validate request
            if (request.getFromIban() == null || request.getFromIban().trim().isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Source IBAN is required");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            if (request.getToIban() == null || request.getToIban().trim().isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Destination IBAN is required");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Valid transfer amount is required");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            Transaktion transaction = accountService.transferMoney(
                    request.getFromIban(),
                    request.getToIban(),
                    request.getAmount(),
                    request.getVerwendungszweck());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Transfer completed successfully");
            response.put("transaction", transaction);

            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Invalid transfer request");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        } catch (RuntimeException e) {
            LOGGER.warning("Error during transfer: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Transfer failed");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
    }

    /**
     * Deposit money to account
     */
    @POST
    @Path("/{iban}/deposit")
    public Response depositMoney(@PathParam("iban") String iban, TransactionRequest request) {
        try {
            if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Valid deposit amount is required");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            Transaktion transaction = accountService.depositMoney(iban, request.getAmount());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Deposit completed successfully");
            response.put("transaction", transaction);

            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Invalid deposit request");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        } catch (RuntimeException e) {
            LOGGER.warning("Error during deposit: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Deposit failed");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
    }

    /**
     * Withdraw money from account
     */
    @POST
    @Path("/{iban}/withdraw")
    public Response withdrawMoney(@PathParam("iban") String iban, TransactionRequest request) {
        try {
            if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "Valid withdrawal amount is required");
                return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
            }

            Transaktion transaction = accountService.withdrawMoney(iban, request.getAmount());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Withdrawal completed successfully");
            response.put("transaction", transaction);

            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Invalid withdrawal request");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        } catch (RuntimeException e) {
            LOGGER.warning("Error during withdrawal: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Withdrawal failed");
            error.put("message", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
    }

    // Request DTOs
    public static class TransferRequest {
        private String fromIban;
        private String toIban;
        private BigDecimal amount;
        private String verwendungszweck;

        // Getters and setters
        public String getFromIban() {
            return fromIban;
        }

        public void setFromIban(String fromIban) {
            this.fromIban = fromIban;
        }

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

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
    }
}
