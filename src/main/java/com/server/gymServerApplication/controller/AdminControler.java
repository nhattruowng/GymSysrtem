package com.server.gymServerApplication.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/oauth-admin/")
public class AdminControler {

    @GetMapping("test")
    public String test(){
        return "Admin Page";
    }


}
