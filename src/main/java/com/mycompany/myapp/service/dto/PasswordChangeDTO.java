package com.mycompany.myapp.service.dto;

/**
 * A DTO representing a password change required data - current and new password.
 */
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
