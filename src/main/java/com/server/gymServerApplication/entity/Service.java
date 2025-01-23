package com.server.gymServerApplication.entity;


import com.server.gymServerApplication.entity.baseObj.BaseObject;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
public class Service extends BaseObject {

    @NotNull
    private String name;

    private String description;

    @Size(min = 0, message = "KHONG THE CO GIA BE HON 0D !")
    private double price;


    @OneToOne
    @JoinColumn(name = "membership_id", nullable = false, unique = true)
    private Membership membership;

}
