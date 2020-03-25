package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.security.BCryptPasswordHasher;
import com.mycompany.myapp.security.UserNotActivatedException;
import com.mycompany.myapp.security.UsernameNotFoundException;
import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.runtime.QuarkusPrincipal;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Locale;
import java.util.stream.Collectors;

@ApplicationScoped
public class AuthenticationService {

    private final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    public static final String emailValidator = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

    final BCryptPasswordHasher passwordHasher;

    final UserRepository userRepository;

    @Inject
    public AuthenticationService(BCryptPasswordHasher passwordHasher, UserRepository userRepository) {
        this.passwordHasher = passwordHasher;
        this.userRepository = userRepository;
    }

    public QuarkusSecurityIdentity authenticate(String login, String password) {
        User user = loadByUsername(login);
        if (!user.activated) {
            throw new UserNotActivatedException("User " + login + " was not activated");
        }
        if (passwordHasher.checkPassword(password, user.password)) {
            return createQuarkusSecurityIdentity(user);
        }
        log.debug("Authentication failed: password does not match stored value");
        throw new AuthenticationFailedException("Authentication failed: password does not match stored value");
    }

    private User loadByUsername(String login) {
        log.debug("Authenticating {}", login);

        if (login.matches(emailValidator)) {
            return userRepository.findOneWithAuthoritiesByEmailIgnoreCase(login)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + login + " was not found in the database"));
        }
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return userRepository.findOneWithAuthoritiesByLogin(lowercaseLogin)
            .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));

    }

    private QuarkusSecurityIdentity createQuarkusSecurityIdentity(User user) {
        QuarkusSecurityIdentity.Builder builder = QuarkusSecurityIdentity.builder();
        builder.setPrincipal(new QuarkusPrincipal(user.login));
        builder.addCredential(new io.quarkus.security.credential.PasswordCredential(user.password.toCharArray()));
        builder.addRoles(user.authorities.stream().map(authority -> authority.name).collect(Collectors.toSet()));
        return builder.build();
    }
}
