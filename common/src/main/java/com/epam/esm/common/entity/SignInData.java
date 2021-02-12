package com.epam.esm.common.entity;

import java.util.Objects;

public class SignInData {

    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public SignInData setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SignInData setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignInData that = (SignInData) o;
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
