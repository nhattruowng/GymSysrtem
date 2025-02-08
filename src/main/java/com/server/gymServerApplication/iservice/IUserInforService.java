package com.server.gymServerApplication.iservice;

import com.server.gymServerApplication.modelView.ResponseObject;
import com.server.gymServerApplication.modelView.reques.InformationUerPut;

import java.util.concurrent.CompletableFuture;

public interface IUserInforService {


    CompletableFuture<ResponseObject> putInformation(InformationUerPut informationUerPut);

}
