package io.github.jhipster.sample;

import static io.github.jhipster.sample.config.Constants.DATE_TIME_FORMAT;
import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.vm.LoginVM;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

/**
 * Utility class for testing REST controllers.
 */
public final class TestUtil {

    private static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
        .ofPattern(DATE_TIME_FORMAT)
        .withZone(ZoneId.of("UTC"));

    public static String formatDateTime(Temporal temporal) {
        return DATE_TIME_FORMATTER.format(temporal);
    }

    /**
     * Verifies the equals/hashcode contract on the domain object.
     */
    public static <T> void equalsVerifier(Class<T> clazz) throws Exception {
        T domainObject1 = clazz.getConstructor().newInstance();
        assertThat(domainObject1.toString()).isNotNull();
        assertThat(domainObject1).isEqualTo(domainObject1);
        assertThat(domainObject1.hashCode()).isEqualTo(domainObject1.hashCode());
        // Test with an instance of another class
        Object testOtherObject = new Object();
        assertThat(domainObject1).isNotEqualTo(testOtherObject);
        assertThat(domainObject1).isNotEqualTo(null);
        // Test with an instance of the same class
        T domainObject2 = clazz.getConstructor().newInstance();
        assertThat(domainObject1).isNotEqualTo(domainObject2);
        // HashCodes are equals because the objects are not persisted yet
        assertThat(domainObject1.hashCode()).isEqualTo(domainObject2.hashCode());
    }

    public static String getAdminToken() {
        return getToken("admin", "admin");
    }

    public static String getToken(String username, String password) {
        //Authenticating user
        var login = new LoginVM();
        login.username = username;
        login.password = password;

        return given()
            .body(login)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
            .when()
            .post("/api/authenticate")
            .then()
            .statusCode(OK.getStatusCode())
            .body("id_token", instanceOf(String.class))
            .body("id_token", notNullValue())
            .header(HttpHeaders.AUTHORIZATION, not(blankOrNullString()))
            .extract()
            .path("id_token");
    }

    public static ObjectMapper jsonbObjectMapper() {
        final var config = new JsonbConfig().withDateFormat(DATE_TIME_FORMAT, null);
        final Jsonb jsonb = JsonbBuilder.create(config);
        return new ObjectMapper() {

            @Override
            public Object deserialize(ObjectMapperDeserializationContext context) {
                return jsonb.fromJson(context.getDataToDeserialize().asString(), context.getType());
            }

            @Override
            public Object serialize(ObjectMapperSerializationContext context) {
                return jsonb.toJson(context.getObjectToSerialize());
            }
        };
    }

    public static byte[] createByteArray(int size, String data) {
        byte[] byteArray = new byte[size];
        for (int i = 0; i < size; i++) {
            byteArray[i] = Byte.parseByte(data, 2);
        }
        return byteArray;
    }

    private TestUtil() {}
}
