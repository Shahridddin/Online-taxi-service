package uz.pdp.online_taxi_service.configuration;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.online_taxi_service.enums.Role;


import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final String email;
    private final boolean isActive;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String username, String password, String email, boolean isActive, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isActive = isActive;
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name())); // ✅ `SimpleGrantedAuthority` ishlatilmoqda
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
        return username;
    }

    public String getEmail() {
        return email;
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
        return isActive;
    }
}
