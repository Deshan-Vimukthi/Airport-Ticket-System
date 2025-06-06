package com.example.airport.webConfigaration;

import com.example.airport.users.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record CustomUserDetails(User user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Single role per user
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName().toUpperCase()));
    }

    // Implement other UserDetails methods
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getUserStatus().getId() != 3;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getUserStatus().getId() != 2;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getUserStatus().getId() == 1;
    }
}

