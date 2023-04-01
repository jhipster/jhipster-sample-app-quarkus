package io.github.jhipster.sample.web.rest;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

import io.github.jhipster.sample.builder.UserBuilder;
import io.github.jhipster.sample.infrastructure.EmailServerResource;
import io.github.jhipster.sample.infrastructure.InjectMailServer;
import io.github.jhipster.sample.security.AuthoritiesConstants;
import io.github.jhipster.sample.utility.IntegrationTestBase;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import javax.ws.rs.core.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusIntegrationTest
@TestHTTPEndpoint(AccountResource.class)
@QuarkusTestResource(value = EmailServerResource.class, restrictToAnnotatedClass = true)
class AccountResourceIT extends IntegrationTestBase {

    @InjectMailServer
    String mailServerUrl;

    UserBuilder userBuilder;

    @BeforeEach
    void init() {
        userBuilder =
            UserBuilder
                .aUser()
                .withLogin("johndoe")
                .withPassword("Passw0rd")
                .withFirstName("John")
                .withLastName("Doe")
                .withEmail("john.doe@jhipster.tech")
                .withImageUrl("http://placehold.it/50x50")
                .withLangKey("en");
    }

    @Test
    void should_allow_authenticated_user_to_retrieve_own_account_details() {
        var user = userBuilder.buildVM();

        registerAndActivateUser(user);

        given()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + authenticateUser(user.login, user.password))
            .when()
            .get("/account")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .body("login", is(user.login))
            .body("firstName", is(user.firstName))
            .body("lastName", is(user.lastName))
            .body("email", is(user.email))
            .body("imageUrl", is(user.imageUrl))
            .body("langKey", is(user.langKey))
            .body("authorities", hasItems(AuthoritiesConstants.USER));
    }

    @Override
    public String getMailServerUrl() {
        return mailServerUrl;
    }
}
