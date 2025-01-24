package com.server.gymServerApplication.service;

import com.server.gymServerApplication.entity.User;
import com.server.gymServerApplication.iservice.IAuthentication;
import com.server.gymServerApplication.modelView.ResponseObject;
import com.server.gymServerApplication.modelView.repon.UserRepo;
import com.server.gymServerApplication.modelView.reques.RegisUser;
import com.server.gymServerApplication.repository.IUserrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthentication {

    private final IUserrepository iUserrepository;
    private User user;

    @Autowired
    public AuthenticationService(IUserrepository iUserrepository) {
        this.iUserrepository = iUserrepository;
        this.user = new User();
    }


    @Override
    public boolean FaceRegistration() {
        return false;
    }

    @Override
    public UserRepo FaceLogin() {
        return null;
    }

    @Override
    public Comparable<ResponseObject> Signup(RegisUser user) {
        ResponseObject resulResponseObject;
        boolean result = iUserrepository.existsUserByEmail(user.email());
        if (result) { /// email co dang ki r !

        }

        // gui mail




        // ktr password


        return null;
    }
}
