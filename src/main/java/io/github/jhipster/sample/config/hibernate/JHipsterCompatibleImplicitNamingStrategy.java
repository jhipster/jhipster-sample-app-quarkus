package io.github.jhipster.sample.config.hibernate;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitJoinTableNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;

public class JHipsterCompatibleImplicitNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {

    @Override
    public Identifier determineJoinTableName(ImplicitJoinTableNameSource source) {
        String joinedName = String.join("_", source.getOwningPhysicalTableName(), source.getAssociationOwningAttributePath().getProperty());
        return toIdentifier(joinedName, source.getBuildingContext());
    }
}
