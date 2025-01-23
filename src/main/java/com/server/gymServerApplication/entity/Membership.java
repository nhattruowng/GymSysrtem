package com.server.gymServerApplication.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.server.gymServerApplication.entity.baseObj.BaseObject;
import com.server.gymServerApplication.infor.MEMBERSHIP_TYPE;
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
@Table(indexes = {@Index(name = "idx_bill", columnList = "bill_id"),
        @Index(name = "idx_service", columnList = "service_id")})
public class Membership extends BaseObject {
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToOne
    @JoinColumn(name = "bill_id", nullable = false, unique = true)
    private Bill bill;

    @OneToOne
    @JoinColumn(name = "service_id", nullable = false, unique = true)
    private Service service;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private MEMBERSHIP_TYPE membershipType;
}
