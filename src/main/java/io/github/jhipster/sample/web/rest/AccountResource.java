package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.User;
import io.github.jhipster.sample.service.InvalidPasswordException;
import io.github.jhipster.sample.service.MailService;
import io.github.jhipster.sample.service.UserService;
import io.github.jhipster.sample.service.UsernameAlreadyUsedException;
import io.github.jhipster.sample.service.dto.PasswordChangeDTO;
import io.github.jhipster.sample.service.dto.UserDTO;
import io.github.jhipster.sample.web.rest.errors.EmailAlreadyUsedException;
import io.github.jhipster.sample.web.rest.errors.EmailNotFoundException;
import io.github.jhipster.sample.web.rest.errors.InvalidPasswordWebException;
import io.github.jhipster.sample.web.rest.errors.LoginAlreadyUsedException;
import io.github.jhipster.sample.web.rest.vm.KeyAndPasswordVM;
import io.github.jhipster.sample.web.rest.vm.ManagedUserVM;

import io.quarkus.security.Authenticated;
import java.security.Principal;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST controller for managing the current user's account.
 */
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class AccountResource {
    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private static class AccountResourceException extends RuntimeException {

        private AccountResourceException(String message) {
            super(message);
        }
    }

    final MailService mailService;

    final UserService userService;

    @Inject
    public AccountResource(MailService mailService, UserService userService) {
        this.mailService = mailService;
        this.userService = userService;
    }

    /**
     * {@code GET /account} : get the current user.
     *
     * @return the current user.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be returned.
     */
    @GET
    @Path("/account")
    @Authenticated
    public UserDTO getAccount(@Context SecurityContext ctx) {
        return userService
            .getUserWithAuthoritiesByLogin(ctx.getUserPrincipal().getName())
            .map(UserDTO::new)
            .orElseThrow(() -> new AccountResourceException("User could not be found"));
    }

    /**
     * {@code POST /account} : update the current user information.
     *
     * @param userDTO the current user information.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws RuntimeException          {@code 500 (Internal Server Error)} if the user login wasn't found.
     */
    @POST
    @Path("/account")
    public Response saveAccount(@Valid UserDTO userDTO, @Context SecurityContext ctx) {
        var userLogin = Optional
            .ofNullable(ctx.getUserPrincipal().getName())
            .orElseThrow(() -> new AccountResourceException("Current user login not found"));
        var existingUser = User.findOneByEmailIgnoreCase(userDTO.email);
        if (existingUser.isPresent() && (!existingUser.get().login.equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        var user = User.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new AccountResourceException("User could not be found");
        }
        userService.updateUser(userLogin, userDTO.firstName, userDTO.lastName, userDTO.email, userDTO.langKey, userDTO.imageUrl);
        return Response.ok().build();
    }

    /**
     * {@code POST /register} : register the user.
     *
     * @param managedUserVM the managed user View Model.
     * @throws InvalidPasswordWebException  {@code 400 (Bad Request)} if the password is incorrect.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already used.
     */
    @POST
    @Path("/register")
    @PermitAll
    public CompletionStage<Response> registerAccount(@Valid ManagedUserVM managedUserVM) {
        if (!checkPasswordLength(managedUserVM.password)) {
            throw new InvalidPasswordWebException();
        }
        try {
            var user = userService.registerUser(managedUserVM, managedUserVM.password);
            return mailService.sendActivationEmail(user).thenApply(it -> Response.created(null).build());
        } catch (UsernameAlreadyUsedException e) {
            throw new LoginAlreadyUsedException();
        } catch (io.github.jhipster.sample.service.EmailAlreadyUsedException e) {
            throw new EmailAlreadyUsedException();
        }
    }

    /**
     * {@code GET /activate} : activate the registered user.
     *
     * @param key the activation key.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be activated.
     */
    @GET
    @Path("/activate")
    @PermitAll
    public void activateAccount(@QueryParam(value = "key") String key) {
        var user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this activation key");
        }
    }

    /**
     * {@code GET /authenticate} : check if the user is authenticated, and return its login.
     *
     * @param ctx the request security context.
     * @return the login if the user is authenticated.
     */
    @GET
    @Path("/authenticate")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String isAuthenticated(@Context SecurityContext ctx) {
        log.debug("REST request to check if the current user is authenticated");
        return Optional.ofNullable(ctx.getUserPrincipal()).map(Principal::getName).orElse("");
    }

    /**
     * {@code POST /account/change-password} : changes the current user's password.
     *
     * @param passwordChangeDto current and new password.
     * @throws InvalidPasswordWebException {@code 400 (Bad Request)} if the new password is incorrect.
     */
    @POST
    @Path("/account/change-password")
    public Response changePassword(PasswordChangeDTO passwordChangeDto, @Context SecurityContext ctx) {
        var userLogin = Optional
            .ofNullable(ctx.getUserPrincipal().getName())
            .orElseThrow(() -> new AccountResourceException("Current user login not found"));
        if (!checkPasswordLength(passwordChangeDto.newPassword)) {
            throw new InvalidPasswordWebException();
        }
        try {
            userService.changePassword(userLogin, passwordChangeDto.currentPassword, passwordChangeDto.newPassword);
        } catch (InvalidPasswordException e) {
            throw new InvalidPasswordWebException();
        }
        return Response.ok().build();
    }

    /**
     * {@code POST /account/reset-password/init} : Send an email to reset the password of the user.
     *
     * @param mail the mail of the user.
     * @throws EmailNotFoundException {@code 400 (Bad Request)} if the email address is not registered.
     */
    @POST
    @Path("/account/reset-password/init")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response requestPasswordReset(String mail) {
        mailService.sendPasswordResetMail(userService.requestPasswordReset(mail).orElseThrow(EmailNotFoundException::new));
        return Response.ok().build();
    }

    /**
     * {@code POST /account/reset-password/finish} : Finish to reset the password of the user.
     *
     * @param keyAndPassword the generated key and the new password.
     * @throws InvalidPasswordWebException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws RuntimeException         {@code 500 (Internal Server Error)} if the password could not be reset.
     */
    @POST
    @Path("/account/reset-password/finish")
    public Response finishPasswordReset(KeyAndPasswordVM keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.newPassword)) {
            throw new InvalidPasswordWebException();
        }
        var user = userService.completePasswordReset(keyAndPassword.newPassword, keyAndPassword.key);

        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this reset key");
        }
        return Response.ok().build();
    }

    private static boolean checkPasswordLength(String password) {
        return (
            !password.isEmpty() &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH
        );
    }
}
