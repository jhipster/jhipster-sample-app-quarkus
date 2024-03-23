package io.github.jhipster.sample.web.rest.errors;

import com.tietoevry.quarkus.resteasy.problem.ExceptionMapperBase;
import com.tietoevry.quarkus.resteasy.problem.HttpProblem;
import jakarta.annotation.Priority;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Exception Mapper for ConstraintViolationException from Bean Validation AP (Hibernate Validator).
 * Adds 'fieldErrors' field into `application/problem` responses.
 */
@Provider
@Priority(Priorities.USER - 1)
public final class ConstraintViolationExceptionMapper extends ExceptionMapperBase<ConstraintViolationException> {

    @Override
    protected HttpProblem toProblem(ConstraintViolationException exception) {
        List<FieldErrorVM> fieldErrors = exception
            .getConstraintViolations()
            .stream()
            .map(this::toFieldErrorVM)
            .collect(Collectors.toList());

        return HttpProblem.builder()
            .withStatus(Response.Status.BAD_REQUEST)
            .withTitle(Response.Status.BAD_REQUEST.getReasonPhrase())
            .with("fieldErrors", fieldErrors)
            .build();
    }

    private FieldErrorVM toFieldErrorVM(ConstraintViolation<?> constraintViolation) {
        return new FieldErrorVM(
            dtoName(constraintViolation.getLeafBean()),
            constraintViolation.getMessage(),
            dropMethodNameAndArgumentPositionFromPath(constraintViolation.getPropertyPath())
        );
    }

    private String dtoName(Object leafBean) {
        String name = leafBean.getClass().getSimpleName();
        return String.valueOf(name.charAt(0)).toLowerCase(Locale.ROOT) + name.substring(1);
    }

    private String dropMethodNameAndArgumentPositionFromPath(Path propertyPath) {
        Iterator<Path.Node> propertyPathIterator = propertyPath.iterator();
        propertyPathIterator.next();
        propertyPathIterator.next();

        List<String> allNamesExceptFirstTwo = new ArrayList<>();
        while (propertyPathIterator.hasNext()) {
            allNamesExceptFirstTwo.add(propertyPathIterator.next().getName());
        }

        return String.join(".", allNamesExceptFirstTwo);
    }
}
