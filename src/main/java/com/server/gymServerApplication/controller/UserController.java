package com.server.gymServerApplication.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/oauth-user/")
@PreAuthorize("hasRole('USER')")
public class UserController {
    @GetMapping("test")
    public String getUserName() {
        return "user test";
    }
}
