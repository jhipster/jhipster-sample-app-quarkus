package io.github.jhipster.sample.config.mock;

import io.github.jhipster.sample.config.JHipsterProperties;
import io.quarkus.test.Mock;
import io.smallrye.config.SmallRyeConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.Config;
import org.mockito.Mockito;

@ApplicationScoped
public class JHipsterPropertiesMock {

    @Inject
    Config config;

    @Produces
    @ApplicationScoped
    @Mock
    JHipsterProperties properties() {
        JHipsterProperties jHipsterProperties = config.unwrap(SmallRyeConfig.class).getConfigMapping(JHipsterProperties.class);
        JHipsterProperties spyJHipsterProperties = Mockito.spy(jHipsterProperties);

        JHipsterProperties.Info spyInfo = Mockito.spy(jHipsterProperties.info());
        JHipsterProperties.Info.Swagger spySwagger = Mockito.spy(jHipsterProperties.info().swagger());
        Mockito.when(spyJHipsterProperties.info()).thenReturn(spyInfo);
        Mockito.when(spyJHipsterProperties.info().swagger()).thenReturn(spySwagger);

        JHipsterProperties.Security spySecurity = Mockito.spy(jHipsterProperties.security());
        JHipsterProperties.Security.Authentication spyAuthentication = Mockito.spy(jHipsterProperties.security().authentication());
        JHipsterProperties.Security.Authentication.Jwt spyJwt = Mockito.spy(jHipsterProperties.security().authentication().jwt());
        JHipsterProperties.Security.Authentication.Jwt.PrivateKey spyPrivateKey = Mockito.spy(
            jHipsterProperties.security().authentication().jwt().privateKey()
        );
        Mockito.when(spyJHipsterProperties.security()).thenReturn(spySecurity);
        Mockito.when(spyJHipsterProperties.security().authentication()).thenReturn(spyAuthentication);
        Mockito.when(spyJHipsterProperties.security().authentication().jwt()).thenReturn(spyJwt);
        Mockito.when(spyJHipsterProperties.security().authentication().jwt().privateKey()).thenReturn(spyPrivateKey);

        JHipsterProperties.Mail spyMail = Mockito.spy(jHipsterProperties.mail());
        Mockito.when(spyJHipsterProperties.mail()).thenReturn(spyMail);

        return spyJHipsterProperties;
    }
}
