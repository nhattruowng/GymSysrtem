package com.server.gymServerApplication.entity;

import com.server.gymServerApplication.entity.baseObj.BaseObject;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
//@Table(indexes = {@Index(name = "idx_email", columnList = "email"),
//        @Index(name = "idx_phone", columnList = "phone")})
public class Payment extends BaseObject {

    @Size(min = 0, message = "Gia Khong the am !")
    private double amount;

    @OneToOne
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;


}
