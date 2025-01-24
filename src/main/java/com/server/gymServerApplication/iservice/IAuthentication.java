package com.server.gymServerApplication.iservice;

import com.server.gymServerApplication.modelView.ResponseObject;
import com.server.gymServerApplication.modelView.repon.UserRepo;
import com.server.gymServerApplication.modelView.reques.RegisUser;

public interface IAuthentication {

    boolean FaceRegistration();

    UserRepo FaceLogin();

    Comparable<ResponseObject> Signup(RegisUser user);

}
