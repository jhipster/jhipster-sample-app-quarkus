package io.github.jhipster.sample.web.rest.vm;

/**
 * View Model object for storing the user's key and password.
 */
public class KeyAndPasswordVM {
    public String key;

    public String newPassword;

    @Override
    public String toString() {
        return "KeyAndPasswordVM{" + "key='" + key + '\'' + ", newPassword='" + newPassword + '\'' + '}';
    }
}
