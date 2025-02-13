package com.server.gymServerApplication.config;

import com.server.gymServerApplication.exception.GlobalExceptionHandler;
import com.server.gymServerApplication.service.TokenService;
import com.server.gymServerApplication.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.spec.SecretKeySpec;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@PropertySource("classpath:application.properties")
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String SECRET;

    private final String[] PUBLIC_ENDPOINTS;

    private final String[] PUBLIC_ENDPOINTS_METHOD;

    private final String[] PUBLIC_ENDPOINTS_ADMIN;

    private final String[] PUBLIC_ENDPOINTS_PT;

    private final String[] PUBLIC_ENDPOINTS_ALL_ROLES;

    private final TokenService tokenService;

    private final UserService userService;


    private final GlobalExceptionHandler globalExceptionHandler;

    public SecurityConfig(TokenService tokenService, UserService userService, GlobalExceptionHandler globalExceptionHandler) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.globalExceptionHandler = globalExceptionHandler;
        this.PUBLIC_ENDPOINTS = EndpointsConfig.getPublicEndpoints();
        this.PUBLIC_ENDPOINTS_METHOD = EndpointsConfig.getPublicEndpointsMethod();
        this.PUBLIC_ENDPOINTS_ADMIN = EndpointsConfig.getPublicEndpointsAdmin();
        this.PUBLIC_ENDPOINTS_PT = EndpointsConfig.getPublicEndpointsPT();
        this.PUBLIC_ENDPOINTS_ALL_ROLES = EndpointsConfig.getPublicEndPointsAllRoles();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests(request -> request
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(new CustomRequestMatcher(PUBLIC_ENDPOINTS_METHOD)).permitAll()
                        .requestMatchers(PUBLIC_ENDPOINTS_ALL_ROLES).hasAnyRole("ADMIN", "PT", "USER", "STAFF")
                        .requestMatchers(PUBLIC_ENDPOINTS_ADMIN).hasRole("ADMIN")
                        .requestMatchers(PUBLIC_ENDPOINTS_PT).hasRole("PT")
                        .anyRequest()
                        .authenticated()
                ).oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                        .authenticationEntryPoint(globalExceptionHandler).accessDeniedHandler((request, response, accessDeniedException) -> {
                            globalExceptionHandler.handleAccessDeniedException(request, response);
                        }))
                .userDetailsService(userService)

                .csrf(AbstractHttpConfigurer::disable);
        httpSecurity.addFilterBefore(new JwtAuthenticationFilter(jwtDecoder(), tokenService, userService),
                UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }


    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET.getBytes(), "HmacSHA512");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
    }


    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<String> roles = jwt.getClaimAsStringList("roles");
            if (roles != null) {
                return AuthorityUtils.createAuthorityList(roles.toArray(new String[0]));
            }
            return AuthorityUtils.NO_AUTHORITIES;
        });
        return converter;
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity httpSecurity)
            throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider()).build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests(registry -> {
//                    registry.requestMatchers("/", "/login").permitAll();
//                    registry.anyRequest().authenticated();
//                })
//                .oauth2Login(oauth2login -> {
//                    oauth2login
//                            .loginPage("/login")
//                            .successHandler((request, response, authentication) ->
//                                    response.sendRedirect("api/v1/application-google/signup-with-google-new-account"));
//                })
//                .build();
//    }
}
