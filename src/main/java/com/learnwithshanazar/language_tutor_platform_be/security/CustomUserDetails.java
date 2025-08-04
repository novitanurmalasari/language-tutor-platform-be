package com.learnwithshanazar.language_tutor_platform_be.security;

import com.learnwithshanazar.language_tutor_platform_be.entity.Admin;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetails implements UserDetails {

    private String id;
    private String username;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Admin admin) {
        this.id = admin.getId();
        this.username = admin.getUsername();
        this.email = admin.getEmail();
        this.password = admin.getPassword();
        this.authorities = Collections.singletonList(
                new SimpleGrantedAuthority(admin.getRole().name())
        );
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
}