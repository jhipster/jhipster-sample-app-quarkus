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
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A BankAccount.
 */
@Entity
@Table(name = "bank_account")
@Cacheable
@RegisterForReflection
public class BankAccount extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    public String name;

    @NotNull
    @Column(name = "balance", precision = 21, scale = 2, nullable = false)
    public BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonbTransient
    public User user;

    @OneToMany(mappedBy = "bankAccount")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public Set<Operation> operations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankAccount)) {
            return false;
        }
        return id != null && id.equals(((BankAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", balance=" + balance +
            "}";
    }

    public BankAccount update() {
        return update(this);
    }

    public BankAccount persistOrUpdate() {
        return persistOrUpdate(this);
    }

    public static BankAccount update(BankAccount bankAccount) {
        if (bankAccount == null) {
            throw new IllegalArgumentException("bankAccount can't be null");
        }
        var entity = BankAccount.<BankAccount>findById(bankAccount.id);
        if (entity != null) {
            entity.name = bankAccount.name;
            entity.balance = bankAccount.balance;
            entity.user = bankAccount.user;
            entity.operations = bankAccount.operations;
        }
        return entity;
    }

    public static BankAccount persistOrUpdate(BankAccount bankAccount) {
        if (bankAccount == null) {
            throw new IllegalArgumentException("bankAccount can't be null");
        }
        if (bankAccount.id == null) {
            persist(bankAccount);
            return bankAccount;
        } else {
            return update(bankAccount);
        }
    }

    public static PanacheQuery<BankAccount> findByUserIsCurrentUser() {
        return find("select bankAccount from BankAccount bankAccount where bankAccount.user.login = ?#{principal.username}");
    }

}
