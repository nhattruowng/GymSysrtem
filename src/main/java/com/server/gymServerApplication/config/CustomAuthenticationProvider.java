//package com.server.gymServerApplication.config;
//
//import com.server.gymServerApplication.entity.AccountDetails;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    private final UserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder;
//
//    public CustomAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
//        this.userDetailsService = userDetailsService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String key = authentication.getName(); // email or sdt
//        String password = (String) authentication.getCredentials(); // password
//
//        AccountDetails userDetails = (AccountDetails) userDetailsService.loadUserByUsername(key);
//
//
//        if (userDetails == null)
//            throw new UsernameNotFoundException("email hoat sdt khong hop le!");
//
//        if (!passwordEncoder.matches(password, userDetails.getUser().getPassword()))
//            throw new BadCredentialsException("sai mat khau!");
//
//
//        return new UsernamePasswordAuthenticationToken(
//                userDetails, password, userDetails.getAuthorities());
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//}
