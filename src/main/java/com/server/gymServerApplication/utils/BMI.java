package com.server.gymServerApplication.utils;

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
}
