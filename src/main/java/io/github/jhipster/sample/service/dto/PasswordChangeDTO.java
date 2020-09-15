package io.github.jhipster.sample.service.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * A DTO representing a password change required data - current and new password.
 */
@RegisterForReflection
public class PasswordChangeDTO {
    public String currentPassword;
    public String newPassword;

    public PasswordChangeDTO() {
        // Empty constructor needed for Jackson.
    }

    public PasswordChangeDTO(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }
}
