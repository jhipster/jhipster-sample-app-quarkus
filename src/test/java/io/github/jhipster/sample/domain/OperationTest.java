package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.TestUtil;
import org.junit.jupiter.api.Test;


public class OperationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Operation.class);
        Operation operation1 = new Operation();
        operation1.id = 1L;
        Operation operation2 = new Operation();
        operation2.id = operation1.id;
        assertThat(operation1).isEqualTo(operation2);
        operation2.id = 2L;
        assertThat(operation1).isNotEqualTo(operation2);
        operation1.id = null;
        assertThat(operation1).isNotEqualTo(operation2);
    }
}
