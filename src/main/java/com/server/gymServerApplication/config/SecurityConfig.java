package com.server.gymServerApplication.config;

import com.server.gymServerApplication.exception.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@PropertySource("classpath:application.properties")
public class SecurityConfig {

//    @Value("${jwt.secret}")
//    private String SECRET;

    private final String[] PUBLIC_ENDPOINTS;

    private final String[] PUBLIC_ENDPOINTS_METHOD;

    private final String[] PUBLIC_ENDPOINTS_ADMIN;

    private final String[] PUBLIC_ENDPOINTS_AUTHOR;

    private final String[] PUBLIC_ENDPOINTS_ALL_ROLES;


    private final GlobalExceptionHandler globalExceptionHandler;

    public SecurityConfig(GlobalExceptionHandler globalExceptionHandler) {
        this.globalExceptionHandler = globalExceptionHandler;
        this.PUBLIC_ENDPOINTS = EndpointsConfig.getPublicEndpoints();
        this.PUBLIC_ENDPOINTS_METHOD = EndpointsConfig.getPublicEndpointsMethod();
        this.PUBLIC_ENDPOINTS_ADMIN = EndpointsConfig.getPublicEndpointsAdmin();
        this.PUBLIC_ENDPOINTS_AUTHOR = EndpointsConfig.getPublicEndpointsAuthor();
        this.PUBLIC_ENDPOINTS_ALL_ROLES = EndpointsConfig.getPublicEndPointsAllRoles();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests(request -> request
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable);


        return httpSecurity.build();
    }
//    @Bean
//    JwtDecoder jwtDecoder() {
//        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET.getBytes(), "HS512");
//        return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
//    }
//
//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
//            List<String> roles = jwt.getClaimAsStringList("roles");
//            if (roles != null) {
//                return AuthorityUtils.createAuthorityList(roles.toArray(new String[0]));
//            }
//            return AuthorityUtils.NO_AUTHORITIES;
//        });
//        return converter;
//    }
//
//
//
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
//
//    @Bean
//    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
}
