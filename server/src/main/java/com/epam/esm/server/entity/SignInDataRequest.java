package com.epam.esm.server.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class SignInDataRequest {

    @NotNull
    @Size(min = 5, max = 50)
    private String login;
    @NotNull
    @Size(min = 6, max = 50)
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
        SignInDataRequest that = (SignInDataRequest) o;
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
