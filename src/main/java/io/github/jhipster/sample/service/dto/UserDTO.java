package io.github.jhipster.sample.service.dto;

import io.github.jhipster.sample.config.Constants;
import io.github.jhipster.sample.domain.User;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with his authorities.
 */
@RegisterForReflection
public class UserDTO {

    public Long id;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    public String login;

    @Size(max = 50)
    public String firstName;

    @Size(max = 50)
    public String lastName;

    @Email
    @Size(min = 5, max = 254)
    public String email;

    @Size(max = 256)
    public String imageUrl;

    public Boolean activated = false;

    @Size(min = 2, max = 10)
    public String langKey;

    public String createdBy;

    public Instant createdDate;

    public String lastModifiedBy;

    public Instant lastModifiedDate;

    public Set<String> authorities;

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(User user) {
        this.id = user.id;
        this.login = user.login;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.email = user.email;
        this.activated = user.activated;
        this.imageUrl = user.imageUrl;
        this.langKey = user.langKey;
        this.createdBy = user.createdBy;
        this.createdDate = user.createdDate;
        this.lastModifiedBy = user.lastModifiedBy;
        this.lastModifiedDate = user.lastModifiedDate;
        this.authorities = user.authorities.stream().map(authority -> authority.name).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return (
            "UserDTO{" +
            "login='" +
            login +
            '\'' +
            ", firstName='" +
            firstName +
            '\'' +
            ", lastName='" +
            lastName +
            '\'' +
            ", email='" +
            email +
            '\'' +
            ", imageUrl='" +
            imageUrl +
            '\'' +
            ", activated=" +
            activated +
            ", langKey='" +
            langKey +
            '\'' +
            ", createdBy=" +
            createdBy +
            ", createdDate=" +
            createdDate +
            ", lastModifiedBy='" +
            lastModifiedBy +
            '\'' +
            ", lastModifiedDate=" +
            lastModifiedDate +
            ", authorities=" +
            authorities +
            "}"
        );
    }
}
