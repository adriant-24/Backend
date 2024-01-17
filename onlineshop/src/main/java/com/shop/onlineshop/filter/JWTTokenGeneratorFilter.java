package com.shop.onlineshop.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

//@Component
public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

    @Value("${jwt.secret: 0e62c3f3c132c8c134557ad3cdb759e4ebf620309f8466610036f6d11e0e3485}")
    String jwtSecret;

    @Value("${jwt.header: Authorization}")
    String jwtHeader;

    @Value("${jwt.expiration-hours: 48}")
    long jwtExpirationHours;

    final long hourToMilisec = 24*60*60*1000;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null){
            String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining());
            SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            long expirationTime = new Date().getTime() + jwtExpirationHours * hourToMilisec;
            String jwtToken = Jwts.builder()
                    .issuer("Amy OnlineShop")
                    .subject("JWT Token")
                    .claim("username", authentication.getName())
                    .claim("authorities", authorities)
                    .issuedAt(new Date())
                    .expiration(new Date(expirationTime))
                    .signWith(secretKey)
                    .compact();
            response.setHeader(jwtHeader, jwtToken);
        }

        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/api/user/login");
    }
}
