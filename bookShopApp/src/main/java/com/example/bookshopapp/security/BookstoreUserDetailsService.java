package com.example.bookshopapp.security;

import com.example.bookshopapp.data.user.UserEntity;
import com.example.bookshopapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BookstoreUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public BookstoreUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("user not found doh!");
        } else {
            return new BookstoreUserDetails(user);
        }
    }
}
