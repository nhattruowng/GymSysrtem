package com.server.gymServerApplication.entity.mysql;


import com.server.gymServerApplication.entity.baseObj.BaseObject;
import com.server.gymServerApplication.entity.listener.BillListenerObj;
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
@Table(indexes = {@Index(name = "idx_used", columnList = "user_id"),
        @Index(name = "idx_memberships", columnList = "membership_id"),
        @Index(name = "idx_service", columnList = "service_id"),
        @Index(name = "idx_payment", columnList = "payment_id")})
@EntityListeners(BillListenerObj.class)
public class Bill extends BaseObject {

    @ManyToOne
    private User user;

    private double total_amount = 0;

    @Size(min = 0, message = "So luong don hang k the be hon 1!")
    private int quantity = 1;

    @OneToOne
    @JoinColumn(name = "payment_id", nullable = false, unique = true)
    private Payment payment;

    @OneToOne
    @JoinColumn(name = "membership_id", nullable = false, unique = true)
    private Membership membership;

    @OneToOne
    @JoinColumn(name = "service_id", nullable = false, unique = true)
    private Service service;

}
