package com.shop.ordersservice.security;

import com.shop.ordersservice.filter.AuthenticatedUserLoggingFilter;
import com.shop.ordersservice.filter.CsrfCookieFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfiguration {

    CorsConfigProperties corsConfigProperties;

    @Autowired
    public SecurityConfiguration(CorsConfigProperties corsConfigProperties){
        this.corsConfigProperties = corsConfigProperties;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception {

        CsrfTokenRequestAttributeHandler requestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        requestAttributeHandler.setCsrfRequestAttributeName("_csrf");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloackRoleConverter());

        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
         //   .cors(corsConfigurer-> corsConfigurer.configurationSource(corsConfigurationSource()))
            .csrf(csrf->
                    csrf.csrfTokenRequestHandler(requestAttributeHandler)
                    .ignoringRequestMatchers("/actuator/**", "/api/testRefresh","/api/categories","/api/products/**", "/api/products", "/api/product")
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            )
            .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
            .addFilterAfter(new AuthenticatedUserLoggingFilter(), CsrfCookieFilter.class)
            .authorizeHttpRequests(configurer->
                    configurer
                           .requestMatchers("/swagger-ui/**"," /v3/api-docs/**",
                                   "/actuator/**","/api/testRefresh",
                                   "/actuator").permitAll()
//                               .requestMatchers("/items/showAddItemForm").hasRole("ADMIN")
                            .requestMatchers("/api/orders","/api/purchase").authenticated()
            )
            .oauth2ResourceServer (configurer->
                    configurer.
                            jwt(jwt->jwt.jwtAuthenticationConverter(jwtAuthenticationConverter))
            );

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(corsConfigProperties.getAllowedOrigins()));
        configuration.setAllowedMethods(List.of("*"));//corsConfigProperties.getAllowedMethods()
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(Arrays.asList(corsConfigProperties.getExposedHeaders()));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
