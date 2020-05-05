package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.TestUtil;
import org.junit.jupiter.api.Test;


public class BankAccountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankAccount.class);
        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.id = 1L;
        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.id = bankAccount1.id;
        assertThat(bankAccount1).isEqualTo(bankAccount2);
        bankAccount2.id = 2L;
        assertThat(bankAccount1).isNotEqualTo(bankAccount2);
        bankAccount1.id = null;
        assertThat(bankAccount1).isNotEqualTo(bankAccount2);
    }
}
