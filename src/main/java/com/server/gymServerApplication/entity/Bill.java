package com.server.gymServerApplication.entity;


import com.server.gymServerApplication.entity.baseObj.BaseObject;
import jakarta.persistence.*;
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
//    @Table(indexes = {@Index(name = "idx_used", columnList = "user_id"),
//            @Index(name = "idx_payment", columnList = "payment_id")})
public class Bill extends BaseObject {

    @ManyToOne
    private User user;

    private float total_amount = 0;

    @OneToOne
    private Payment payment;



}
