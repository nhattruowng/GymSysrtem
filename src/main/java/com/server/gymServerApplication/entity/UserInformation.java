package com.server.gymServerApplication.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.server.gymServerApplication.entity.baseObj.BaseObject;
import com.server.gymServerApplication.infor.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserInformation extends BaseObject {

    @OneToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private Gender gender;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date birthDate;




//    public void init() {
//        if (this.birthDate == null) {
//            try {
//                this.birthDate = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000");
//            } catch (ParseException e) {
//                throw new RuntimeException("Error parsing default date", e);
//            }
//        }
//    }
}
