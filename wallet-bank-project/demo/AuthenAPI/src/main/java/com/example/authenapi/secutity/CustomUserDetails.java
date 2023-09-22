package com.example.authenapi.secutity;

import com.example.authenapi.entity.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomUserDetails implements UserDetails {

    @Getter
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        user.getRoles().forEach(role -> roles.add(new SimpleGrantedAuthority("ROLE_" + role))); // ROLE_USER, ROLE_ADMIN
        return roles;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public String getFullname() {
        return user.getFullName();
    }

    public String getUniqueIdName() {
        return user.getUniqueIdName();
    }

    public String getUniqueIdValue() {
        return user.getUniqueIdValue();
    }

    public String getPhoneNumber() {
        return user.getPhoneNumber();
    }

    public String getCustomerType() {
        return user.getCustomerType();
    }

    public String getCustomerNo() {
        return user.getCustomerNo();
    }

    public String getAccountNo() {
        return user.getAccountNo();
    }

    public String getClientId() {
        return user.getClientId();
    }

    public String getClientSecret() {
        return user.getClientSecret();
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