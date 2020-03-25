package com.mycompany.myapp.domain;

import com.mycompany.myapp.config.Constants;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.BatchSize;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
* A user.
*/
@Entity
@Table(name = "jhi_user")
//@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
//The entity inherits `PanacheEntity` to take advantage of the ID and the public fields
public class User extends PanacheEntity implements Serializable {

   private static final long serialVersionUID = 1L;

   @NotNull
   @Pattern(regexp = Constants.LOGIN_REGEX)
   @Size(min = 1, max = 50)
   @Column(length = 50, unique = true, nullable = false)
   public String login;

   @JsonbTransient
   @NotNull
   @Size(min = 60, max = 60)
   @Column(name = "password_hash", length = 60, nullable = false)
   public String password;

   @Size(max = 50)
   @Column(name = "first_name", length = 50)
   public String firstName;

   @Size(max = 50)
   @Column(name = "last_name", length = 50)
   public String lastName;

   @Email
   @Size(min = 5, max = 254)
   @Column(length = 254, unique = true)
   public String email;

   @NotNull
   @Column(nullable = false)
   public boolean activated = false;

   @Size(min = 2, max = 10)
   @Column(name = "lang_key", length = 10)
   public String langKey;

   @Size(max = 256)
   @Column(name = "image_url", length = 256)
   public String imageUrl;

   @Size(max = 20)
   @Column(name = "activation_key", length = 20)
   @JsonbTransient
   public String activationKey;

   @Size(max = 20)
   @Column(name = "reset_key", length = 20)
   @JsonbTransient
   public String resetKey;

   @Column(name = "reset_date")
   public Instant resetDate = null;

   @JsonbTransient
   @ManyToMany
   @JoinTable(
       name = "jhi_user_authority",
       joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
       inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
//    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
   @BatchSize(size = 20)
   public Set<Authority> authorities = new HashSet<>();

   //To move to an audit mechanism
   //    @CreatedBy
   @Column(name = "created_by", nullable = false, length = 50, updatable = false)
   @JsonbTransient
   public String createdBy = "";

   //    @CreatedDate
   @Column(name = "created_date", updatable = false)
   @JsonbTransient
   public Instant createdDate = Instant.now();

   //    @LastModifiedBy
   @Column(name = "last_modified_by", length = 50)
   @JsonbTransient
   public String lastModifiedBy = "";

   //    @LastModifiedDate
   @Column(name = "last_modified_date")
   @JsonbTransient
   public Instant lastModifiedDate = Instant.now();

   @Override
   public boolean equals(Object o) {
       if (this == o) {
           return true;
       }
       if (!(o instanceof User)) {
           return false;
       }
       return id != null && id.equals(((User) o).id);
   }

   @Override
   public int hashCode() {
       return 31;
   }

   @Override
   public String toString() {
       return "User{" +
           "login='" + login + '\'' +
           ", firstName='" + firstName + '\'' +
           ", lastName='" + lastName + '\'' +
           ", email='" + email + '\'' +
           ", imageUrl='" + imageUrl + '\'' +
           ", activated='" + activated + '\'' +
           ", langKey='" + langKey + '\'' +
           ", activationKey='" + activationKey + '\'' +
           "}";
   }

}
