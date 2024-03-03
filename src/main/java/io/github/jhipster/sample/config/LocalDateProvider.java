package io.github.jhipster.sample.config;

import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Provider
public class LocalDateProvider implements ParamConverterProvider {

    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Constants.LOCAL_DATE_FORMAT);

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (!rawType.equals(LocalDate.class)) {
            return null;
        }
        return new ParamConverter<>() {
            @Override
            public T fromString(String value) {
                return (T) LocalDate.parse(value, dateFormatter);
            }

            @Override
            public String toString(T value) {
                return ((LocalDate) value).format(dateFormatter);
            }
        };
    }
}
