package io.github.jhipster.sample.web.rest.errors;

final class FieldErrorVM {

    public final String objectName;
    public final String field;
    public final String message;

    public FieldErrorVM(String objectName, String message, String field) {
        this.objectName = objectName;
        this.field = field;
        this.message = message;
    }
}
