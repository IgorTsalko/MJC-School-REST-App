package com.epam.esm.common.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails {

    private final String login;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String login,
                           String password,
                           String role) {
        this.login = login;
        this.password = password;
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsImpl that = (UserDetailsImpl) o;
        return Objects.equals(login, that.login)
                && Objects.equals(password, that.password)
                && Objects.equals(authorities, that.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, authorities);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
