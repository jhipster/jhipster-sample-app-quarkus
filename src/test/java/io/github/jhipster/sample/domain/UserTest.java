package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.TestUtil;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void testUserEquals() throws Exception {
        TestUtil.equalsVerifier(User.class);
        var user1 = new User();
        user1.id = 1l;
        var user2 = new User();
        user2.id = user1.id;
        assertThat(user1).isEqualTo(user2);
        user2.id = 2l;
        assertThat(user1).isNotEqualTo(user2);
        user1.id = null;
        assertThat(user1).isNotEqualTo(user2);
    }
}
