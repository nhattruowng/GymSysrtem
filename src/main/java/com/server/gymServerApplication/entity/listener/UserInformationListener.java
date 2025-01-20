package com.server.gymServerApplication.entity.listener;

import com.server.gymServerApplication.entity.UserInformation;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class UserInformationListener {
    @PrePersist
    @PreUpdate
    public void calculateBMI(UserInformation userInformation) {
        if (userInformation.getWeight() > 0 && userInformation.getHeight() > 0) {
            userInformation.setBmi(
                    com.server.gymServerApplication.utils.BMI.getBMI(
                            userInformation.getWeight(),
                            userInformation.getHeight()
                    )
            );
        }
    }
}
