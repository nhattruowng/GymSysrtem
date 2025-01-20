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
    @Table(indexes = {@Index(name = "idx_used", columnList = "user_id"),
            @Index(name = "idx_payment", columnList = "payment_id")})
public class Bill extends BaseObject {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private float total_amount = 0;


    @OneToOne
    @JoinColumn(name = "payment_id", nullable = false, unique = true )
    private Payment payment;



}
