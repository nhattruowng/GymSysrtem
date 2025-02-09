package com.server.gymServerApplication.entity.mysql;


import com.server.gymServerApplication.entity.baseObj.BaseObject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(indexes = {@Index(name = "idx_membership",columnList = "membership_id")})
public class Service extends BaseObject {

    @NotNull
    private String name;

    private String description;

    @Size(min = 0, message = "KHONG THE CO GIA BE HON 0D !")
    private double price;

    @OneToOne
    @JoinColumn(name = "membership_id", nullable = false, unique = true)
    private Membership membership;

    private int valueTimeout;  // gia tri cua dich vu keo day.



}
