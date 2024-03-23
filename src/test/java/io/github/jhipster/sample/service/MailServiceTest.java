package io.github.jhipster.sample.service;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.domain.User;
import io.quarkus.mailer.MockMailbox;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.ext.mail.MailMessage;
import jakarta.inject.Inject;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link MailService}.
 */
@QuarkusTest
public class MailServiceTest {

    @Inject
    MockMailbox mailbox;

    @Inject
    MailService mailService;

    private static final String[] languages = {
        // jhipster-needle-i18n-language-constant - JHipster will add/remove languages in this array
        // TODO: this array should be used alongside localized templates to ensure languages are correctly handle
        // see https://github.com/quarkusio/quarkus/issues/7665
        // https://quarkus.io/guides/qute-reference#localization
    };

    @BeforeEach
    void init() {
        mailbox.clear();
    }

    User user() {
        User user = new User();
        user.login = "john";
        user.email = "john.doe@example.com";
        user.langKey = "en";

        return user;
    }

    @Test
    void should_containsActivationInfosWhenCallSendActivationEmail() {
        User user = user();

        mailService.sendActivationEmail(user);

        List<MailMessage> sent = mailbox.getMailMessagesSentTo(user.email);
        assertThat(sent).hasSize(1);
        MailMessage actual = sent.get(0);
        assertThat(actual.getHtml()).contains("Your JHipster account has been created, please click on the URL below to activate it:");
        assertThat(actual.getSubject()).isEqualTo("jhipsterSampleApplication account activation is required");
    }

    @Test
    void should_containsActivationInfosWhenCallSendCreationEmail() {
        User user = user();

        mailService.sendCreationEmail(user);

        List<MailMessage> sent = mailbox.getMailMessagesSentTo(user.email);
        assertThat(sent).hasSize(1);
        MailMessage actual = sent.get(0);
        assertThat(actual.getHtml()).contains("Your JHipster account has been created, please click on the URL below to access it:");
        assertThat(actual.getSubject()).isEqualTo("jhipsterSampleApplication account activation is required");
    }

    @Test
    void should_containsResetInfosWhenCallSendPasswordResetMail() {
        User user = user();

        mailService.sendPasswordResetMail(user);

        List<MailMessage> sent = mailbox.getMailMessagesSentTo(user.email);
        assertThat(sent).hasSize(1);
        MailMessage actual = sent.get(0);
        assertThat(actual.getHtml()).contains(
            "For your JHipster account a password reset was requested, please click on the URL below to reset it:"
        );
        assertThat(actual.getSubject()).isEqualTo("jhipsterSampleApplication password reset");
    }
}
