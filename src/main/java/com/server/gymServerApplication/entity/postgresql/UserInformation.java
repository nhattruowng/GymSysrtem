package com.server.gymServerApplication.entity.postgresql;

import com.server.gymServerApplication.entity.baseObj.BaseObject;
import com.server.gymServerApplication.entity.listener.UserInformationListener;
import com.server.gymServerApplication.infor.BMI;
import com.server.gymServerApplication.infor.Gender;
import com.server.gymServerApplication.infor.WHR;
import com.server.gymServerApplication.infor.WHtR;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EntityListeners(UserInformationListener.class) // Tinh BMI khi tạo infor
@Table(indexes = {
        @Index(name = "idx_user_information", columnList = "user_id")
})
public class UserInformation extends BaseObject {

    @Column(name = "user_id", nullable = false, unique = true)
    private String user;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Size(max = 100, min = 1, message = "Tuoi sai")
    private int age;

    @Positive(message = "Cân nặng lớn hơn 0!")
    private float weight;

    @Positive(message = "Chiều cao lớn hơn 0!")
    private float height;

    @ElementCollection
    @Column(name = "measurement")
    @Size(max = 3, message = "Lưu số đo 3 vòng ")
    private List<@Positive(message = "Mỗi số đo phải lớn hơn 0!") Float> threeRoundMeasurement = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private BMI bmi;

    @Enumerated(EnumType.STRING)
    private WHR whr;

    @Enumerated(EnumType.STRING)
    private WHtR wHtR; //Chỉ số vòng eo / chiều cao

}
