package com.server.gymServerApplication.utils;

import com.server.gymServerApplication.infor.Gender;
import com.server.gymServerApplication.infor.WHR;
import com.server.gymServerApplication.infor.WHtR;

public class BMI {

    public static com.server.gymServerApplication.infor.BMI getBMI(float weight, float height) {
        float bmiValue = weight / (height * height);
        if (bmiValue < 18.5) {
            return com.server.gymServerApplication.infor.BMI.UNDERWEIGHT;
        } else if (bmiValue >= 18.5 && bmiValue <= 24.9) {
            return com.server.gymServerApplication.infor.BMI.HEALTHY_WEIGHT;
        } else if (bmiValue >= 25 && bmiValue <= 29.9) {
            return com.server.gymServerApplication.infor.BMI.OVERWEIGHT;
        } else {
            return com.server.gymServerApplication.infor.BMI.OBESE;
        }
    }

    public static WHR getWhr(Gender gender, float round1, float round2) {
        float whr = round1 / round2;
        return switch (gender) {
            case MALE -> (whr < 0.8) ? WHR.LOW_RISK_OF_OBESITY :
                    (whr >= 0.8 && whr <= 0.89) ? WHR.MAY_HAVE_RISK_OF_OBESITY :
                            WHR.HIGH_RISK_OF_OBESITY;

            case FEMALE -> (whr < 0.9) ? WHR.LOW_RISK_OF_OBESITY :
                    (whr >= 0.9 && whr <= 0.99) ? WHR.MAY_HAVE_RISK_OF_OBESITY :
                            WHR.HIGH_RISK_OF_OBESITY;

            default -> WHR.UNCONFIRMED;
        };
    }

    public static WHtR getWhtr(float round2, float height) {
        float whtr = round2 / height;
        return (whtr < 0.85) ? WHtR.HEALTHY :
                (whtr >= 0.85 && whtr <= 0.94) ? WHtR.OVERWEIGHT :
                        (whtr >= 0.95 && whtr <= 1.04) ? WHtR.OBESITY :
                                (whtr >= 1.05 && whtr <= 1.14) ? WHtR.MORbid_OBESITY : WHtR.UNCONFIRMED
                ;
    }
}
