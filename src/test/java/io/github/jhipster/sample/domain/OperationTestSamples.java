package io.github.jhipster.sample.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OperationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Operation getOperationSample1() {
        Operation operation = new Operation();
        operation.id = 1L;
        operation.description = "description1";
        return operation;
    }

    public static Operation getOperationSample2() {
        Operation operation = new Operation();
        operation.id = 2L;
        operation.description = "description2";
        return operation;
    }

    public static Operation getOperationRandomSampleGenerator() {
        Operation operation = new Operation();
        operation.id = longCount.incrementAndGet();
        operation.description = UUID.randomUUID().toString();
        return operation;
    }
}
