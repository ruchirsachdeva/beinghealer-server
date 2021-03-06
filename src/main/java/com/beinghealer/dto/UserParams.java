package com.beinghealer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.beinghealer.model.Role;
import com.beinghealer.model.User;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

@ToString
@EqualsAndHashCode
public final class UserParams {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(UserParams.class);

    @NotNull
    @Size(min = 1, max = 255)
    @Email
    private final String email;

    @NotNull
    @Size(min = 8, max = 100)
    private final String password;


    @NotNull
    @Size(min = 1, max = 100)
    private final String name;

    @NonNull
    private final String role;

    public UserParams(@JsonProperty("email") String email,
                      @JsonProperty("password") String password,
                      @JsonProperty("name") String name,
                      @JsonProperty("role") String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

    public Optional<String> getEncodedPassword() {
        return Optional.ofNullable(password).map(p -> new BCryptPasswordEncoder().encode(p));
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }


    public Optional<String> getRole() {
        return Optional.ofNullable(role);
    }


    public User toUser() {
        User user = new User();
        user.setUsername(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password != null ? password : "password"));
        user.setName(name);
        user.setRole(new Role(role));
        return user;
    }


}
