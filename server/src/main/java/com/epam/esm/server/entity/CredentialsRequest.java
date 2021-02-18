package com.epam.esm.server.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class CredentialsRequest {

    @NotNull
    @Size(min = 5, max = 50)
    @Pattern(regexp = "^[^\\s?@$%^()/]*$", message = "{invalid-login}")
    private String login;
    @NotNull
    @Size(min = 6, max = 50)
    @Pattern(regexp = "^[^\\s]*$", message = "{invalid-password}")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CredentialsRequest that = (CredentialsRequest) o;
        return Objects.equals(login, that.login)
                && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
