package com.server.gymServerApplication.entity.listener;

import com.server.gymServerApplication.entity.UserInformation;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class UserInformationListener {
    @PrePersist
    @PreUpdate
    public void calculateBMI(UserInformation userInformation) {

        //BMI
        userInformation.setBmi(com.server.gymServerApplication.utils.BMI.getBMI(userInformation.getWeight(), userInformation.getHeight()));

        // WHR
        userInformation.setWhr(com.server.gymServerApplication.utils.BMI.getWhr(userInformation.getGender()
                , userInformation.getThreeRoundMeasurement().get(1)
                , userInformation.getThreeRoundMeasurement().get(2)));

        // WHtR
        userInformation.setWHtR(com.server.gymServerApplication.utils.BMI
                .getWhtr(userInformation.getThreeRoundMeasurement().get(1),
                        userInformation.getHeight()));

    }


}
