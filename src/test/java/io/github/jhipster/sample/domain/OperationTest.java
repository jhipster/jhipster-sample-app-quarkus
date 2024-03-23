package io.github.jhipster.sample.domain;

import static io.github.jhipster.sample.domain.BankAccountTestSamples.*;
import static io.github.jhipster.sample.domain.LabelTestSamples.*;
import static io.github.jhipster.sample.domain.OperationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.TestUtil;
import org.junit.jupiter.api.Test;

class OperationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Operation.class);
        Operation operation1 = getOperationSample1();
        Operation operation2 = new Operation();
        assertThat(operation1).isNotEqualTo(operation2);

        operation2.id = operation1.id;
        assertThat(operation1).isEqualTo(operation2);

        operation2 = getOperationSample2();
        assertThat(operation1).isNotEqualTo(operation2);
    }
}
