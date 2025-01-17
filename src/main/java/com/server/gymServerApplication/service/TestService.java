package com.server.gymServerApplication.service;

import com.server.gymServerApplication.iservice.ITestService;
import org.springframework.stereotype.Service;


@Service
public class TestService implements ITestService {
    @Override
    public String sayHello() {
        return "SAY Hello!";
    }
}
