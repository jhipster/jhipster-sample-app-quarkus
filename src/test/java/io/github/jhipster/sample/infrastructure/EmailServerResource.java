package io.github.jhipster.sample.infrastructure;

import io.quarkus.test.common.DevServicesContext;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import java.util.Map;
import java.util.Optional;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.shaded.com.google.common.collect.ImmutableMap;

/**
 * MailHog email server resource integration with Quarkus DevServices to
 * intercept email messages during Native/Integration tests execution.
 */
public class EmailServerResource implements QuarkusTestResourceLifecycleManager, DevServicesContext.ContextAware {

    private Optional<String> containerNetworkId;
    private GenericContainer<?> container;
    private String mailServerUrl;

    @Override
    public void setIntegrationTestContext(DevServicesContext context) {
        containerNetworkId = context.containerNetworkId();
    }

    @Override
    public Map<String, String> start() {
        container =
            new GenericContainer<>("mailhog/mailhog")
                .withExposedPorts(1025, 8025)
                .withLogConsumer(outputFrame -> {})
                .waitingFor(Wait.forHttp("/").forPort(8025));

        containerNetworkId.ifPresent(container::withNetworkMode);
        container.start();

        mailServerUrl = String.format("http://%s:%d/", getDevServicesAwareHost(), container.getMappedPort(8025));
        return ImmutableMap.of(
            "quarkus.mailer.port",
            container.getMappedPort(1025).toString(),
            "quarkus.mailer.host",
            getDevServicesAwareHost(),
            "quarkus.mailer.mock",
            "false"
        );
    }

    private String getDevServicesAwareHost() {
        return containerNetworkId.isPresent() ? container.getCurrentContainerInfo().getConfig().getHostName() : container.getHost();
    }

    @Override
    public void stop() {
        if (container != null) {
            container.stop();
            container = null;
        }
    }

    @Override
    public void inject(TestInjector testInjector) {
        testInjector.injectIntoFields(mailServerUrl, new TestInjector.AnnotatedAndMatchesType(InjectMailServer.class, String.class));
    }
}
