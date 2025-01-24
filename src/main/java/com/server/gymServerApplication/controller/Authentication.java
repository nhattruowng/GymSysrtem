package com.server.gymServerApplication.controller;

import com.server.gymServerApplication.iservice.IAuthentication;
import com.server.gymServerApplication.modelView.ResponseObject;
import com.server.gymServerApplication.modelView.reques.RegisUser;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/v1/application-authent/")
public class Authentication {

    private final IAuthentication authentication;

    @Autowired
    public Authentication(IAuthentication authentication) {
        this.authentication = authentication;
    }

    @PostMapping("signup-newaccount")
    private CompletableFuture<ResponseObject> sinup(@RequestBody RegisUser regisUser) throws MessagingException {
        return authentication.Signup(regisUser);
    }




}
