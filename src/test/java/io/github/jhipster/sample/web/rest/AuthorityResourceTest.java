package io.github.jhipster.sample.web.rest;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.*;

import io.github.jhipster.sample.TestUtil;
import io.github.jhipster.sample.security.AuthoritiesConstants;
import io.quarkus.liquibase.LiquibaseFactory;
import io.quarkus.test.junit.QuarkusTest;
import javax.inject.Inject;
import liquibase.Liquibase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
class AuthorityResourceTest {

    String adminToken;

    @Inject
    LiquibaseFactory liquibaseFactory;

    @BeforeEach
    public void authenticateAdmin() {
        this.adminToken = TestUtil.getAdminToken();
    }

    @BeforeEach
    public void databaseFixture() throws Exception {
        try (Liquibase liquibase = liquibaseFactory.createLiquibase()) {
            liquibase.dropAll();
            liquibase.validate();
            liquibase.update(liquibaseFactory.createContexts(), liquibaseFactory.createLabels());
        }
    }

    @Test
    public void getAllAuthorities() {
        given()
            .auth()
            .preemptive()
            .oauth2(adminToken)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .when()
            .get("/api/authorities")
            .then()
            .statusCode(OK.getStatusCode())
            .contentType(APPLICATION_JSON)
            .body("$", hasSize(greaterThan(0)))
            .body("$", hasItems(AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN));
    }
}
