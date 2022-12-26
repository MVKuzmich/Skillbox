package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.dto.SearchWordDto;
import com.example.bookshopapp.security.ContactConfirmationPayload;
import com.example.bookshopapp.security.ContactConfirmationResponse;
import com.example.bookshopapp.security.RegistrationForm;
import com.example.bookshopapp.service.BookstoreUserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserAuthenticationController {

    private final BookstoreUserRegisterService userRegisterService;

    @Autowired
    public UserAuthenticationController(BookstoreUserRegisterService userRegisterService) {
        this.userRegisterService = userRegisterService;
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }

    @GetMapping("/signin")
    public String signInPage() {

        return "signin";
    }

    @GetMapping("/signup")
    public String handleSignUp(Model model) {
        model.addAttribute("regForm", new RegistrationForm());
        return "signup";
    }

    //заглушка для работы кнопки подтверждения
    @PostMapping("/requestContactConfirmation")
    @ResponseBody
    public ContactConfirmationResponse handleRequestContactConfirmation(@RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(true);
        return response;
    }

    //заглушка для работы кнопки подтверждения
    @PostMapping("/approveContact")
    @ResponseBody
    public ContactConfirmationResponse handleApproveContact(@RequestBody ContactConfirmationPayload payload){
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(true);
        return response;
    }
//
    @PostMapping("/register")
    public String handleUserRegistration(RegistrationForm registrationForm, Model model) {
        userRegisterService.registerNewUser(registrationForm);
        model.addAttribute("registrationOk", true);
        return "signin";
    }

    @PostMapping("/login")
    @ResponseBody
    public ContactConfirmationResponse login(@RequestBody ContactConfirmationPayload payload) {
        return userRegisterService.login(payload);

    }


//    @PostMapping("/login")
//    @ResponseBody
//    public  ContactConfirmationResponse handleLogin(@RequestBody ContactConfirmationPayload payload,
//                                                    HttpServletResponse httpServletResponse){
//        ContactConfirmationResponse loginResponse = userRegister.jwtLogin(payload);
//        Cookie cookie = new Cookie("token",loginResponse.getResult());
//        httpServletResponse.addCookie(cookie);
//        return loginResponse;
//    }
//
    @GetMapping("/my")
    public String handleMy(){
        return "my";
    }

    @GetMapping("/profile")
    public String handleProfile(Model model){
        model.addAttribute("currentUser",userRegisterService.getCurrentUser());
        return "profile";
    }

    @GetMapping("/logout")
    public String handleLogout(HttpServletRequest request){
        HttpSession session = request.getSession();
        SecurityContextHolder.clearContext();
        if (session != null){
            session.invalidate();
        }

        for (Cookie cookie : request.getCookies()){
            cookie.setMaxAge(0);
        }
        return "redirect:/";
    }
}


