package com.shop.onlineshop.security;

import com.shop.onlineshop.filter.AuthenticatedUserLoggingFilter;
import com.shop.onlineshop.filter.CsrfCookieFilter;
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

  //  JWTTokenValidatorFilter jwtTokenValidatorFilter;
  //  JWTTokenGeneratorFilter jwtTokenGeneratorFilter;

    @Autowired
    public SecurityConfiguration(CorsConfigProperties corsConfigProperties){
        this.corsConfigProperties = corsConfigProperties;
    }
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(UserServiceImpl userService){
//        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//        auth.setUserDetailsService(userService);
//        auth.setPasswordEncoder(bCryptPasswordEncoder());
//        return auth;
//    }
//AuthorizationFilter
//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{noop}admin")
//                .roles("REGULAR_USER","ADMIN")
//                .build();
//        UserDetails user1 = User.builder()
//                .username("user1")
//                .password("{noop}user1")
//                .roles("REGULAR_USER")
//                .build();
//        Collection<UserDetails> users = new ArrayList<>();
//        users.add(admin);
//        users.add(user1);
//        return new InMemoryUserDetailsManager(users);
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception {

        CsrfTokenRequestAttributeHandler requestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        requestAttributeHandler.setCsrfRequestAttributeName("_csrf");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloackRoleConverter());

        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
           // .cors(corsConfigurer-> corsConfigurer.configurationSource(corsConfigurationSource()))
            .csrf(csrf->
                    csrf.csrfTokenRequestHandler(requestAttributeHandler)
                    .ignoringRequestMatchers("/actuator/busrefresh", "/api/categories","/api/products/**", "/api/products", "/api/product")
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            )
            .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
            .addFilterAfter(new AuthenticatedUserLoggingFilter(), CsrfCookieFilter.class)
            .authorizeHttpRequests(configurer->
                    configurer
                           .requestMatchers("/swagger-ui/**"," /v3/api-docs/**",
                                   "/actuator/**",
                                   "/actuator",
                                   "/api/user/testOrders",
                                   "/api/categories","/api/products/**", "/api/products", "/api/product").permitAll()
//                               .requestMatchers("/items/showAddItemForm").hasRole("ADMIN")
                            .requestMatchers("/api/user/**", "/api/order").authenticated()
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
