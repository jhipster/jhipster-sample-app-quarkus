package io.github.jhipster.sample.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LabelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Label getLabelSample1() {
        Label label = new Label();
        label.id = 1L;
        label.label = "label1";
        return label;
    }

    public static Label getLabelSample2() {
        Label label = new Label();
        label.id = 2L;
        label.label = "label2";
        return label;
    }

    public static Label getLabelRandomSampleGenerator() {
        Label label = new Label();
        label.id = longCount.incrementAndGet();
        label.label = UUID.randomUUID().toString();
        return label;
    }
}
