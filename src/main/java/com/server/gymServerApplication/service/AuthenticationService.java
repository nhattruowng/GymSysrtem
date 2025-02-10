package com.server.gymServerApplication.service;

import com.server.gymServerApplication.entity.mysql.AccountDetails;
import com.server.gymServerApplication.entity.mysql.User;
import com.server.gymServerApplication.iservice.IAuthentication;
import com.server.gymServerApplication.iservice.IEmailService;
import com.server.gymServerApplication.modelView.ResponseObject;
import com.server.gymServerApplication.modelView.reques.ChangeInforAccount;
import com.server.gymServerApplication.modelView.repon.ChangePassword;
import com.server.gymServerApplication.modelView.repon.LoginRepose;
import com.server.gymServerApplication.modelView.repon.UserRepo;
import com.server.gymServerApplication.modelView.reques.LoginReques;
import com.server.gymServerApplication.modelView.reques.RegisUser;
import com.server.gymServerApplication.repository.mysql.IUserrepository;
import com.server.gymServerApplication.repository.postgresql.IFaceID;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.concurrent.CompletableFuture;

@Service
@Aspect
public class AuthenticationService implements IAuthentication {

    private final IUserrepository iUserrepository;
    private final IEmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final IFaceID faceidRepository;
    private Authentication authentication;
    private User user;
    private String verifiCode;

    @Autowired
    public AuthenticationService(IUserrepository iUserrepository,
                                 IEmailService emailService,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 TokenService tokenService, IFaceID faceidRepository) {
        this.iUserrepository = iUserrepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.faceidRepository = faceidRepository;
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        this.user = new User();
    }

    public void setAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Override
    public boolean FaceRegistration() throws AccountNotFoundException {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccountNotFoundException("BAN CHUA DANG NHAP!");
        }

        AccountDetails userDetails = (AccountDetails) authentication.getPrincipal();

        if (!userDetails.isAccountNonLocked()) {
            throw new AccountNotFoundException("TAI KHOAN DA BAN KHOA!");
        }



        return false;
    }

    @Override
    public UserRepo FaceLogin() {
        return null;
    }

    //    @Transactional("primaryTransactionManager")
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
                SendMailUtils.Template(verifiCode, "MA XAC THUC GYMSTEM"));

        user = User.builder()
                .email(regisUser.email())
                .password(passwordEncoder.encode(regisUser.password()))
                .name(regisUser.email())
                .build();


        return CompletableFuture.completedFuture(new ResponseObject("NHAP MA XAC NHAN ", HttpStatus.OK, null));
    }

    //    @Transactional("primaryTransactionManager")
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
//    @Cacheable(value = "users", key = "#loginReques.keyLogin()")
    public CompletableFuture<ResponseObject> login(LoginReques loginReques) {
//        System.err.println(loginReques.keyLogin());
//        System.err.println(loginReques.password());
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginReques.keyLogin(), loginReques.password())
            );

            AccountDetails userDetails = (AccountDetails) authentication.getPrincipal();

            SecurityContextHolder.getContext().setAuthentication(authentication);

            LoginRepose loginRepose = new LoginRepose(userDetails.getUser().getName()
                    , userDetails.getUser().getEmail(),
                    userDetails.getUser().getPhone(),
                    userDetails.getUser().getAvata(),
                    tokenService.generateToken(userDetails.getUser())
            );

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

    }

    /*

     */
    @Override
    @Async
    public CompletableFuture<ResponseObject> changePassWork(ChangePassword password) throws AccountNotFoundException {

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccountNotFoundException("BAN CHUA DANG NHAP!");
        }

        AccountDetails userDetails = (AccountDetails) authentication.getPrincipal();

        if (!userDetails.isAccountNonLocked()) {
            throw new AccountNotFoundException("TAI KHOAN DA BAN KHOA!");
        }

        String email = userDetails.getUser().getEmail();
        String phone = userDetails.getUser().getPhone();

        User user = iUserrepository.findByPhoneOrEmail(email, phone)
                .filter(user1 -> !user1.isDelete())
                .orElseThrow(() -> new AccountNotFoundException("TAI KHOAN DA BAN KHOA!"));

        if (!passwordEncoder.matches(password.oldPassword(), user.getPassword())) {
            throw new AccountNotFoundException("MAT KHAU KHONG DUNG!");
        }

        if (password.oldPassword().equals(password.newPassword())) {
            throw new AccountNotFoundException("MAT KHAU MUON THAY DOI KHONG DUOC TRUNG VOI MAT KHAU HIEN TAI!");
        }

        user.setPassword(passwordEncoder.encode(password.newPassword()));

        iUserrepository.save(user);

        setAuthentication();

        return CompletableFuture.completedFuture(ResponseObject.builder()
                .data(true)
                .message("THAY DOI MAT KHAU THANH CONG!")
                .httpStatus(HttpStatus.OK)
                .build());
    }


    @Override
    public CompletableFuture<ResponseObject> forgotPasswordAccess(String key)
            throws AccountNotFoundException, MessagingException {

        User tempUser = iUserrepository.findByPhoneOrEmail(key, key)
                .filter(user1 -> !user1.isDelete())
                .orElseThrow(() -> new AccountNotFoundException("TAI KHOAN KHONG TON TAI HOAC DA BI XOA!"));


        String password = OtherFunctions.generateRandomNumberString();
        tempUser.setPassword(passwordEncoder.encode(password));

        iUserrepository.save(tempUser); // luu password vao

        emailService.sendMailVerification("MAT KHAU GYMSYSTEM",
                OtherFunctions.anonymousEmail(tempUser.getEmail()), password,
                SendMailUtils.Template(password, "MAT KHAU HIEN TAI CUA BAN LA: "));


        setAuthentication(); // xoa tai khoan khoi Security

        return CompletableFuture.completedFuture(ResponseObject.builder()
                .data(true)
                .httpStatus(HttpStatus.OK)
                .message("MAT KHAU DA GUI VE MAIL")
                .build());
    }






}
