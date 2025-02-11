package com.server.gymServerApplication.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/application-google/")
public class LoginController {

    @GetMapping("/")
    public String index() {
        return "Google OAuth2 Login API is working!";
    }

    @GetMapping("/home")
    public Map<String, Object> home(OAuth2AuthenticationToken authenticationToken) {
        return authenticationToken.getPrincipal().getAttributes();
    }
    @GetMapping("/testlogin")
    public Map<String, Object> CurrentUser(OAuth2AuthenticationToken authenticationToken) {
        System.out.println(authenticationToken.getPrincipal().getAttributes());
        return authenticationToken.getPrincipal().getAttributes();
    }
}
