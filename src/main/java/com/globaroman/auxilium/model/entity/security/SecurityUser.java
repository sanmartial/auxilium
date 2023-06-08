package com.globaroman.auxilium.model.entity.security;

import com.globaroman.auxilium.model.entity.UserAUX;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SecurityUser implements UserDetails {
    private String password;
    private String username;
    private boolean isActive;
    private List<RoleAUX> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(roleAUX -> new SimpleGrantedAuthority(roleAUX.name()))
                .collect(Collectors.toSet());
    }

    public SecurityUser(String password, String username, boolean isActive, List<RoleAUX> authorities) {
        this.password = password;
        this.username = username;
        this.isActive = isActive;
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetails getUserFromUserAUX(UserAUX user) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getRole().getAuthorities()
        );
    }
}
