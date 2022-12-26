package com.example.bookshopapp.service;


import com.example.bookshopapp.data.enums.ContactType;
import com.example.bookshopapp.data.user.UserContactEntity;
import com.example.bookshopapp.data.user.UserEntity;
import com.example.bookshopapp.repository.UserContactRepository;
import com.example.bookshopapp.repository.UserRepository;
import com.example.bookshopapp.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookstoreUserRegisterService {

    private final UserRepository userRepository;
    private final UserContactRepository contactRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
//    private final BookstoreUserDetailsService bookstoreUserDetailsService;
//    private final JWTUtil jwtUtil;


    public void registerNewUser(RegistrationForm registrationForm) {
        if (userRepository.findUserByEmail(registrationForm.getEmail()) == null) {
            UserEntity user = userRepository.save(new UserEntity(
                    passwordEncoder.encode(registrationForm.getPassword()),
                    LocalDateTime.now(),
                    0,
                    registrationForm.getName()
            ));
            contactRepository.save(new UserContactEntity(
                    user, ContactType.EMAIL, (short) 1, "111", 3, LocalDateTime.now(), registrationForm.getEmail()));
            contactRepository.save(new UserContactEntity(
                    user, ContactType.PHONE, (short) 1, "111", 3, LocalDateTime.now(), registrationForm.getPhone()));
        }
    }

    public ContactConfirmationResponse login(ContactConfirmationPayload payload) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                        payload.getCode()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(true);
        return response;
    }
//
//    public ContactConfirmationResponse jwtLogin(ContactConfirmationPayload payload) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
//                payload.getCode()));
//        BookstoreUserDetails userDetails =
//                (BookstoreUserDetails) bookstoreUserDetailsService.loadUserByUsername(payload.getContact());
//        String jwtToken = jwtUtil.generateToken(userDetails);
//        ContactConfirmationResponse response = new ContactConfirmationResponse();
//        response.setResult(jwtToken);
//        return response;
//    }
//
//
    public Object getCurrentUser() {
        BookstoreUserDetails userDetails =
                (BookstoreUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }
}
