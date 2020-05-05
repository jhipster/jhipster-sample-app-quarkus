package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.security.AuthoritiesConstants;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

public class AuthorityTest {

    @Test
    public void testAuthorityEquals() {
        var authorityA = new Authority();
        assertThat(authorityA).isEqualTo(authorityA);
        assertThat(authorityA).isNotEqualTo(null);
        assertThat(authorityA).isNotEqualTo(new Object());
        assertThat(authorityA.hashCode()).isEqualTo(0);
        assertThat(authorityA.toString()).isNotNull();

        var authorityB = new Authority();
        assertThat(authorityA).isEqualTo(authorityB);

        authorityB.name = AuthoritiesConstants.ADMIN;
        assertThat(authorityA).isNotEqualTo(authorityB);

        authorityA.name = AuthoritiesConstants.USER;
        assertThat(authorityA).isNotEqualTo(authorityB);

        authorityB.name = AuthoritiesConstants.USER;
        assertThat(authorityA).isEqualTo(authorityB);
        assertThat(authorityA.hashCode()).isEqualTo(authorityB.hashCode());
    }
}
