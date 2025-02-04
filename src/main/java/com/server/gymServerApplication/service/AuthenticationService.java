package com.server.gymServerApplication.service;

import com.server.gymServerApplication.entity.AccountDetails;
import com.server.gymServerApplication.entity.User;
import com.server.gymServerApplication.iservice.IAuthentication;
import com.server.gymServerApplication.iservice.IEmailService;
import com.server.gymServerApplication.modelView.ResponseObject;
import com.server.gymServerApplication.modelView.map.ObjMap;
import com.server.gymServerApplication.modelView.repon.LoginRepose;
import com.server.gymServerApplication.modelView.repon.UserRepo;
import com.server.gymServerApplication.modelView.reques.LoginReques;
import com.server.gymServerApplication.modelView.reques.RegisUser;
import com.server.gymServerApplication.repository.IUserrepository;
import com.server.gymServerApplication.utils.OtherFunctions;
import com.server.gymServerApplication.utils.SendMailUtils;
import jakarta.mail.MessagingException;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
@Aspect
public class AuthenticationService implements IAuthentication {

    private final IUserrepository iUserrepository;
    private final IEmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    private User user;


    private String verifiCode;

    @Autowired
    public AuthenticationService(IUserrepository iUserrepository, IEmailService emailService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.iUserrepository = iUserrepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
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

        if (iUserrepository.existsUserByEmail(regisUser.email())) { /// email co dang ki r !
            return CompletableFuture.completedFuture(new ResponseObject("email da ton tai", HttpStatus.OK, false));
        }

        // gui mail
        verifiCode = OtherFunctions.generateRandomNumberString();

        emailService.sendMailVerification("CHAO MUNG BAN DEN VOI HE THONG GYMSYSTEM",
                regisUser.email(), verifiCode,
                SendMailUtils.Template(verifiCode));

        user = User.builder()
                .email(regisUser.email())
                .password(passwordEncoder.encode(regisUser.password()))
                .build();


        return CompletableFuture.completedFuture(new ResponseObject("NHAP MA XAC NHAN ", HttpStatus.OK, null));
    }

    @Transactional
    @Override
    public CompletableFuture<ResponseObject> verify(String code) {
        ResponseObject resulResponseObject = null;
        if (code.equals(verifiCode)) {
            iUserrepository.save(user);
            resulResponseObject = new ResponseObject("DANG KI THANH CONG", HttpStatus.OK, true);
        } else {
            resulResponseObject = new ResponseObject("DANG KI THAT BAI!", HttpStatus.OK, false);
        }
        return CompletableFuture.completedFuture(resulResponseObject);
    }

    @Async
    @Override
    public CompletableFuture<ResponseObject> login(LoginReques loginReques) {
        System.err.println(loginReques.keyLogin());
        System.err.println(loginReques.password());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginReques.keyLogin(), loginReques.password())
            );

            AccountDetails userDetails = (AccountDetails) authentication.getPrincipal();
            LoginRepose loginRepose = ObjMap.INSTANCE.loginRepose(userDetails.getUser());

//            LoginRepose loginRepose = LoginRepose.builder()
//                    .phone(userDetails.getUser().getPhone())
//                    .avata(userDetails.getUser().getAvata())
//                    .token(tokenService.generateToken(userDetails.getUser()))
//                    .name(userDetails.getUser().getName())
//                    .email(userDetails.getUser().getEmail())
//                    .build();


            return CompletableFuture.completedFuture(ResponseObject.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Login successful")
                    .data(loginRepose)
                    .build());
        } catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseObject.builder()
                    .httpStatus(HttpStatus.UNAUTHORIZED)
                    .message(e.getMessage())
                    .build());
        }

//        return CompletableFuture.completedFuture(ResponseObject.builder()
//                .httpStatus(HttpStatus.OK)
//                .message("OK")
//                .build());
    }


}
