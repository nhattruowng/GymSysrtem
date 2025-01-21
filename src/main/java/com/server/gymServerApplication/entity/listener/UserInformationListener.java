package com.server.gymServerApplication.entity.listener;

import com.server.gymServerApplication.entity.UserInformation;
import com.server.gymServerApplication.infor.WHR;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class UserInformationListener {
    @PrePersist
    @PreUpdate
    public void calculateBMI(UserInformation userInformation) {

        //BMI
        if (userInformation.getWeight() > 0 && userInformation.getHeight() > 0) {
            userInformation.setBmi(
                    com.server.gymServerApplication.utils.BMI.getBMI(
                            userInformation.getWeight(),
                            userInformation.getHeight()
                    )
            );
        }

        // WHR

        float whr = (userInformation.getThreeRoundMeasurement().get(1) / userInformation.getThreeRoundMeasurement().get(2));
        switch (userInformation.getGender()) {
            case MALE -> {
                if (whr < 0.8) {
                    userInformation.setWhr(WHR.LOW_RISK_OF_OBESITY);
                } else if (whr >= 0.8 && whr <= 0.89) {
                    userInformation.setWhr(WHR.MAY_HAVE_RISK_OF_OBESITY);
                } else if (whr >= 0.9) {
                    userInformation.setWhr(WHR.HIGH_RISK_OF_OBESITY);
                }
            }
            case FEMALE -> {
                if (whr < 0.9) {
                    userInformation.setWhr(WHR.LOW_RISK_OF_OBESITY);
                } else if (whr >= 0.9 && whr <= 0.99) {
                    userInformation.setWhr(WHR.MAY_HAVE_RISK_OF_OBESITY);
                } else if (whr >= 1) {
                    userInformation.setWhr(WHR.HIGH_RISK_OF_OBESITY);
                }
            }
            default -> throw new RuntimeException("WHR không có giá trị !");
        }

    }


}
