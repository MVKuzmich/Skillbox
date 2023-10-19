package com.example.bookshopapp.controllers;

import com.example.bookshopapp.dto.UserContactConfirmationPayload;
import com.example.bookshopapp.dto.UserContactConfirmationResponse;
import com.example.bookshopapp.dto.UserRegistrationForm;
import com.example.bookshopapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController extends BaseController {

    private final UserService userService;

    @GetMapping("/signin")
    public String handleSignInPage() {

        return "signin";
    }

    @GetMapping("/signup")
    public String handleSignUpPage(Model model) {
        model.addAttribute("regForm", new UserRegistrationForm());
        return "signup";
    }

    //заглушка для работы кнопки подтверждения
    @PostMapping("/requestContactConfirmation")
    @ResponseBody
    public UserContactConfirmationResponse handleRequestContactConfirmation(@RequestBody UserContactConfirmationPayload payload) {
        UserContactConfirmationResponse response = new UserContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    //заглушка для работы кнопки подтверждения
    @PostMapping("/approveContact")
    @ResponseBody
    public UserContactConfirmationResponse handleApproveContact(@RequestBody UserContactConfirmationPayload payload) {
        UserContactConfirmationResponse response = new UserContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    //
    @PostMapping("/register")
    public String handleUserRegistration(UserRegistrationForm userRegistrationForm, Model model) {
        userService.registerNewUser(userRegistrationForm);
        model.addAttribute("registrationOk", true);
        return "signin";
    }


    //метод при работе с JWT
    @PostMapping("/login")
    @ResponseBody
    public UserContactConfirmationResponse handleLogin(@RequestBody UserContactConfirmationPayload payload,
                                                       HttpServletResponse httpServletResponse) {
        UserContactConfirmationResponse loginResponse = userService.jwtLogin(payload);
        Cookie cookie = new Cookie("token", loginResponse.getResult());
        httpServletResponse.addCookie(cookie);
        return loginResponse;
    }


    @GetMapping("/my")
    public String handleMy() {

        return "my";
    }

    @GetMapping("/profile")
    public String handleProfile(Model model) {
        model.addAttribute("currentUser", userService.getCurrentUser());
        return "profile";
    }
}


