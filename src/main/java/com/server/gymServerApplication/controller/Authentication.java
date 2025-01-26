package com.server.gymServerApplication.controller;

import com.server.gymServerApplication.iservice.IAuthentication;
import com.server.gymServerApplication.modelView.ResponseObject;
import com.server.gymServerApplication.modelView.reques.LoginReques;
import com.server.gymServerApplication.modelView.reques.RegisUser;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/v1/application-authent/")
public class Authentication {

    private final IAuthentication authentication;

    @Autowired
    public Authentication(IAuthentication authentication) {
        this.authentication = authentication;
    }

    @PostMapping("signup-new-account")
    public CompletableFuture<ResponseObject> sinup(@RequestBody RegisUser regisUser) throws MessagingException {
        return authentication.Signup(regisUser);
    }

    @PostMapping("signup-new-account/verified/{code}")
    public CompletableFuture<ResponseObject> sinVerified(@PathVariable String code) throws MessagingException {
        return authentication.verify(code);
    }

    @PostMapping("login-account")
    public CompletableFuture<ResponseObject>login(@RequestBody LoginReques loginReques){
        return authentication.login(loginReques);
    }



}
