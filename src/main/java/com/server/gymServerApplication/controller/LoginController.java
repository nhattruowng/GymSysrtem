package com.server.gymServerApplication.controller;

import com.server.gymServerApplication.iservice.IAuthentication;
import com.server.gymServerApplication.modelView.ResponseObject;
import com.server.gymServerApplication.modelView.reques.RegisUser;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/v1/application-google/")
public class LoginController {
    private final IAuthentication authentication;

    @Autowired
    public LoginController(IAuthentication authentication) {
        this.authentication = authentication;
    }

    @GetMapping("/")
    public String index() {
        return "Google OAuth2 Login API is working!";
    }

    @GetMapping("/home")
    public Map<String, Object> home(OAuth2AuthenticationToken authenticationToken) {
        return authenticationToken.getPrincipal().getAttributes();
    }

    @GetMapping("/login")
    public String login() {
        return "custom_login";
    }

    @GetMapping("/profile")
    public String profile(OAuth2AuthenticationToken token, Model model) {
        model.addAttribute("name", token.getPrincipal().getAttribute("name"));
        model.addAttribute("email", token.getPrincipal().getAttribute("email"));
        model.addAttribute("photo", token.getPrincipal().getAttribute("picture"));
        System.out.println(token.getPrincipal().getAttributes());
        return "home";
    }

    @GetMapping("/signup-with-google-new-account")
    public CompletableFuture<ResponseObject> signupWithGoogle(OAuth2AuthenticationToken authenticationToken) throws MessagingException {
        Map<String, Object> attributes = authenticationToken.getPrincipal().getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String picture = (String) attributes.get("picture");
        System.out.println(email + "_________" + name + "_________" + picture);
        return authentication.SignupWitGoogle(email, name, picture) ;
    }
}
