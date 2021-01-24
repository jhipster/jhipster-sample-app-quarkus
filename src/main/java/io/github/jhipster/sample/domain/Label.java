package io.github.jhipster.sample.domain;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import javax.json.bind.annotation.JsonbTransient;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Label.
 */
@Entity
@Table(name = "label")
@Cacheable
@RegisterForReflection
public class Label extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "label", nullable = false)
    public String label;

    @ManyToMany(mappedBy = "labels")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonbTransient
    public Set<Operation> operations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Label)) {
            return false;
        }
        return id != null && id.equals(((Label) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Label{" +
            "id=" + id +
            ", label='" + label + "'" +
            "}";
    }

    public Label update() {
        return update(this);
    }

    public Label persistOrUpdate() {
        return persistOrUpdate(this);
    }

    public static Label update(Label label) {
        if (label == null) {
            throw new IllegalArgumentException("label can't be null");
        }
        var entity = Label.<Label>findById(label.id);
        if (entity != null) {
            entity.label = label.label;
            entity.operations = label.operations;
        }
        return entity;
    }

    public static Label persistOrUpdate(Label label) {
        if (label == null) {
            throw new IllegalArgumentException("label can't be null");
        }
        if (label.id == null) {
            persist(label);
            return label;
        } else {
            return update(label);
        }
    }


}
