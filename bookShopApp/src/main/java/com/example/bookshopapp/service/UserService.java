package com.example.bookshopapp.service;

import com.example.bookshopapp.data.enums.ContactType;
import com.example.bookshopapp.data.user.UserContactEntity;
import com.example.bookshopapp.data.user.UserEntity;
import com.example.bookshopapp.repository.UserContactRepository;
import com.example.bookshopapp.repository.UserRepository;
import com.example.bookshopapp.dto.UserContactConfirmationPayload;
import com.example.bookshopapp.dto.UserContactConfirmationResponse;
import com.example.bookshopapp.dto.UserRegistrationForm;
import com.example.bookshopapp.security.BookShopUserDetailsService;
import com.example.bookshopapp.security.SecurityUser;
import com.example.bookshopapp.security.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final UserContactRepository contactRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final BookShopUserDetailsService userDetailsService;

    public UserEntity getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        // TODO: 28.05.2023 method is used only for temporal logic -> refactor when user defining will be realized
    }

    @Transactional
    public void registerNewUser(UserRegistrationForm userRegistrationForm) {
        if (userRepository.findUserByEmail(userRegistrationForm.getEmail()) == null) {
            UserEntity user = userRepository.save(new UserEntity(
                    passwordEncoder.encode(userRegistrationForm.getPassword()),
                    LocalDateTime.now(),
                    0,
                    userRegistrationForm.getName()
            ));
            contactRepository.save(new UserContactEntity(
                    user, ContactType.EMAIL, (short) 1, "111", 3, LocalDateTime.now(), userRegistrationForm.getEmail()));
            contactRepository.save(new UserContactEntity(
                    user, ContactType.PHONE, (short) 1, "111", 3, LocalDateTime.now(), userRegistrationForm.getPhone()));
        }
        // TODO: 01.06.2023 Обработка случая, когда при регистрации вводятся данные существующего пользователя 
    }

    public UserContactConfirmationResponse login(UserContactConfirmationPayload payload) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                        payload.getCode()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserContactConfirmationResponse response = new UserContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    public UserContactConfirmationResponse jwtLogin(UserContactConfirmationPayload payload) throws UsernameNotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                payload.getCode()));
        SecurityUser userDetails =
                (SecurityUser) userDetailsService.loadUserByUsername(payload.getContact());
        String jwtToken = jwtUtil.generateToken(userDetails);
        UserContactConfirmationResponse response = new UserContactConfirmationResponse();
        response.setResult(jwtToken);
        return response;
    }

    public UserEntity getCurrentUser() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}
