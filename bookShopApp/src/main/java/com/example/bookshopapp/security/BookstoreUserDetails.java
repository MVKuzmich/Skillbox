package com.example.bookshopapp.security;

import com.example.bookshopapp.data.enums.ContactType;
import com.example.bookshopapp.data.user.UserContactEntity;
import com.example.bookshopapp.data.user.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class BookstoreUserDetails implements UserDetails {

    private final UserEntity user;

    public BookstoreUserDetails(UserEntity user) {
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return user.getHash();
    }

    @Override
    public String getUsername() {
        return user.getUserContactSet().stream()
                .filter(contact -> contact.getType().name().equalsIgnoreCase("EMAIL"))
                .findFirst().orElseThrow(() -> new RuntimeException("email does not exist")).getContact();
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
