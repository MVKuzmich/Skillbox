package com.example.bookshopapp.errors;

import com.example.bookshopapp.dto.UserContactConfirmationResponse;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlerController {


    @ExceptionHandler(EmptySearchException.class)
    public String handleEmptySearchException(EmptySearchException e, RedirectAttributes redirectAttributes) {
        log.warn(e.getLocalizedMessage());
        redirectAttributes.addFlashAttribute("searchError", e);
        return "redirect:/";
    }

    @ExceptionHandler({AuthenticationException.class})
    @ResponseBody
    public ResponseEntity<UserContactConfirmationResponse> handleAuthenticationException(AuthenticationException ex) {

        UserContactConfirmationResponse response = new UserContactConfirmationResponse();
        response.setResult("Username or password does not exist");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler({JwtException.class})
    @ResponseBody
    public ResponseEntity<UserContactConfirmationResponse> handleJwtException(JwtException ex, HttpServletRequest request) {

        UserContactConfirmationResponse response = new UserContactConfirmationResponse();
        response.setResult("Token can not be trust");

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}
