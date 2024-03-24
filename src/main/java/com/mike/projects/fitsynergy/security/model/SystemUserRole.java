package com.mike.projects.fitsynergy.security.model;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum SystemUserRole {
    CLIENT(Sets.newHashSet()),
    TRAINER(Sets.newHashSet()),
    ADMIN(Sets.newHashSet());

    private final Set<SystemUserPermission> permissions;

    SystemUserRole(Set<SystemUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<SystemUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> grantedAuthorities = permissions.stream()
                .map(a -> new SimpleGrantedAuthority(a.getPermission()))
                .collect(Collectors.toSet());
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return grantedAuthorities;
    }
}
