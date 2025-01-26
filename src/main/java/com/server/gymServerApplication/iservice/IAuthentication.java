package com.server.gymServerApplication.iservice;

import com.server.gymServerApplication.modelView.ResponseObject;
import com.server.gymServerApplication.modelView.repon.UserRepo;
import com.server.gymServerApplication.modelView.reques.LoginReques;
import com.server.gymServerApplication.modelView.reques.RegisUser;
import jakarta.mail.MessagingException;

import java.util.concurrent.CompletableFuture;

public interface IAuthentication {

    boolean FaceRegistration();

    UserRepo FaceLogin();

    CompletableFuture<ResponseObject> Signup(RegisUser user) throws MessagingException;

    CompletableFuture<ResponseObject> verify(String code);


    CompletableFuture<ResponseObject> login(LoginReques loginReques);

}
