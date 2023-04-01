package io.github.jhipster.sample.utility;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;
import static javax.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.junit.jupiter.api.Assertions.fail;

import io.github.jhipster.sample.web.rest.vm.LoginVM;
import io.github.jhipster.sample.web.rest.vm.ManagedUserVM;
import io.restassured.response.Response;
import io.vertx.core.json.JsonObject;
import java.util.regex.Pattern;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

/**
 * Base class encapsulating login/logout functions required by all Integration
 * tests. RestAssured context is derived from <code>@TestHTTPEndpoint</code>
 * annotation defined in the derived class.
 */
public abstract class IntegrationTestBase {

    private static final Pattern ACTIVATION_KEY_PATTERN = Pattern.compile(".*key=(\\w+).*", Pattern.MULTILINE);

    protected void registerAndActivateUser(ManagedUserVM user) {
        registerUser(user);
        activateUser(user.email);
    }

    protected String getMailServerUrl() {
        return "http://localhost";
    }

    protected String authenticateUser(String username, String password) {
        //Authenticating user
        var login = new LoginVM();
        login.username = username;
        login.password = password;

        var token = given()
            .body(login)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
            .when()
            .post("/authenticate")
            .then()
            .statusCode(OK.getStatusCode())
            .body("id_token", instanceOf(String.class))
            .body("id_token", notNullValue())
            .header(HttpHeaders.AUTHORIZATION, not(blankOrNullString()))
            .extract()
            .path("id_token");

        return (String) token;
    }

    private void registerUser(ManagedUserVM user) {
        given()
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .body(user)
            .when()
            .post("/register")
            .then()
            .statusCode(CREATED.getStatusCode());
    }

    private void activateUser(String email) {
        given()
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .when()
            .get("/activate?key={key}", getAccountActivationKeyFromEmailMessage(email))
            .then()
            .statusCode(NO_CONTENT.getStatusCode());
    }

    private String getAccountActivationKeyFromEmailMessage(String toAddress) {
        Response mailServerResponse = given()
            .contentType(APPLICATION_JSON)
            .when()
            .get(getMailServerUrl() + "api/v2/search?kind=to&query={toAddress}&limit=1", toAddress);

        assertThat(mailServerResponse.statusCode()).isEqualTo(OK.getStatusCode());
        assertThat(mailServerResponse.getContentType()).isEqualTo(APPLICATION_JSON);

        JsonObject jsonResponse = new JsonObject(mailServerResponse.getBody().asString());
        assertThat(jsonResponse.getString("count")).isEqualTo("1");

        String activationEmail = jsonResponse.getJsonArray("items").getJsonObject(0).getJsonObject("Content").getString("Body");

        return extractKeyFromEmailMessage(activationEmail);
    }

    private String extractKeyFromEmailMessage(String emailMessage) {
        var matcher = ACTIVATION_KEY_PATTERN.matcher(emailMessage);

        if (!matcher.find()) {
            fail("User account activation key not found in the activation mail");
        }

        return matcher.group(1);
    }
}
