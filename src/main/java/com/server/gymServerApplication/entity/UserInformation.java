package com.server.gymServerApplication.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.server.gymServerApplication.entity.baseObj.BaseObject;
import com.server.gymServerApplication.entity.listener.UserInformationListener;
import com.server.gymServerApplication.infor.BMI;
import com.server.gymServerApplication.infor.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EntityListeners(UserInformationListener.class) // Tinh BMI khi tạo infor
public class UserInformation extends BaseObject {

    @OneToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private Gender gender;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

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



}
