package com.server.gym_serverapplication.controller;


import com.server.gym_serverapplication.iservice.ITestService;
import com.server.gym_serverapplication.modelView.ResponseObject;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/test/")
public class test {

    private final ITestService iTestService;

    @Autowired
    public test(ITestService iTestService) {
        this.iTestService = iTestService;
    }


    @GetMapping("test")
    @Operation(summary = "test Api")
    @Async
    public CompletableFuture<ResponseObject> test() {
        return CompletableFuture.completedFuture(ResponseObject.builder()
                .data(iTestService.sayHello())
                .httpStatus(HttpStatus.OK)
                .message("test Api response")
                .build());
    }
}
