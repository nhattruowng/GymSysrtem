package com.server.gymServerApplication.iservice;

import com.server.gymServerApplication.modelView.ResponseObject;
import com.server.gymServerApplication.modelView.reques.ChangeInforAccount;

import javax.security.auth.login.AccountNotFoundException;
import java.util.concurrent.CompletableFuture;

public interface IAccountService {
    CompletableFuture<ResponseObject> editAccount(ChangeInforAccount changeInforAccount) throws AccountNotFoundException;

    CompletableFuture<ResponseObject> blockAccount(String password) throws AccountNotFoundException;




}
