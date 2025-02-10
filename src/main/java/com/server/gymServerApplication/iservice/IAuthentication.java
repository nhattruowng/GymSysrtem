package com.server.gymServerApplication.iservice;

import com.server.gymServerApplication.modelView.ResponseObject;
import com.server.gymServerApplication.modelView.repon.ChangePassword;
import com.server.gymServerApplication.modelView.repon.UserRepo;
import com.server.gymServerApplication.modelView.reques.LoginReques;
import com.server.gymServerApplication.modelView.reques.RegisUser;
import jakarta.mail.MessagingException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.concurrent.CompletableFuture;

public interface IAuthentication {

    boolean FaceRegistration() throws AccountNotFoundException;

    UserRepo FaceLogin();

    CompletableFuture<ResponseObject> Signup(RegisUser user) throws MessagingException;

    CompletableFuture<ResponseObject> verify(String code);


    CompletableFuture<ResponseObject> login(LoginReques loginReques);

    CompletableFuture<ResponseObject> changePassWork(ChangePassword password) throws AccountNotFoundException;

    CompletableFuture<ResponseObject> forgotPasswordAccess(String key) throws AccountNotFoundException, MessagingException;


}
