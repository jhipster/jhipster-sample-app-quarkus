package io.github.jhipster.sample.config;
import org.junit.jupiter.api.Test;

import javax.ws.rs.ext.ParamConverter;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LocalDateProviderTest {

    private LocalDateProvider localDateProvider = new LocalDateProvider();

    @Test
    void should_ReturnParamConverterAndConverterConverts(){
        final ParamConverter<LocalDate> converter = localDateProvider.getConverter(LocalDate.class, null, null);
        assertThat(converter).isNotNull();
    }

    @Test
    void should_ConvertLocalDateToString(){
        final ParamConverter<LocalDate> converter = localDateProvider.getConverter(LocalDate.class, null, null);
        final LocalDate now = LocalDate.now();
        final String expected = LocalDateProvider.dateFormatter.format(now);
        assertThat(converter.toString(now)).isEqualTo(expected);
    }

    @Test
    void should_ConvertStringToLocalDate(){
        final ParamConverter<LocalDate> converter = localDateProvider.getConverter(LocalDate.class, null, null);
        final LocalDate date = LocalDate.now();
        final String dateString = LocalDateProvider.dateFormatter.format(date);
        assertThat(converter.fromString(dateString)).isEqualTo(date);
    }
}
