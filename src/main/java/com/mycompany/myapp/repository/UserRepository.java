package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Hibernate Panache JPA repository for the {@link User} entity.
 */
@SuppressWarnings("unused")
@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public Optional<User> findOneByActivationKey(String activationKey) {
        return find("activationKey", activationKey).firstResultOptional();
    }

    public List<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime) {
        return list("activated = false and activationKey not null and createdDate <= ?1", dateTime);
    }

    public Optional<User> findOneByResetKey(String resetKey) {
        return find("resetKey", resetKey).firstResultOptional();
    }

    public Optional<User> findOneByEmailIgnoreCase(String email) {
        return find("LOWER(email) = LOWER(?1)", email).firstResultOptional();
    }

    public Optional<User> findOneByLogin(String login) {
        return find("login", login).firstResultOptional();
    }

    public Optional<User> findOneWithAuthoritiesById(Long id) {
        return find("FROM User u LEFT JOIN FETCH u.authorities WHERE u.id = ?1", id).firstResultOptional();
    }

    public Optional<User> findOneWithAuthoritiesByLogin(String login) {
        return find("FROM User u LEFT JOIN FETCH u.authorities WHERE u.login = ?1", login)
//            .withHint(QueryHints.HINT_CACHEABLE, "true")
            .firstResultOptional();
    }

    public Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email) {
        return find("FROM User u LEFT JOIN FETCH u.authorities WHERE LOWER(u.login) = LOWER(?1)", email)
//            .withHint(QueryHints.HINT_CACHEABLE, "true")
            .firstResultOptional();
    }

    public List<User> findAllByLoginNot(Page page, String login) {
        return find("login != ?1", login).page(page).list();
    }

}
