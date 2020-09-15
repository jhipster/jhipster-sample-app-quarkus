package io.github.jhipster.sample.web.rest.vm;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * View Model object for storing the user's key and password.
 */
@RegisterForReflection
public class KeyAndPasswordVM {
    public String key;

    public String newPassword;

    @Override
    public String toString() {
        return "KeyAndPasswordVM{" + "key='" + key + '\'' + ", newPassword='" + newPassword + '\'' + '}';
    }
}
