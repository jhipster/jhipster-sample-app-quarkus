package io.github.jhipster.sample.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BankAccountTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BankAccount getBankAccountSample1() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.id = 1L;
        bankAccount.name = "name1";
        return bankAccount;
    }

    public static BankAccount getBankAccountSample2() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.id = 2L;
        bankAccount.name = "name2";
        return bankAccount;
    }

    public static BankAccount getBankAccountRandomSampleGenerator() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.id = longCount.incrementAndGet();
        bankAccount.name = UUID.randomUUID().toString();
        return bankAccount;
    }
}
