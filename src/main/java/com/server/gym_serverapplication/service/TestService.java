package com.server.gym_serverapplication.service;

import com.server.gym_serverapplication.iservice.ITestService;
import org.springframework.stereotype.Service;


@Service
public class TestService implements ITestService {
    @Override
    public String sayHello() {
        return "SAY Hello!";
    }
}
