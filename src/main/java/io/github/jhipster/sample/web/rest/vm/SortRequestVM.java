package io.github.jhipster.sample.web.rest.vm;

import io.quarkus.panache.common.Sort;
import jakarta.ws.rs.QueryParam;
import java.util.List;

public class SortRequestVM {

    @QueryParam("sort")
    public List<String> sort;

    public Sort toSort() {
        Sort resultSort = null;

        for (String currentSort : sort) {
            var sortDetails = currentSort.split(",");
            var columnName = sortDetails[0];
            var direction = sortDetails.length > 1 ? sortDetails[1] : null;

            if (resultSort == null) {
                resultSort = Sort.by(columnName, toDirection(direction));
            } else {
                resultSort.and(columnName, toDirection(direction));
            }
        }

        return resultSort;
    }

    private Sort.Direction toDirection(String direction) {
        return "asc".equals(direction) ? Sort.Direction.Ascending : Sort.Direction.Descending;
    }

    @Override
    public String toString() {
        return "SortRequestVM{" + "sort='" + sort + '\'' + '}';
    }
}
