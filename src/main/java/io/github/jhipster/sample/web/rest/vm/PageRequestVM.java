package io.github.jhipster.sample.web.rest.vm;

import io.quarkus.panache.common.Page;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public class PageRequestVM {

    @QueryParam("page")
    @DefaultValue("0")
    @Positive
    public int index;

    @QueryParam("size")
    @DefaultValue("50")
    @Positive
    public int size;

    public Page toPage() {
        return Page.of(index, size);
    }

    @Override
    public String toString() {
        return "PageRequestVM{" + "page=" + index + ", size=" + size + '}';
    }
}
