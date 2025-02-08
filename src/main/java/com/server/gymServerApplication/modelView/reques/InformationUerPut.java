package com.server.gymServerApplication.modelView.reques;

import java.util.List;

public record InformationUerPut(int age,
                                float weight,
                                float height,
                                String gender,
                                List<Float> threeRoundMeasurement
                                ) {
}
