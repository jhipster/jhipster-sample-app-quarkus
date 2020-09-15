package io.github.jhipster.sample.web.rest.vm;

import io.quarkus.runtime.annotations.RegisterForReflection;

import io.github.jhipster.sample.service.dto.UserDTO;
import javax.validation.constraints.Size;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
@RegisterForReflection
public class ManagedUserVM extends UserDTO {
    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    public String password;

    @Override
    public String toString() {
        return "ManagedUserVM{" + super.toString() + "} ";
    }
}
