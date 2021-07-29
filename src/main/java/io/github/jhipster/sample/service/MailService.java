package io.github.jhipster.sample.service;

import io.github.jhipster.sample.config.JHipsterProperties;
import io.github.jhipster.sample.domain.User;
import io.quarkus.mailer.MailTemplate;
import io.quarkus.qute.Location;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service for sending emails.
 */
@ApplicationScoped
public class MailService {
    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    final JHipsterProperties jHipsterProperties;

    final MailTemplate activationEmail;

    final MailTemplate creationEmail;

    final MailTemplate passwordResetEmail;

    @Inject
    public MailService(
        JHipsterProperties jHipsterProperties,
        @Location("mail/activationEmail") MailTemplate activationEmail,
        @Location("mail/creationEmail") MailTemplate creationEmail,
        @Location("mail/passwordResetEmail") MailTemplate passwordResetEmail
    ) {
        this.jHipsterProperties = jHipsterProperties;
        this.activationEmail = activationEmail;
        this.creationEmail = creationEmail;
        this.passwordResetEmail = passwordResetEmail;
    }

    public CompletionStage<Void> sendEmailFromTemplate(User user, MailTemplate template, String subject) {
        return template
            .to(user.email)
            .subject(subject)
            .data(BASE_URL, jHipsterProperties.mail().baseUrl())
            .data(USER, user)
            .send()
            .subscribeAsCompletionStage()
            .thenAccept(
                it -> {
                    log.debug("Sent email to User '{}'", user.email);
                }
            );
    }

    public CompletionStage<Void> sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.email);
        return sendEmailFromTemplate(user, activationEmail, "jhipsterSampleApplication account activation is required");
    }

    public CompletionStage<Void> sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.email);
        return sendEmailFromTemplate(user, creationEmail, "jhipsterSampleApplication account activation is required");
    }

    public CompletionStage<Void> sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.email);
        return sendEmailFromTemplate(user, passwordResetEmail, "jhipsterSampleApplication password reset");
    }
}
