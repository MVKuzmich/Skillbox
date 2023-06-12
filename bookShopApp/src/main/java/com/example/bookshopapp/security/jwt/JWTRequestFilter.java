package com.example.bookshopapp.security.jwt;


import com.example.bookshopapp.data.user.UserEntity;
import com.example.bookshopapp.repository.UserRepository;
import com.example.bookshopapp.security.BookShopUserDetailsService;
import com.example.bookshopapp.security.SecurityUser;
import com.example.bookshopapp.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTRequestFilter extends OncePerRequestFilter {

    private final BookShopUserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;
    private final HandlerExceptionResolver handlerExceptionResolver;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException, RuntimeException {
        String token = null;
        String username = null;
        Cookie[] cookies = httpServletRequest.getCookies();
        try {
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        token = cookie.getValue();
                        username = jwtUtil.extractUsername(token);
                    }

                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityUser userDetails =
                                (SecurityUser) userDetailsService.loadUserByUsername(username);
                        if (jwtUtil.validateToken(token, userDetails)) {
                            UsernamePasswordAuthenticationToken authenticationToken =
                                    new UsernamePasswordAuthenticationToken(
                                            userDetails, null, userDetails.getAuthorities());

                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                    }
                }
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (ExpiredJwtException ex) {

        }
        catch(JwtException ex) {
            handlerExceptionResolver.resolveException(httpServletRequest, httpServletResponse, null, ex);
        }

    }
}
