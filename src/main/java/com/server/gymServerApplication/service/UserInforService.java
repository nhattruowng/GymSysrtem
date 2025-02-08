package com.server.gymServerApplication.service;

import com.server.gymServerApplication.entity.mysql.AccountDetails;
import com.server.gymServerApplication.entity.postgresql.UserInformation;
import com.server.gymServerApplication.infor.Gender;
import com.server.gymServerApplication.iservice.IUserInforService;
import com.server.gymServerApplication.modelView.ResponseObject;
import com.server.gymServerApplication.modelView.reques.InformationUerPut;
import com.server.gymServerApplication.repository.mysql.IUserrepository;
import com.server.gymServerApplication.repository.postgresql.IUserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UserInforService implements IUserInforService {


    private final IUserrepository iUserrepository;
    private final IUserInformation userInformation;
    private InformationUerPut informationUerPut;
    private Authentication authentication;


    @Autowired
    public UserInforService(IUserrepository iUserrepository, IUserInformation userInformation) {
        this.iUserrepository = iUserrepository;
        this.userInformation = userInformation;
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
    }


    @Override
    public CompletableFuture<ResponseObject> putInformation(InformationUerPut informationUerPut) {


        UserInformation userInformation1 = UserInformation.builder()
                .age(informationUerPut.age())
                .height(informationUerPut.height())
                .weight(informationUerPut.weight())
                .gender(informationUerPut.gender().equals("MALE") ? Gender.MALE : Gender.FEMALE)
                .threeRoundMeasurement(informationUerPut.threeRoundMeasurement())
                .build();

        if (authentication.isAuthenticated()) {
            AccountDetails userDetails = (AccountDetails) authentication.getPrincipal();
            boolean isAccountexit = iUserrepository.existsByIdIsDeleteFalse(userDetails.getUser().getId());
            if (isAccountexit) {
                userInformation1.setUser(userDetails.getUser().getId());
                userInformation.save(userInformation1);
                return CompletableFuture.completedFuture(ResponseObject.builder()
                        .message("Thong so uoc tinh!")
                        .httpStatus(HttpStatus.OK)
                        .data(userInformation1.getBmi() + " " + userInformation1.getWhr() + " " + userInformation1.getWHtR())
                        .build());
            }
        }
        return CompletableFuture.completedFuture(ResponseObject.builder()
                .message("Thong so uoc tinh!")
                .httpStatus(HttpStatus.OK)
                .data(userInformation1.getBmi() + " " + userInformation1.getWhr() + " " + userInformation1.getWHtR())
                .build());
    }


}
