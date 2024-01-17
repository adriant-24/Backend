package com.shop.onlineshop.filter;

import jakarta.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.stream.Collectors;

public class AuthenticatedUserLoggingFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(AuthenticatedUserLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null){
            String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining());
            logger.info("Successfully logged in user: " + authentication.getName()+"./n" +
                    "Roles: " + authorities);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
