package com.server.gymServerApplication.iservice;

import com.server.gymServerApplication.modelView.repon.UserRepo;

public interface IAuthentication {

    boolean FaceRegistration();

    UserRepo FaceLogin();

}
