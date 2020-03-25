package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Authority;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthorityRepository implements PanacheRepositoryBase<Authority, String> {
}
