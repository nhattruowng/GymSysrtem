package com.server.gymServerApplication.service;

import com.server.gymServerApplication.entity.User;
import com.server.gymServerApplication.iservice.IAuthentication;
import com.server.gymServerApplication.iservice.IEmailService;
import com.server.gymServerApplication.modelView.ResponseObject;
import com.server.gymServerApplication.modelView.repon.UserRepo;
import com.server.gymServerApplication.modelView.reques.RegisUser;
import com.server.gymServerApplication.repository.IUserrepository;
import com.server.gymServerApplication.utils.OtherFunctions;
import com.server.gymServerApplication.utils.SendMailUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
public class AuthenticationService implements IAuthentication {

    private final IUserrepository iUserrepository;
    private final IEmailService emailService;
    private final PasswordEncoder passwordEncoder;

    private User user;


    private String verifiCode;

    @Autowired
    public AuthenticationService(IUserrepository iUserrepository, IEmailService emailService, PasswordEncoder passwordEncoder) {
        this.iUserrepository = iUserrepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
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

    @Transactional
    @Async
    @Override
        public CompletableFuture<ResponseObject> Signup(RegisUser regisUser) throws MessagingException {
        ResponseObject resulResponseObject = new ResponseObject();
        boolean result = iUserrepository.existsUserByEmail(regisUser.email());
        if (result) { /// email co dang ki r !
            resulResponseObject = new ResponseObject("email da ton tai", HttpStatus.OK , null);
        }

        // gui mail
        verifiCode = OtherFunctions.generateRandomNumberString();
        try {
            emailService.sendMailVerification("CHAO MUNG BAN DEN VOI HE THONG GYMSYSTEM", regisUser.email(),verifiCode, SendMailUtils.Template(verifiCode));
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending");
        }
        user = User.builder()
                .email(regisUser.email())
                .password(passwordEncoder.encode(regisUser.password()))
                .build();

        iUserrepository.save(user);
        resulResponseObject = new ResponseObject("OK ", HttpStatus.OK , null);

        return CompletableFuture.completedFuture(resulResponseObject);
    }
}
