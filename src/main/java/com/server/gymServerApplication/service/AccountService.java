package com.server.gymServerApplication.service;

import com.server.gymServerApplication.entity.mysql.AccountDetails;
import com.server.gymServerApplication.entity.mysql.User;
import com.server.gymServerApplication.iservice.IAccountService;
import com.server.gymServerApplication.modelView.ResponseObject;
import com.server.gymServerApplication.modelView.reques.ChangeInforAccount;
import com.server.gymServerApplication.repository.mysql.IUserrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.concurrent.CompletableFuture;

@Service
public class AccountService implements IAccountService {


    private final IUserrepository iUserrepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(IUserrepository iUserrepository, PasswordEncoder passwordEncoder) {
        this.iUserrepository = iUserrepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Async
    @Transactional
    public CompletableFuture<ResponseObject> editAccount(ChangeInforAccount changeInforAccount) throws AccountNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccountNotFoundException("CHUA DANG NHAP!");
        }

        AccountDetails userDetails = (AccountDetails) authentication.getPrincipal();

        if (!userDetails.isAccountNonLocked()) {
            throw new AccountNotFoundException("TAI KHOAN DA BAN KHOA!");
        }

        String email = userDetails.getUser().getEmail();
        String phone = userDetails.getUser().getPhone();

        User user = iUserrepository.findByPhoneOrEmail(email, phone)
                .filter(user1 -> !user1.isDelete())
                .orElseThrow(() -> new AccountNotFoundException("TAI KHOAN DA BAN KHOATAI KHOAN DA BAN KHOA"));


        if (changeInforAccount.Email() != null || changeInforAccount.Phone() != null) {
            user.setName(changeInforAccount.name() != null ? changeInforAccount.name() : user.getName());
            user.setAvata(changeInforAccount.avatar() != null ? changeInforAccount.avatar() : user.getAvata());
            user.setEmail(changeInforAccount.Email() != null ? changeInforAccount.Email() : user.getEmail());
            user.setPhone(changeInforAccount.Phone() != null ? changeInforAccount.Phone() : user.getPhone());
        }
        iUserrepository.save(user);

        SecurityContextHolder.getContext().setAuthentication(null);

        return CompletableFuture.completedFuture(ResponseObject.builder()
                .message("CAP NHAT THONG TIN  THANH CONG!")
                .data(true)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @Override
    @Transactional
    public CompletableFuture<ResponseObject> blockAccount(String password) throws AccountNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccountNotFoundException("CHUA DANG NHAP!");
        }

        AccountDetails userDetails = (AccountDetails) authentication.getPrincipal();

        User user = iUserrepository.findByEmail(userDetails.getUser().getEmail())
                .filter(user1 -> !user1.isDelete())
                .orElseThrow(() -> new UsernameNotFoundException("TAI KHOAN KHONG TON TAI HOAC DA BI XOA!"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AccountNotFoundException("MAT KHAU KHONG DUNG!");
        }

        user.setDelete(true);
        iUserrepository.saveAndFlush(user);

        return CompletableFuture.completedFuture(ResponseObject.builder()
                .data(true)
                .httpStatus(HttpStatus.OK)
                .message("XOA TAI KHOAN THANH CONG!")
                .build());
    }



}
