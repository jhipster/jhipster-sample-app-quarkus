package io.github.jhipster.sample.web.rest;

import static javax.ws.rs.core.UriBuilder.fromPath;

import io.github.jhipster.sample.domain.BankAccount;
import io.github.jhipster.sample.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.sample.web.util.HeaderUtil;
import io.github.jhipster.sample.web.util.ResponseUtil;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.jhipster.sample.domain.BankAccount}.
 */
@Path("/api/bank-accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class BankAccountResource {

    private final Logger log = LoggerFactory.getLogger(BankAccountResource.class);

    private static final String ENTITY_NAME = "bankAccount";

    @ConfigProperty(name = "application.name")
    String applicationName;


    
    /**
     * {@code POST  /bank-accounts} : Create a new bankAccount.
     *
     * @param bankAccount the bankAccount to create.
     * @return the {@link Response} with status {@code 201 (Created)} and with body the new bankAccount, or with status {@code 400 (Bad Request)} if the bankAccount has already an ID.
     */
    @POST
    @Transactional
    public Response createBankAccount(@Valid BankAccount bankAccount, @Context UriInfo uriInfo) {
        log.debug("REST request to save BankAccount : {}", bankAccount);
        if (bankAccount.id != null) {
            throw new BadRequestAlertException("A new bankAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        var result = BankAccount.persistOrUpdate(bankAccount);
        var response = Response.created(fromPath(uriInfo.getPath()).path(result.id.toString()).build()).entity(result);
        HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code PUT  /bank-accounts} : Updates an existing bankAccount.
     *
     * @param bankAccount the bankAccount to update.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the updated bankAccount,
     * or with status {@code 400 (Bad Request)} if the bankAccount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankAccount couldn't be updated.
     */
    @PUT
    @Transactional
    public Response updateBankAccount(@Valid BankAccount bankAccount) {
        log.debug("REST request to update BankAccount : {}", bankAccount);
        if (bankAccount.id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        var result = BankAccount.persistOrUpdate(bankAccount);
        var response = Response.ok().entity(result);
        HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankAccount.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code DELETE  /bank-accounts/:id} : delete the "id" bankAccount.
     *
     * @param id the id of the bankAccount to delete.
     * @return the {@link Response} with status {@code 204 (NO_CONTENT)}.
     */
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteBankAccount(@PathParam("id") Long id) {
        log.debug("REST request to delete BankAccount : {}", id);
        BankAccount.findByIdOptional(id).ifPresent(bankAccount -> {
            bankAccount.delete();
        });
        var response = Response.noContent();
        HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code GET  /bank-accounts} : get all the bankAccounts.
     *     * @return the {@link Response} with status {@code 200 (OK)} and the list of bankAccounts in body.
     */
    @GET
    public List<BankAccount> getAllBankAccounts() {
        log.debug("REST request to get all BankAccounts");
        return BankAccount.findAll().list();
    }


    /**
     * {@code GET  /bank-accounts/:id} : get the "id" bankAccount.
     *
     * @param id the id of the bankAccount to retrieve.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the bankAccount, or with status {@code 404 (Not Found)}.
     */
    @GET
    @Path("/{id}")

    public Response getBankAccount(@PathParam("id") Long id) {
        log.debug("REST request to get BankAccount : {}", id);
        Optional<BankAccount> bankAccount = BankAccount.findByIdOptional(id);
        return ResponseUtil.wrapOrNotFound(bankAccount);
    }
}
