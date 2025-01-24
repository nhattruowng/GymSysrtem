package com.server.gymServerApplication.iservice;

import jakarta.mail.MessagingException;

import java.util.function.Function;

public interface IEmailService {
    void sendMailVerification(String subject, String email,
                              String codeVerifi,
                              Function<String, String> function) throws MessagingException;
}
