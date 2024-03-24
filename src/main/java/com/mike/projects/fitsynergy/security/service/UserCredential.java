package com.mike.projects.fitsynergy.security.service;

import com.mike.projects.fitsynergy.users.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserCredential implements UserDetails {

    private final String username;
    private final String password;
    private final Collection<SimpleGrantedAuthority> authorities;
    private final Boolean isAccountNonExpired;
    private final Boolean isAccountNonLocked;
    private final Boolean isCredentialsNonExpired;
    private final Boolean isEnabled;

    public UserCredential(User user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.authorities = user.getUserDetails().getRole().getGrantedAuthorities();
        this.isAccountNonExpired = !user.getUserDetails().getIsExpired();
        this.isAccountNonLocked = !user.getUserDetails().getIsLocked();
        this.isCredentialsNonExpired = !user.getUserDetails().getIsCredentialsExpired();
        this.isEnabled = user.getUserDetails().getIsEnabled();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
