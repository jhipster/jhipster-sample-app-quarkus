package com.mycompany.myapp.domain;

import com.mycompany.myapp.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    @Test
    public void testUserEquals() throws Exception {
        TestUtil.equalsVerifier(User.class);
        var user1 = new User();
        user1.id = 1L;
        var user2 = new User();
        user2.id = user1.id;
        assertThat(user1).isEqualTo(user2);
        user2.id = 2L;
        assertThat(user1).isNotEqualTo(user2);
        user1.id = null;
        assertThat(user1).isNotEqualTo(user2);
    }

}
