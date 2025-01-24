package com.server.gymServerApplication.controller;


import com.server.gymServerApplication.iservice.IEmailService;
import com.server.gymServerApplication.iservice.ITestService;
import com.server.gymServerApplication.modelView.ResponseObject;
import com.server.gymServerApplication.utils.SendMailUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/v1/test/")
public class test {

    private final ITestService iTestService;
    private final IEmailService emailService;

    @Autowired
    public test(ITestService iTestService,
                IEmailService emailService) {
        this.iTestService = iTestService;
        this.emailService = emailService;
    }


    @GetMapping
    @Operation(summary = "test Api")
    @Async
    public CompletableFuture<ResponseObject> test() {
        return CompletableFuture.completedFuture(ResponseObject.builder()
                .data(iTestService.sayHello())
                .httpStatus(HttpStatus.OK)
                .message("test Api response")
                .build());
    }
    @PostMapping("send-email-test")
    @Operation(summary = "Send Email Test")
    @Async
    public CompletableFuture<ResponseObject> sendEmailTest() throws MessagingException {
        emailService.sendMailVerification("Test Email","ltn04098@gmail.com","test", SendMailUtils.Template("test"));
        return CompletableFuture.completedFuture(ResponseObject.builder()
                .httpStatus(HttpStatus.OK)
                .message("Email sent successfully")
                .build());
    }
}
