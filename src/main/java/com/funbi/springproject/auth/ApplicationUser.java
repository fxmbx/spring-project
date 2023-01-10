package com.funbi.springproject.auth;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class ApplicationUser implements UserDetails {

    private final Set<? extends GrantedAuthority>  getGrantedAuthorities;

    private final String password;
    private final String username;
    private final boolean isAccountNonExpired; 
    private final boolean isAccountNonLocked; 
    private final boolean isCredentialsNonExpired; 
    private final boolean isEnabled; 

    public ApplicationUser(
        String password,
        String username,
        Set<? extends GrantedAuthority> getGrantedAuthorities,
        boolean isAccountNonExpired,
        boolean isAccountNonLocked, 
        boolean isCredentialsNonExpired,
        boolean isEnabled) {
        this.getGrantedAuthorities = getGrantedAuthorities;
        this.password = password;
        this.username = username;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getGrantedAuthorities;
    }

    @Override
    public String getPassword() {
        
        return this.password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
       
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
    
}
