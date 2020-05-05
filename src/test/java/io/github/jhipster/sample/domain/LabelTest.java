package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.TestUtil;
import org.junit.jupiter.api.Test;


public class LabelTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Label.class);
        Label label1 = new Label();
        label1.id = 1L;
        Label label2 = new Label();
        label2.id = label1.id;
        assertThat(label1).isEqualTo(label2);
        label2.id = 2L;
        assertThat(label1).isNotEqualTo(label2);
        label1.id = null;
        assertThat(label1).isNotEqualTo(label2);
    }
}
